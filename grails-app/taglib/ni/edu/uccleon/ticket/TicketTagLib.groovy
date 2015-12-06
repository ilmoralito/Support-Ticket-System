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

    def getAssistanceTypes = { attrs ->
        List types = attrs.types
        def mb = new groovy.xml.MarkupBuilder(out)
        Map checkboxParams = [type: "checkbox", id: "PROGRAMMED", name: "types", value: "PROGRAMMED"]
        Map assistanceTypeNicknames = [ PROGRAMMED: "Programado", "NON-SCHEDULED": "No programado"]

        if ("PROGRAMMED" in types) checkboxParams.checked = true

        mb.div {
            input(checkboxParams)
            label(for: "PROGRAMMED") { mb.yield assistanceTypeNicknames["PROGRAMMED"] }
        }

        mb.div {
            mkp.yieldUnescaped g.checkBox(type: "checkbox", id: "NON-SCHEDULED", name: "types", value: "NON-SCHEDULED", checked: "NON-SCHEDULED" in types)
            label(for: "NON-SCHEDULED") { mb.yield assistanceTypeNicknames["NON-SCHEDULED"] }
        }
    }
}
