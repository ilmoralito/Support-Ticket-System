package ni.edu.uccleon.ticket

class TicketTagLib {
    def userService
    def tagService
    def departmentService

    static namespace = "ticket"

    def state = { attrs ->
        def states = [PENDING: "PENDIENTE", PROCESS: "PROCESO", CLOSED: "CERRADO"]

        out << states[attrs.state]
    }

    def renderDepartments = { attrs, body ->
        def departments = departmentService.departments

        out << render(template: "/templates/departments", model: [departments: departments])
    }

    def usersByRole = { attrs ->
        def users = userService.getUsersByRole(attrs.role)

        out << render(template: "/templates/usersByRole", model: [users: users])
    }

    def getTags = {
        def tags = tagService.tags

        out << render(template: "/templates/tags", model: [tags: tags])
    }

    def usersFullName = { attrs ->
        out << userService.getUsersFullNameFromUsersEmail(attrs.attendedBy)
    }
}
