package ni.edu.uccleon.ticket

class TaskInterceptor {
    def springSecurityService

    boolean before() {
        def assistanceId = params.int("assistanceId")
        def assistance = Assistance.get assistanceId
        def currentUser = springSecurityService.loadCurrentUser()
        def isAttendedByCurrentUser = AttendedBy.exists(assistanceId, currentUser.id)

        if (!isAttendedByCurrentUser || assistance.state == "CLOSED") {
            redirect controller: "assistance", action: "binnacle", id: assistanceId

            return false
        }

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
