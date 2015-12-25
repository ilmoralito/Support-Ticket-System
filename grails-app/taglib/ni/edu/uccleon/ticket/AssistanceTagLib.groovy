package ni.edu.uccleon.ticket

import static org.asciidoctor.Asciidoctor.Factory.create;
import org.asciidoctor.Asciidoctor;

class AssistanceTagLib {
    static namespace = "assistance"

    static Asciidoctor asciidoctor = create();

    def dateCompletedStatus = { attrs ->
        def status = attrs.status ? "ABRIR" : "CERRAR"

        out << status
    }

    def renderFromAsciidoctorToHTML = { attrs ->
        String description = attrs.description

        out << asciidoctor.convert(description, new HashMap<String, Object>())
    }

    def getState = { attrs ->
        def states = [PENDING: "PENDIENTE", PROCESS: "PROCESO", CLOSED: "CERRADO"]

        out << states[attrs.state]
    }
}
