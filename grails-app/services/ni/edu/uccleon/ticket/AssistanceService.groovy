package ni.edu.uccleon.ticket

import grails.transaction.Transactional
import static java.util.Calendar.*

@Transactional
class AssistanceService {
    DepartmentService departmentService

    static private List getMonths() {
        [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ]
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
        Map assistances = Assistance.findAll {
            year(dateCreated) == y &&
            month(dateCreated) == this.months.findIndexOf { it == m } + 1
        }.groupBy { it.user.departments[0] }

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
}
