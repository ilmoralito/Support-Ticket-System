package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder

class TicketTagLib {
    static namespace = "ticket"
    //static defaultEncodeAs = [taglib:'html']
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

    def renderDepartments = { attrs, body ->
        def rest = new RestBuilder()
        def resp = rest.get("http://localhost:9090/departments")
        def data = resp.json.groupBy { it.area }.collect {
            [ area: it.key, data: it.value.name ]
        }

        out << render(template: "/templates/departments", model: [departments: data])
    }
}
