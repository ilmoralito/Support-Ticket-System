package ni.edu.uccleon.ticket

class TagInterceptor {
    TagInterceptor() {
        match(action: "save")
    }

    boolean before() {
        Assistance assistance = Assistance.get params?.assistanceId

        if (assistance && assistance.state == "CLOSED") {
            log.info "Accion no permitida"

            redirect controller: "assistance", action: "binnacle", id: params?.assistanceId
            return false
        }

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
