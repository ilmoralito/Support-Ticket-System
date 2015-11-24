package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService
    def userService

    static allowedMethods = [
        index: ["GET", "POST"],
        create: ["GET", "POST"],
        edit: "GET",
        update: "POST",
        delete: "GET",
        application: ["GET", "POST"],
        binnacle: ["GET", "POST"],
        updateAttendedBy: "GET",
        addTags: "POST"
    ]

    def index() {
        def user = springSecurityService.currentUser

        def assistances = {
            if (request.method == "POST") {
                def state = params.list("state")
                def attendedBy = params.list("attendedBy").collect { User.get it }

                Assistance.byCurrentUser().filter(state, attendedBy).list()
            } else {
                Assistance.byCurrentUser().inState("PENDING").list()
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

        if (assistance.state == "PENDING") {
            assistance.delete()

            flash.message = "Solicitud eliminada"
        }

        redirect action: "index"
    }

    @Secured(["ROLE_ADMIN"])
    def application() {
        def assistances = Assistance.inState(["PENDING", "PROCESS"]).list().groupBy { it.dateCreated.clearTime() }.collect {
            [dateCreated: it.key, assistances: it.value]
        }

        [assistances: assistances]
    }

    @Secured(["ROLE_ADMIN"])
    def binnacle(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        def isAttendedByCurrentUser = {
            assistance.attendedBy.user.contains(springSecurityService.currentUser)
        }

        [assistance: assistance, isAttendedByCurrentUser: isAttendedByCurrentUser()]
    }

    @Secured(["ROLE_ADMIN"])
    def updateAttendedBy(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        //this should be in a service
        def currentUser = springSecurityService.currentUser

        if (assistance.isAttendedBy(currentUser)) {
            def attendedBy = AttendedBy.where { assistance == assistance && user == currentUser }.get()
            assistance.removeFromAttendedBy attendedBy
        } else {
            assistance.addToAttendedBy new AttendedBy(currentUser)
        }

        redirect action: "binnacle", id: id
    }

    @Secured(["ROLE_ADMIN"])
    def addTags(Long id) {
        def assistance = Assistance.get id
        def tags = params.list("tags")

        if (!assistance) {
            response.sendError 404
        }

        // Delete current tags
        def tempTags = []

        tempTags.addAll assistance.tags

        tempTags.each { tag ->
            assistance.removeFromTags tag
        }

        // Add new Tags
        tags.each { tag ->
            def tagInstance = Tag.findByName tag

            assistance.addToTags tagInstance
        }

        assistance.save()

        redirect controller: "assistance", action: "binnacle", id: id
    }
}
