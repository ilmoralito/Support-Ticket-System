package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TaskController {
    static allowedMethods = [
        save: "POST",
        delete: "GET"
    ]

    def save(Integer assistanceId) {
        def assistance = Assistance.get assistanceId

        if (!assistance) {
            response.sendError 404
        }

        if (!Task.create(assistance, params.description)) {
            flash.message = "A ocurrido un error"
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId
    }

    def delete(Long id, Long assistanceId) {
        def task = Task.get id

        if (!task) {
            response.sendError 404
        }

        if (!Task.remove(id)) {
            flash.message = "A ocurrido un error"
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId
    }
}
