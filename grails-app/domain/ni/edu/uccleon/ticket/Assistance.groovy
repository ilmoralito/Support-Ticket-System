package ni.edu.uccleon.ticket

class Assistance {
    def springSecurityService

    String description
    User attendedBy
    Date dateCompleted

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description blank: false, maxSize: 140
        attendedBy nullable: true, validator: { attendedBy->
            attendedBy?.authorities?.authority?.contains("ROLE_ADMIN")
        }
        dateCompleted nullable: true, validator: { dateCompleted ->
            if (dateCompleted) {
                dateCompleted >= new Date()
            }
        }
    }

    static mapping = {
        version false
    }

    static namedQueries = {
        byCurrentUser { currentUser ->
            //def currentUser = this.springSecurityService.currentUser

            eq "user", currentUser
        }

        notAttended {
            isNull "attendedBy"
        }
    }

    static belongsTo = [user: User]

    String toString() { "$user.fullName in $user.departments" }
}
