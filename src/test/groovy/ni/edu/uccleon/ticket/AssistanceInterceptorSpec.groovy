package ni.edu.uccleon.ticket


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(AssistanceInterceptor)
class AssistanceInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test assistance interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"assistance")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
