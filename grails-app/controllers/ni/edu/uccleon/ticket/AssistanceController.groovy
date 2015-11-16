package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService

    static allowedMethods = [
        index: "GET",
        create: ["GET", "POST"]
    ]

    def index() {
        def user = springSecurityService.currentUser
        def authorities = user.authorities.authority

        [
            assistances: authorities.contains("ROLE_USER") ? Assistance.findAllByUserAndAttendedByIsNull(user) : [],
            adminUsers: UserRole.findAllByRole(Role.findByAuthority("ROLE_ADMIN")).user,
            authorities: authorities
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
}
