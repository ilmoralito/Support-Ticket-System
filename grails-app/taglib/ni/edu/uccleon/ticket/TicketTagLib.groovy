package ni.edu.uccleon.ticket

class TicketTagLib {
    def userService
    def departmentService

    static namespace = "ticket"

    def state = { attrs ->
        def states = [PENDING: "PENDIENTE", PROCESS: "PROCESO", CLOSED: "CERRADO"]

        out << states[attrs.state]
    }

    def renderDepartments = { attrs ->
        List departments = departmentService.departments
        List departmentList = attrs.departmentList
        Map departmentsNickname = [ Academic: "Academico", Administrative: "Administrativo" ]
        Map checkboxParams = [ type: "checkbox", name: "departments" ]
        def mb = new groovy.xml.MarkupBuilder(out)

        departments.each { department ->
            mb.h6 { yield departmentsNickname[department.area] }

            department.departments.each { d ->
                checkboxParams.value = d

                d in departmentList ? checkboxParams.checked = true : checkboxParams.remove("checked")

                mb.div {
                    input(checkboxParams)
                    label(for: d) { yield d }
                }
            }
        }
    }

    def usersByRole = { attrs ->
        List userList = attrs.userList*.toLong()
        List users = userService.getUsersByRole(attrs.role)
        Map checkboxParams = [ type: "checkbox", name: attrs.name ]
        def mb = new groovy.xml.MarkupBuilder(out)

        users.each { user ->
            checkboxParams.value = user.id
            checkboxParams.id = user.id

            user.id in userList ? checkboxParams.checked = true : checkboxParams.remove("checked")

            mb.div {
                input(checkboxParams)
                label(for: user.id) { yield user.fullName }
            }
        }
    }

    def getTags = { attrs ->
        def tags = Tag.list()
        def tagList = attrs.tagList
        def checkboxParams = [type: "checkbox", name: "tags"]
        def mb = new groovy.xml.MarkupBuilder(out)

        tags.each { tag ->
            mb.div {
                checkboxParams.value = tag.name

                tag.name in tagList ? checkboxParams.checked = true : checkboxParams.remove("checked")

                input(checkboxParams)
                label(for: tag.name) { yield tag.name }
            }
        }
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
