package ni.edu.uccleon.ticket

import grails.transaction.Transactional
import static java.util.Calendar.*

@Transactional
class AssistanceService {

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
        List<Assistance> assistances = Assistance.where {
            year(dateCreated) == y && month(dateCreated) == this.months.findIndexOf { it == m } + 1
        }.list()

        List byDepartments = assistances.groupBy { it.user.departments } { it.user }.collect { a ->
            [
                departments: a.key,
                users: a.value.collect { u ->
                    [
                        user: u.key.fullName,
                        programmed: u.value.findAll { it.type == "PROGRAMMED" }.size(),
                        nonscheduled: u.value.findAll { it.type == "NON-SCHEDULED" }.size(),
                        pending: u.value.findAll { it.state == "PENDING" }.size(),
                        process: u.value.findAll { it.state == "PROCESS" }.size(),
                        closed: u.value.findAll { it.state == "CLOSED" }.size(),
                    ]
                }
            ]
        }

        byDepartments
    }
}
