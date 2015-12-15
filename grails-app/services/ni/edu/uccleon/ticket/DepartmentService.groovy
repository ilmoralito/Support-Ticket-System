package ni.edu.uccleon.ticket

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class DepartmentService {
    def departmentsQueryUrl

    def getDepartmentAreas() {
        [ Academic: "Academico", Administrative: "Administrativo" ]
    }

    def getDepartments(Integer max = 25) {
        def rest = new RestBuilder()
        def json = rest.get("$departmentsQueryUrl?max=$max").json
        def departments = json.groupBy { it.area }.collect { department ->
            [ area: this.departmentAreas[department.key], departments: department.value.name ]
        }

        departments
    }
}
