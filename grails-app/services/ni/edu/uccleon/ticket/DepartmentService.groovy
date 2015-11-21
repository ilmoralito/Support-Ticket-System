package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class DepartmentService {
    def departmentsQueryUrl

    def getDepartments() {
        def rest = new RestBuilder()
        def departments = rest.get(departmentsQueryUrl).json
        def data = departments.groupBy { it.area }.collect { department ->
            [ area: department.key, data: department.value.name ]
        }

        data
    }
}
