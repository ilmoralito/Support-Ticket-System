package ni.edu.uccleon.ticket

class TicketTagLib {
    static namespace = "ticket"
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def state = { attrs ->
        def attendedBy = attrs?.attendedBy
        def dateCompleted = attendedBy?.dateCompleted

        if (attendedBy && dateCompleted) {
            out << "Cerrado"
        } else if (attendedBy) {
            out << "Atendiendo"
        } else if (!attendedBy) {
            out << "Sin atender"
        }
    }
}
