package ni.edu.uccleon.ticket

import org.grails.databinding.BindUsing

class Tag implements Comparable {
    @BindUsing({
        obj, source -> source["name"]?.toLowerCase()?.tokenize(" ")*.capitalize().join(" ")
    })
    String name

    static constraints = {
        name blank: false, unique: true, validator: { name -> !name.contains(" ") }
    }

    static mapping = {
        version false
        sort "name"
    }

    static belongsTo = Assistance
    static hasMany = [assistances: Assistance]

    int compareTo(obj) {
        name.compareTo(obj.name)
    }

    String toString() { name }
}
