package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService

    def index() {
        def user = springSecurityService.currentUser
        def authorities = user.authorities.authority

        [
            assistances: authorities.contains("ROLE_USER") ? Assistance.findAllByUserAndAttendedByIsNull(user) : [],
            adminUsers: UserRole.findAllByRole(Role.findByAuthority("ROLE_ADMIN")).user,
            authorities: authorities
        ]
    }
}
