package ni.edu.uccleon.ticket

class AssistanceInterceptor {
    AssistanceInterceptor() {
        match action: "updateAttendedBy"
        match action: "addTags"
    }

    boolean before() {
        def assistance = Assistance.get params?.id

        if (assistance && assistance.state == "CLOSED") {
            redirect action: "binnacle", id: params.id

            return false
        }

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
