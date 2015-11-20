package ni.edu.uccleon.ticket

import org.grails.databinding.BindUsing

class Tag {
    @BindUsing({
        obj, source -> source["name"]?.toLowerCase()?.tokenize(" ")*.capitalize().join(" ")
    })
    String name

    static constraints = {
        name blank: false, unique: true
    }

    String toString() { name }
}
