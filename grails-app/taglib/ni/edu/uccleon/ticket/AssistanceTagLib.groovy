package ni.edu.uccleon.ticket

class AssistanceTagLib {
	static namespace = "assistance"

	def dateCompletedStatus = { attrs ->
		def status = attrs.status ? "ABRIR" : "CERRAR"

		out << status
	}
}
