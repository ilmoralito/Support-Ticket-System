package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder

class TicketTagLib {
    static namespace = "ticket"
    //static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def state = { attrs ->
        switch(attrs.state) {
            case "PENDING":
                out << "Pendiente"
            break
            case "PROCESS":
                out << "Proceso"
            break
            case "CLOSED":
                out << "Cerrado"
            break
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
