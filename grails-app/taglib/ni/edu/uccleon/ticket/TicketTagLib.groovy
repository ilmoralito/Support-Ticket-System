package ni.edu.uccleon.ticket

class TicketTagLib {
    def userService
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

    def getTags = { attrs ->
        def tags = Tag.list()
        def tagList = attrs.tagList

        out << render(template: "/templates/tags", model: [tags: tags, tagList: tagList])
    }

    def taskStatus = { attrs ->
        def status = attrs.status ? "ATENDIDO" : "PENDIENTE"

        out << status
    }

    def getAssistanceTags = { attrs ->
        def tags = attrs.tags
        def mb = new groovy.xml.MarkupBuilder(out)
        
        mb.span{
            tags.each{ tag ->
                span(class: "label") { mb.yield "${tag.name}" }
            }
        }
    }
}
