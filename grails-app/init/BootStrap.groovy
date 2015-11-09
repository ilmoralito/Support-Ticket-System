import ni.edu.uccleon.ticket.*

class BootStrap {
    def init = { servletContext ->
      def adminRole = new Role("ROLE_ADMIN").save failOnError: true
      def admin1User = new User("me", "John Doe", "john.doe@ucc.edu.ni",  "password").save failOnError: true
      def admin2User = new User("i", "Juan Perez", "juan.perez@ucc.edu.ni", "password").save failOnError: true

      UserRole.create admin1User, adminRole, true
      UserRole.create admin2User, adminRole, true

      assert User.count() == 2
      assert Role.count() == 1
      assert UserRole.count() == 2
    }
    def destroy = {
    }
}
