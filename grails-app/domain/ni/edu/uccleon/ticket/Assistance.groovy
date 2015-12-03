package ni.edu.uccleon.ticket

import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class Assistance {
    String description
    String state = "PENDING"
    Date dateCompleted
    SortedSet tags

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description blank: false, maxSize: 140
        state inList: ["PENDING", "PROCESS", "CLOSED"], maxSize: 140
        dateCompleted nullable: true, validator: { dateCompleted, assistance ->
            if (dateCompleted) {
                def now = new Date().clearTime()
                def validDate = dateCompleted >= now
                def taskNotAttended = assistance?.tasks?.countBy { task ->
                    task.status
                }.containsKey(false)

                if (!assistance.tasks || taskNotAttended || !assistance.tags || !validDate) {
                    return "not.allowed"
                }
            }
        }
    }

    Assistance(String description) {
        this()
        this.description = description
    }

    Assistance(String description, String state) {
        this()
        this.description = description
        this.state = state
    }

    static mapping = {
        version false
        sort dateCreated: "asc"
        tasks sort: "dateCreated", order: "desc"
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

        filter { states = null, attendedBy = null ->
            if (states) {
                "in" "state", states
            }
        }
    }

    static belongsTo = [user: User]

    static hasMany = [tags: Tag, tasks: Task]

    Set<AttendedBy> getAttendedBy() {
        AttendedBy.findAllByAssistance(this)
    }

    def beforeUpdate() {
        if (isDirty("dateCompleted")) {
            state = dateCompleted ? "CLOSED" : "PROCESS"
        }
    }
}
