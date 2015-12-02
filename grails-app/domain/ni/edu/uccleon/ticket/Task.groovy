package ni.edu.uccleon.ticket

import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class Task {
    User user
    Date dateCreated
    Date lastUpdated
    String description
    Boolean status = false

    Task(User user, Assistance assistance, String description) {
        this()
        this.user = user
        this.assistance = assistance
        this.description = description
    }

    Task(User user, String description, Boolean status = false) {
        this()
        this.user = user
        this.description = description
        this.status = status
    }

    static constraints = {
        description blank: false, maxSize: 1500
    }

    static belongsTo = [assistance: Assistance]

    static mapping = { version false }

    static Task create(User user, Assistance assistance, String description) {
        new Task(user, assistance, description).save flush: true
    }

    static Boolean remove(Long id) {
        def count = Task.where { id == id }.deleteAll()

        count
    }
}
