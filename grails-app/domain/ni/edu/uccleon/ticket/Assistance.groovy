package ni.edu.uccleon.ticket

class Assistance {
    String department
    String description
    User attendedBy
    Date dateCompleted

    Date dateCreated
    Date lastUpdated

    static constraints = {
        department blank: false
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

    }

    static belongsTo = [user: User]

    String toString() { "$user.fullName in $department" }
}
