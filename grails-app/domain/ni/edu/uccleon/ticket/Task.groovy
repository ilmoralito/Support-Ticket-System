package ni.edu.uccleon.ticket

import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class Task {
    Date dateCreated
    Date lastUpdated
    String description
    Boolean status = false

    static constraints = {
        description blank: false
    }

    static belongsTo = [assistance: Assistance]

    static mapping = { version false }
}
