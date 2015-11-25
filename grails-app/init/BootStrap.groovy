import ni.edu.uccleon.ticket.*
import grails.plugins.rest.client.RestBuilder

class BootStrap {
	def init = { servletContext ->
		def rest = new RestBuilder()
		def resp = rest.get("http://localhost:9090/departments")

		def adminRole = new Role("ROLE_ADMIN").save failOnError: true
		def userRole = new Role("ROLE_USER").save failOnError: true

		def admin1User = new User(
			fullName: "John Doe",
			email: "john.doe@ucc.edu.ni",
			departments: resp.json[0].name
		).save failOnError: true

		def admin2User = new User(
			fullName: "Juan Perez",
			email: "juan.perez@ucc.edu.ni",
			departments: resp.json[1].name
		).save failOnError: true

		def userUser = new User(
			fullName: "Fulano de Tal",
			email: "fulano.tal@ucc.edu.ni",
			departments: [resp.json[2].name, resp.json[3].name]
		).save failOnError: true

		def testUser = new User(
			fullName: "test user",
			email: "test.user@ucc.edu.ni",
			departments: resp.json[0].name,
			enabled: false
		).save failOnError: true

		UserRole.create admin1User, adminRole, true
		UserRole.create admin2User, adminRole, true
		UserRole.create userUser, userRole, true
		UserRole.create testUser, userRole, true

		assert User.count() == 4
		assert Role.count() == 2
		assert UserRole.count() == 4

		// assistance
		def assistance1 = new Assistance(
			description: "Lorem ipsum dolor sip ament"
		)

		userUser.addToAssistances assistance1
		assistance1.save failOnError: true

		def assistance2 = new Assistance(
			description: "Blah bla bla yakiris markiris"
		)

		userUser.addToAssistances assistance2
		assistance2.save failOnError: true

		def assistance3 = new Assistance(
			description: "grails_plugin_springsecurity_successHandler_defaultTargetUrl"
		)

		userUser.addToAssistances assistance3
		assistance3.save failOnError: true


		AttendedBy.create assistance3, admin1User
		AttendedBy.create assistance3, admin2User

		def assistance4 = new Assistance(
			description: """
				As well as associations
				between different domain classes,
				GORM also supports mapping of basic collection types.
				"""
		)

		userUser.addToAssistances assistance4
		assistance4.save failOnError: true

		AttendedBy.create assistance4, admin1User

		assert Assistance.count() == 4

		// tag
		def printer = new Tag(name:"Impresora").save failOnError: true
		def battery = new Tag(name:"Bateria").save failOnError: true
		def monitor = new Tag(name:"Monitor").save failOnError: true
		def network = new Tag(name:"Red").save failOnError: true
	}
	def destroy = {
	}
}
