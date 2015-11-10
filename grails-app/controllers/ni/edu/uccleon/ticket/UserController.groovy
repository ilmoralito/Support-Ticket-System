package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import grails.core.GrailsApplication

@Secured("ROLE_ADMIN")
class UserController {
    GrailsApplication grailsApplication
    UserService userService

    static allowedMethods = [
        index: ["GET", "POST"],
        create: ["GET", "POST"]
    ]

    def index() {
        def roles = params.list("roles").collect { role -> Role.findByAuthority(role) }
        def enabledStatus = params.list("enabledStatus")
        def data = userService.filter roles, enabledStatus

        [
            users: (roles || enabledStatus) ? data : User.findAllByEnabled true,
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

                roles.each { role ->
                    def roleInstance = Role.findByAuthority role

                    UserRole.create user, roleInstance, true
                }

                flash.message = "Agregado correctament"
            } else {
                flash.message = "Selecciona al menos un rol"
            }

            //TODO: why do i need to redirect?
            redirect action: "create"
        }
    }
}
