package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService

    static allowedMethods = [
        index: ["GET", "POST"],
        create: ["GET", "POST"],
        edit: "GET",
        update: "POST",
        delete: "GET"
    ]

    def index() {
        def user = springSecurityService.currentUser

        def assistances = {
            if (request.method == "POST") {
                def state = params.list("state")
                def attendedBy = params.list("attendedBy").collect { User.get it }

                Assistance.byCurrentUser().filter(state, attendedBy).list()
            } else {
                Assistance.byCurrentUser().notAttended.list()
            }
        }

        [
            assistances: assistances(),
            user: user
        ]
    }

    def create() {
        if (request.method == "POST") {
            def user = springSecurityService.currentUser
            def assistance = new Assistance(params)

            user.addToAssistances assistance

            if (!assistance.save()) {
                assistance.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]" }
                return [assistance: assistance]
            }

            flash.message = "Creada ticket de mantenimiento"
            redirect action: "index"
        }
    }

    def edit(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        [assistance: assistance]
    }

    def update(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        assistance.properties["description"] = params

        if (!assistance.save()) {
            assistance.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]" }
            flash.message = "A ocurrido un error. Verificar datos"
        }

        redirect action: "edit", params: [id: id]
    }

    def delete(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        if (!assistance?.attendedBy) {
            assistance.delete()

            flash.message = "Solicitud eliminada"
        }

        redirect action: "index"
    }
}