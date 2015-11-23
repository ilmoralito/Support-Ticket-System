package ni.edu.uccleon.ticket

class AttendedBy {
    User user

    static constraints = {
        user validator: { user ->
            user.authorities.authority.contains("ROLE_ADMIN")
        }
    }

    AttendedBy(User user) {
        this()
        this.user = user
    }

    static mapping = { version false }

    static belongsTo = [assistance: Assistance]
}
