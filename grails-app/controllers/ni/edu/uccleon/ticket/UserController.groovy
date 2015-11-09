package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_ADMIN")
class UserController {
    static allowedMethods = [
        index: "GET",
        create: ["GET", "POST"]
    ]

    def index() {
        [users: User.list()]
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
