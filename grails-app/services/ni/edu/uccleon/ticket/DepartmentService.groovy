package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class DepartmentService {
    def departmentsQueryUrl

    def getDepartments(Integer max = 25) {
        def rest = new RestBuilder()
        def departments = rest.get("$departmentsQueryUrl?max=$max").json
        def data = departments.groupBy { it.area }.collect { department ->
            [ area: department.key, data: department.value.name ]
        }

        data
    }
}
