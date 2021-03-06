package ni.edu.uccleon.ticket

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

@ToString(cache=true, includeNames=true, includePackage=false)
class AttendedBy implements Comparable {
    User user

    static constraints = {
        user validator: { user ->
            user.authorities.authority.contains("ROLE_ADMIN")
        }
    }

    AttendedBy(Assistance assistance, User user) {
        this()
        this.assistance = assistance
        this.user = user
    }

    private static DetachedCriteria criteriaFor(long assistanceId, long userId) {
        AttendedBy.where {
            assistance == Assistance.load(assistanceId) && user == User.load(userId)
        }
    }

    static AttendedBy get(long assistanceId, long userId) {
        criteriaFor(assistanceId, userId).get()
    }

    static Boolean exists(long assistanceId, long userId) {
        criteriaFor(assistanceId, userId).count()
    }

    static AttendedBy create(Assistance assistance, User user) {
        def attendedByInstance = new AttendedBy(assistance, user).save(flush: true)

        attendedByInstance
    }

    static boolean remove(Assistance assistance, User user) {
        Integer count = AttendedBy.where { assistance == assistance && user == user }.deleteAll()

        count
    }

    int compareTo(obj) {
        user.email.compareTo(obj.user.email)
    }

    static belongsTo = [assistance: Assistance]

    static mapping = { version false }
}
