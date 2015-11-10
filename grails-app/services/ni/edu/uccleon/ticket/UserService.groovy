package ni.edu.uccleon.ticket

import grails.transaction.Transactional

@Transactional
class UserService {

    def filter(List roles, List enabledStatus) {
        def data

        if (roles) {
            def query = UserRole.where { role in roles }

            data = query.list().user
        }

        if (enabledStatus) {
            if (!roles) {
                def query = User.where { enabled in enabledStatus*.toBoolean() }

                data = query.list()
            } else {
                def result = data.findAll { user -> user.enabled in enabledStatus*.toBoolean()}

                data = result
            }
        }

        data
    }

    def addRoles(List roles, User user) {
        roles.each { role ->
            def roleInstance = Role.findByAuthority role

            UserRole.create user, roleInstance, true
        }
    }
}
