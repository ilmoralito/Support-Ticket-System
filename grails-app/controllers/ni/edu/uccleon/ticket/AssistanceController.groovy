package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService

    def index() {
        def user = springSecurityService.currentUser
        def authorities = user.authorities.authority
        def assistances
        
        if (authorities.contains("ROLE_USER")) {
            assistances = Assistance.findAllByUserAndAttendedByIsNull(user)
        }

        [assistances: assistances]
    }
}
