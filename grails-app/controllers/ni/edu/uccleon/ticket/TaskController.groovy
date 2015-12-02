package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TaskController {
    def springSecurityService

    static allowedMethods = [
        save: "POST",
        delete: "GET",
        updateStatus: "GET"
    ]

    def save(Integer assistanceId) {
        def assistance = Assistance.get assistanceId

        if (!assistance) {
            response.sendError 404
        }

        if (!Task.create(springSecurityService.currentUser, assistance, params.description)) {
            flash.message = "A ocurrido un error"
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId
    }

    def delete(Long id, Long assistanceId) {
        def task = Task.get id

        if (!task) {
            response.sendError 404
        }

        if (task.status) {
            response.sendError 403
        } else {
            flash.message = Task.remove(id) ? "Tarea eliminada" : "A ocurrido un error"
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId
    }

    def updateStatus(Long id, Long assistanceId) {
        def task = Task.get id

        if (!task) {
            response.sendError 404
        }

        task.properties["status"] = !task.status

        if (!task.save()) {
            task.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]"}

            flash.message = "A ocurrido un error"
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId, fragment: id
    }
}
