import ni.edu.uccleon.ticket.*

class BootStrap {
    def init = { servletContext ->
      def adminRole = new Role("ROLE_ADMIN").save failOnError: true
      def userRole = new Role("ROLE_USER").save failOnError: true

      def admin1User = new User(fullName: "John Doe", email: "john.doe@ucc.edu.ni").save failOnError: true
      def admin2User = new User(fullName: "Juan Perez", email: "juan.perez@ucc.edu.ni").save failOnError: true
      def userUser = new User(fullName: "Fulano de Tal", email: "fulano.tal@ucc.edu.ni").save failOnError: true

      UserRole.create admin1User, adminRole, true
      UserRole.create admin2User, adminRole, true
      UserRole.create userUser, userRole, true

      assert User.count() == 3
      assert Role.count() == 2
      assert UserRole.count() == 3
    }
    def destroy = {
    }
}
