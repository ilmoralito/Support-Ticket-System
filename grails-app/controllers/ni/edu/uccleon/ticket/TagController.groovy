package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TagController {
    static allowedMethods = [
        save: "POST"
    ]

    def save() {
        
    }
}
