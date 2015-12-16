package ni.edu.uccleon.ticket

import grails.transaction.Transactional
import static java.util.Calendar.*

@Transactional
class AssistanceService {
    DepartmentService departmentService

    static private List getMonths() {
        [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ]
    }

    static private Map getTypes() {
        [ PROGRAMMED: "Programado", "NON-SCHEDULED": "No programado" ]
    }

    def getByYearAndMonth(Integer y, String m) {
        List assistances = Assistance.findAll {
            year(dateCreated) == y &&
            month(dateCreated) == this.months.findIndexOf { it == m } + 1
        }

        assistances
    }

    def getResume() {
        List<Assistance> assistances = Assistance.list()
        List grouped = assistances.groupBy { it.dateCreated[YEAR] } { it.dateCreated[MONTH] } { it.type }.collect { a ->
            [
                year: a.key,
                months: a.value.collect { b ->
                    [
                        month: this.months[b.key],
                        programmed: b.value["PROGRAMMED"].size(),
                        nonscheduled: b.value["NON-SCHEDULED"].size(),
                        total: b.value["PROGRAMMED"].size() + b.value["NON-SCHEDULED"].size()
                    ]
                }
            ]
        }

        grouped
    }

    def getResumeDetail(Integer y, String m) {
        List departments = departmentService.departments
        Map assistances = this.getByYearAndMonth(y, m).groupBy { it.user.departments[0] }

        List byDepartments = departments.collect { d ->
            [
                area: d.area,
                departments: d.departments.collect { ds ->
                    [
                        name: ds,
                        programmed: assistances[ds] ? assistances[ds].count { it.type == "PROGRAMMED" } : 0,
                        nonscheduled: assistances[ds] ? assistances[ds].count { it.type == "NON-SCHEDULED" } : 0,
                        pending: assistances[ds] ? assistances[ds].count { it.state == "PENDING" } : 0,
                        process: assistances[ds] ? assistances[ds].count { it.state == "PROCESS" } : 0,
                        closed: assistances[ds] ? assistances[ds].count { it.state == "CLOSED" } : 0
                    ]
                }
            ]
        }

        byDepartments
    }

    def getResumeDetailByDepartment(Integer y, String m, String department) {
        Map assistances = this.getByYearAndMonth(y, m).findAll {
            department in it.user.departments
        }.groupBy { it.user }

        List data = assistances.collect { d ->
            [
                user: d.key.fullName,
                assistances: d.value.collect { a ->
                    [
                        dateCreated: a.dateCreated,
                        lastUpdated: a.lastUpdated,
                        attendedBy: a.attendedBy.user.fullName.join(", "),
                        type: a.type,
                        state: a.state,
                        tags: a.tags.name.join(", ")
                    ]
                }
            ]
        }

        data
    }
}
