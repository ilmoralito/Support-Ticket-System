package ni.edu.uccleon.ticket

class Assistance {
    String description
    User attendedBy
    String state = "PENDING"
    Date dateCompleted

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description blank: false, maxSize: 140
        attendedBy nullable: true, validator: { attendedBy->
            attendedBy?.authorities?.authority?.contains("ROLE_ADMIN")
        }
        state inList: ["PENDING", "PROCESS", "CLOSED"], maxSize: 140
        dateCompleted nullable: true, validator: { dateCompleted ->
            if (dateCompleted) {
                dateCompleted >= new Date()
            }
        }
    }

    static mapping = {
        version false
        sort dateCreated: "asc"
    }

    static namedQueries = {
        byCurrentUser {
            eq "user", domainClass.application.mainContext.springSecurityService.currentUser
        }

        inState { states ->
            if (states instanceof List) {
                "in" "state", states
            } else {
                eq "state", states
            }
        }

        filter { state, attendedBy ->
            if (state) {
                "in" "state", state
            }

            if (attendedBy) {
                "in" "attendedBy", attendedBy
            }
        }
    }

    static belongsTo = [user: User]

    def beforeUpdate() {
        if (attendedBy && dateCompleted) {
            state = "CLOSED"
        }

        if (attendedBy) {
            state = "PROCESS"
        }
    }

    String toString() { "$user.fullName in $user.departments" }
}
