package ni.edu.uccleon.ticket

import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class Task {
    Date dateCreated
    Date lastUpdated
    String description
    Boolean status = false

    Task(Assistance assistance, String description) {
        this()
        this.assistance = assistance
        this.description = description
    }

    Task(String description) {
        this()
        this.description = description
    }

    static constraints = {
        description blank: false, maxSize: 1500
    }

    static belongsTo = [assistance: Assistance]

    static mapping = { version false }

    static Task create(Assistance assistance, String description) {
        new Task(assistance, description).save flush: true
    }
}
