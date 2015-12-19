package ni.edu.uccleon.ticket

import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class Assistance {
    def assistanceService

    String description
    String department
    String state = "PENDING"
    String type
    Date dateCompleted
    SortedSet tags

    Date dateCreated
    Date lastUpdated

    static constraints = {
        description blank: false, maxSize: 140
        department blank: false, validator: { department, assistance ->
            assistance.assistanceService.departmentInUserDepartments(department, assistance)
        }
        state inList: ["PENDING", "PROCESS", "CLOSED"], maxSize: 140
        type inList: ["PROGRAMMED", "NON-SCHEDULED"], maxSize: 100
        dateCompleted nullable: true, validator: { dateCompleted, assistance ->
            if (dateCompleted) {
                assistance.assistanceService.validateDateCompleted(dateCompleted, assistance)
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

        byState { stateList ->
            "in" "state", stateList
        }

        filter { stateList, attendedByList, departmentList, tagList, typeList, userList ->
            if (stateList) {
                byState stateList
            }

            if (attendedByList) {
                attendedBy {
                    user {
                        "in" "id", attendedByList
                    }
                }
            }

            if (departmentList) {
                "in" "department", departmentList
            }

            if (tagList) {
                tags {
                    "in" "name", tagList
                }
            }

            if (typeList) {
                "in" "type", typeList
            }

            if (userList) {
                user {
                    "in" "id", userList
                }
            }
        }
    }

    static belongsTo = [user: User]

    static hasMany = [tags: Tag, tasks: Task, attendedBy: AttendedBy]

    def beforeValidate() {
        department = user.departments[0]
        type = user.authorities.authority.contains("ROLE_ADMIN") ? "PROGRAMMED" : "NON-SCHEDULED"
    }

    def beforeUpdate() {
        if (isDirty("dateCompleted")) {
            state = dateCompleted ? "CLOSED" : "PROCESS"
        }
    }
}
