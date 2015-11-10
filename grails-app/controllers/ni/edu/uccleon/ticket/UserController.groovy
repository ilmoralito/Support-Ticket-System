package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import grails.core.GrailsApplication

@Secured("ROLE_ADMIN")
class UserController {
    GrailsApplication grailsApplication
    UserService userService

    static allowedMethods = [
        index: ["GET", "POST"],
        create: ["GET", "POST"],
        edit: "GET",
        update: "POST"
    ]

    def index() {
        def roles = params.list("roles").collect { role -> Role.findByAuthority(role) }
        def enabledStatus = params.list("enabledStatus")
        def data = userService.filter roles, enabledStatus

        [
            users: (roles || enabledStatus) ? data : User.findAllByEnabled(true),
            rolesNickname: grailsApplication.config.ni.edu.uccleon.ticket.rolesNickname
        ]
    }

    def create() {
        if (request.method == "POST") {
            def roles = params.list("roles")

            if (roles) {
                def user = new User(params)

                if (!user.save()) {
                    user.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]"}
                    return [user: user]
                }

                userService.addRoles roles, user

                flash.message = "Agregado correctament"
            } else {
                flash.message = "Selecciona al menos un rol"
            }

            //TODO: why do i need to redirect?
            redirect action: "create"
        }
    }

    def edit(User user) {
        if (!user) {
            response.sendError 404
        }

        [user: user]
    }

    def update(User user) {
        if (!user) {
            response.sendError 404
        }

        //users
        user.fullName = params?.fullName
        user.email = params?.email
        user.enabled = params.boolean("enabled") ?: false

        //roles
        def roles = params.list("roles")
        if (roles) {
            UserRole.removeAll user, true
        }

        userService.addRoles roles, user

        flash.message = !user?.save() ? "A ocurrido un error. Verifica los datos" : "Edicion correcta"

        redirect action: "edit", id: user.id
    }
}
