package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_ADMIN")
class UserController {

    def index() {
        [users: User.list()]
    }
}
