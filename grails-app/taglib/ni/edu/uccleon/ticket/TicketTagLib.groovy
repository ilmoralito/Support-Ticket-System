package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder

class TicketTagLib {
    def userService
    def tagService

    static namespace = "ticket"

    def state = { attrs ->
        def states = [PENDING: "PENDIENTE", PROCESS: "PROCESO", CLOSED: "CERRADO"]

        out << states[attrs.state]
    }

    def renderDepartments = { attrs, body ->
        def rest = new RestBuilder()
        def resp = rest.get("http://localhost:9090/departments")
        def data = resp.json.groupBy { it.area }.collect {
            [ area: it.key, data: it.value.name ]
        }

        out << render(template: "/templates/departments", model: [departments: data])
    }

    def usersByRole = { attrs ->
        def users = userService.getUsersByRole(attrs.role)

        out << render(template: "/templates/usersByRole", model: [users: users])
    }

    def getTags = {
        def tags = tagService.getAll()

        out << render(template: "/templates/tags", model: [tags: tags])
    }
}
