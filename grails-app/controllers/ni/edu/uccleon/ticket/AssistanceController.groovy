package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.rest.client.RestBuilder

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService

    def index() {
        def rest = new RestBuilder()
        def departments = rest.get("http://localhost:9090/departments")
        def user = springSecurityService.currentUser
        def authorities = user.authorities.authority

        [
            assistances: authorities.contains("ROLE_USER") ? Assistance.findAllByUserAndAttendedByIsNull(user) : [],
            departments: departments.json,
            adminUsers: UserRole.findAllByRole(Role.findByAuthority("ROLE_ADMIN")).user,
            authorities: authorities
        ]
    }
}
