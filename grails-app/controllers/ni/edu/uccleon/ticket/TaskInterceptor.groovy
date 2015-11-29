package ni.edu.uccleon.ticket

class TaskInterceptor {
    def springSecurityService

    TaskInterceptor() {
        match(action: "updateStatus")
        match(action: "delete")
    }

    boolean before() {
        def assistanceId = params.int("assistanceId")
        def userId = springSecurityService.loadCurrentUser().id
        def isAttendedByCurrentUser = AttendedBy.exists(assistanceId, userId)

        if (!isAttendedByCurrentUser) {
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
