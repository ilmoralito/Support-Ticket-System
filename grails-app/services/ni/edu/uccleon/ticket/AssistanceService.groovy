package ni.edu.uccleon.ticket

import grails.transaction.Transactional
import static java.util.Calendar.*

@Transactional
class AssistanceService {

    private def getMonths() {
        [
            "Enero",
            "Febrero",
            "Marzo",
            "Abril",
            "Mayo",
            "Junio",
            "Julio",
            "Agosto",
            "Septiembre",
            "Octubre",
            "Noviembre",
            "Diciembre"
        ]
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
}
