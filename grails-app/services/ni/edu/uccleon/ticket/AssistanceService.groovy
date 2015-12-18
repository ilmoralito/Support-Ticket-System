package ni.edu.uccleon.ticket

import grails.transaction.Transactional
import static java.util.Calendar.*

@Transactional
class AssistanceService {
    DepartmentService departmentService
    TagService tagService

    static private List getMonths() {
        [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ]
    }

    static private Map getTypes() {
        [ PROGRAMMED: "Programado", "NON-SCHEDULED": "No programado" ]
    }

    def departmentInUserDepartments(String department, Assistance assistance) {
        department in assistance.user.departments
    }

    def getByYearAndMonth(Integer y, String m) {
        List assistances = Assistance.findAll {
            year(dateCreated) == y &&
            month(dateCreated) == this.months.findIndexOf { it == m } + 1
        }

        assistances
    }

    def getAssistances() {
        Assistance.list()
    }

    def getResume() {
        List<Assistance> assistances = this.assistances
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

    def getResumeByTag() {
        Map assistances = this.assistances.groupBy { it.dateCreated[YEAR] } { it.dateCreated[MONTH] }
        List tags = tagService.tags
        List months = this.months
        List byTags = assistances.collect { a ->
            [
                year: a.key,
                months: months.collect { m ->
                    [
                        name: m,
                        tags: tags.collect { t ->
                            [
                                name: t.name,
                                count: a.value.find { it.key == months.indexOf(m) }?.value?.count { it.tags.name.contains(t.name) } ?: 0
                            ]
                        }
                    ]
                }
            ]
        }

        [byTags: byTags, tags: tags, months: months]
    }
}
