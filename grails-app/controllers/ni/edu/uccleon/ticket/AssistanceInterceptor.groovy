package ni.edu.uccleon.ticket

class AssistanceInterceptor {
    AssistanceInterceptor() {
        match action: "updateAttendedBy"
        match action: "addTags"
        match action: "delete"
    }

    boolean before() {
        def id = params.int("id")
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404

            return false
        }

        if (actionName == "delete" && assistance.state in ["PROCESS", "CLOSED"]) {
            redirect action: "binnacle", id: para
        }

        if (actionName in ["updateAttendedBy", "addTags"] && assistance.state == "CLOSED") {
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
