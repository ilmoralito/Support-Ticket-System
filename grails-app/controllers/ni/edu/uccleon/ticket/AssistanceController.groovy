package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import static org.asciidoctor.Asciidoctor.Factory.create;
import org.asciidoctor.Asciidoctor;

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
        addTags: "POST",
        setOrUnsetDateCompleted: "GET"
    ]

    def index() {
        def user = springSecurityService.currentUser

        def assistances = {
            if (request.method == "POST") {
                def state = params.list("state")
                def attendedBy = params.list("attendedBy").collect { User.get it }

                Assistance.byCurrentUser().filter(state, attendedBy).list()
            } else {
                Assistance.byCurrentUser().inState(["PENDING", "PROCESS"]).list()
            }
        }

        [
            assistances: assistances(),
            user: user
        ]
    }

    def create() {
        def assistance = new Assistance(params)

        if (request.method == "POST") {
            def user = springSecurityService.currentUser

            user.addToAssistances assistance

            if (!assistance.save()) {
                assistance.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]" }
                return [assistance: assistance]
            }

            flash.message = "Creada ticket de mantenimiento"
            redirect action: "index"
        }

        [assistance: assistance]
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
        def assistances = {
            if (request.method == "POST") {
                def states = params.list("states")

                Assistance.filter(states).list()
            } else {
                Assistance.inState(["PENDING", "PROCESS"]).list()
            }
        }

        [assistances: assistances().groupBy { it.dateCreated.clearTime() }.collect { [dateCreated: it.key, assistances: it.value] }]
    }

    @Secured(["ROLE_ADMIN"])
    def binnacle(Long id) {
        Assistance assistance = Assistance.get id
        Asciidoctor asciidoctor = create();

        if (!assistance) {
            response.sendError 404
        }

        [
            assistance: assistance,
            isAttendedByCurrentUser: AttendedBy.exists(id, springSecurityService.loadCurrentUser().id),
            tasks: assistance.tasks,
            asciidoctor: asciidoctor
        ]
    }

    @Secured(["ROLE_ADMIN"])
    def updateAttendedBy(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        def currentUser = springSecurityService.currentUser
        def flag = false

        if (AttendedBy.exists(assistance.id, currentUser.id)) {
            flag = true
            AttendedBy.remove assistance, currentUser
        } else {
            AttendedBy.create assistance, currentUser
        }

        assistance.properties["state"] = flag ? "PENDING" : "PROCESS"
        assistance.save()

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

        redirect controller: "assistance", action: "binnacle", id: id, fragment: "listTag"
    }

    @Secured(["ROLE_ADMIN"])
    def setOrUnsetDateCompleted(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        assistance.properties["dateCompleted"] = !assistance.dateCompleted ? new Date() : null

        if (!assistance.save()) {
            assistance.errors.allErrors.each { err ->
                log.error "[$err.field: $err.defaultMessage]"
            }
        }

        redirect action: "binnacle", id: id
    }
}
