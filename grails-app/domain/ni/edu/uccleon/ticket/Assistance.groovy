package ni.edu.uccleon.ticket

class Assistance {
    String description
    String state = "PENDING"
    Date dateCompleted

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description blank: false, maxSize: 140
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
    static hasMany = [tags: Tag, tasks: Task]

    Set<AttendedBy> getAttendedBy() {
        AttendedBy.findAllByAssistance(this)
    }

    def beforeUpdate() {
        if (dateCompleted) {
            state = "CLOSED"
        }

        if (attendedBy) {
            state = "PROCESS"
        }
    }

    String toString() { "$user.fullName in $user.departments" }
}
