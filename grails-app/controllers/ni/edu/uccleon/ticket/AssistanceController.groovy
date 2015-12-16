package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import com.craigburke.document.builder.PdfDocumentBuilder

@Secured(["ROLE_ADMIN", "ROLE_USER"])
class AssistanceController {
    def springSecurityService
    def userService
    def assistanceService
    DepartmentService departmentService

    static allowedMethods = [
        index: ["GET", "POST"],
        create: ["GET", "POST"],
        edit: "GET",
        update: "POST",
        delete: "GET",
        application: ["GET", "POST"],
        binnacle: ["GET", "POST"],
        updateAttendedBy: "GET",
        addTags: "POST",
        setOrUnsetDateCompleted: "GET",
        resume: "GET",
        printResume: "GET",
        resumeDetail: "GET",
        printResumeDetail: "GET",
        printResumeDetailByDepartment: "GET"
    ]

    def index() {
        def user = springSecurityService.currentUser

        def assistances = {
            if (request.method == "POST") {
                def states = params.list("states")
                def attendedBy = params.list("attendedBy")*.toLong()

                Assistance.byCurrentUser().filter(states, attendedBy, null, null, null, null).list()
            } else {
                Assistance.byCurrentUser().byState(["PENDING", "PROCESS"]).list()
            }
        }

        [
            assistances: assistances(),
            user: user
        ]
    }

    def create() {
        def assistance = new Assistance(params)

        if (request.method == "POST") {
            def user = springSecurityService.currentUser

            user.addToAssistances assistance

            if (!assistance.save()) {
                assistance.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]" }
                return [assistance: assistance]
            }

            flash.message = "Creada ticket de mantenimiento"
            redirect action: "index"
        }

        [assistance: assistance]
    }

    def edit(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        [assistance: assistance]
    }

    def update(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        assistance.properties["description"] = params

        if (!assistance.save()) {
            assistance.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]" }
            flash.message = "A ocurrido un error. Verificar datos"
        }

        redirect action: "edit", params: [id: id]
    }

    def delete(Integer id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        if (assistance.state == "PENDING") {
            assistance.delete()

            flash.message = "Solicitud eliminada"
        }

        redirect action: "index"
    }

    @Secured(["ROLE_ADMIN"])
    def application() {
        def assistances = {
            if (request.method == "POST") {
                def states = params.list("states")
                def attendedBy = params.list("attendedBy")*.toLong()
                def departments = params.list("departments")
                def tags = params.list("tags")
                def types = params.list("types")
                def users = params.list("users")*.toLong()

                Assistance.filter(states, attendedBy, departments, tags, types, users).list()
            } else {
                Assistance.byState(["PENDING", "PROCESS"]).list()
            }
        }

        [assistances: assistances()?.groupBy { it.dateCreated.clearTime() }.collect { [dateCreated: it.key, assistances: it.value] }]
    }

    @Secured(["ROLE_ADMIN"])
    def binnacle(Long id) {
        Assistance assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        [
            assistance: assistance,
            isAttendedByCurrentUser: AttendedBy.exists(id, springSecurityService.loadCurrentUser().id),
            tasks: assistance.tasks
        ]
    }

    @Secured(["ROLE_ADMIN"])
    def updateAttendedBy(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        def currentUser = springSecurityService.currentUser
        def flag = false

        if (AttendedBy.exists(assistance.id, currentUser.id)) {
            flag = true
            AttendedBy.remove assistance, currentUser
        } else {
            AttendedBy.create assistance, currentUser
        }

        assistance.properties["state"] = flag ? "PENDING" : "PROCESS"
        assistance.save()

        redirect action: "binnacle", id: id
    }

    @Secured(["ROLE_ADMIN"])
    def addTags(Long id) {
        def assistance = Assistance.get id
        def tags = params.list("tags")

        if (!assistance) {
            response.sendError 404
        }

        // Delete current tags
        def tempTags = []

        tempTags.addAll assistance.tags

        tempTags.each { tag ->
            assistance.removeFromTags tag
        }

        // Add new Tags
        tags.each { tag ->
            def tagInstance = Tag.findByName tag

            assistance.addToTags tagInstance
        }

        assistance.save()

        redirect controller: "assistance", action: "binnacle", id: id, fragment: "listTag"
    }

    @Secured(["ROLE_ADMIN"])
    def setOrUnsetDateCompleted(Long id) {
        def assistance = Assistance.get id

        if (!assistance) {
            response.sendError 404
        }

        assistance.properties["dateCompleted"] = !assistance.dateCompleted ? new Date() : null

        if (!assistance.save()) {
            assistance.errors.allErrors.each { err ->
                log.error "[$err.field: $err.defaultMessage]"
            }
        }

        redirect action: "binnacle", id: id
    }

    @Secured(["ROLE_ADMIN"])
    def resume() {
        [assistances: assistanceService.resume]
    }

    @Secured(["ROLE_ADMIN"])
    def printResume() {
        List assistances = assistanceService.resume
        PdfDocumentBuilder pdfBuilder = new PdfDocumentBuilder(response.outputStream)

        Closure customTemplate = {
            "document" font: [ family: "Helvetica", size: 8.pt]
        }

        pdfBuilder.create {
            document (
                template: customTemplate,
                header: { info ->
                    paragraph "Impreso: ${info.dateGenerated.format('yyyy-MM-dd hh:mm')}"
                }
            ) {
                assistances.each { assistance ->
                    paragraph(align: "center", margin: [top: 0.inches]) {
                        text "RESUMEN ASISTENCIAS ${assistance.year}"
                    }

                    table(margin: [top: 0.inches, bottom: 0.inches]) {
                        assistance.months.each { m ->
                            row {
                                cell "Mes"
                                cell "Programados"
                                cell "No programados"
                                cell "Total"
                            }

                            row {
                                cell m.month
                                cell m.programmed
                                cell m.nonscheduled
                                cell m.total
                            }
                        }

                        row {
                            cell "TOTAL"
                            cell assistance.months.programmed.sum()
                            cell assistance.months.nonscheduled.sum()
                            cell assistance.months.programmed.sum() + assistance.months.nonscheduled.sum()
                        }
                    }
                }
            }
        }

        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename=adiosMuchachos.pdf")
        response.outputStream << out.toByteArray()
        response.outputStream.flush()
    }

    @Secured(["ROLE_ADMIN"])
    def resumeDetail(Integer year, String month) {
        List byDepartments = assistanceService.getResumeDetail(year, month)

        [byDepartments: byDepartments]
    }

    @Secured(["ROLE_ADMIN"])
    def printResumeDetail(Integer year, String month) {
        List byDepartments = assistanceService.getResumeDetail(year, month)
        PdfDocumentBuilder pdfBuilder = new PdfDocumentBuilder(response.outputStream)
        Closure customTemplate = {
            "document" font: [ family: "Helvetica", size: 8.pt]
            "cell.th" font: [bold: true, size: 5.pt]
        }

        pdfBuilder.create {
            document (
                template: customTemplate,
                header: { info ->
                    paragraph "Impreso: ${info.dateGenerated.format('yyyy-MM-dd hh:mm')}"
                }
            ) {
                heading3 "DETALLE ${month?.toUpperCase()} ${year}..."

                table(margin: [top: 0.inches, bottom: 0.inches]) {
                    byDepartments.each { department ->
                        row {
                            cell department.area.toUpperCase(), style: "th"
                            cell "PROGRAMADOS", style: "th"
                            cell "NO PROGRAMADOS", style: "th"
                            cell "PENDIENTES", style: "th"
                            cell "PROCESO", style: "th"
                            cell "CERRADO", style: "th"
                        }

                        department.departments.each { d ->
                            row {
                                cell d.name
                                cell d.programmed
                                cell d.nonscheduled
                                cell d.pending
                                cell d.process
                                cell d.closed
                            }
                        }

                        row {
                            cell "TOTAL"
                            cell department.departments.sum { it.programmed }
                            cell department.departments.sum { it.nonscheduled }
                            cell department.departments.sum { it.pending }
                            cell department.departments.sum { it.process }
                            cell department.departments.sum { it.closed }
                        }
                    }
                }
            }
        }

        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename=caminito.pdf")
        response.outputStream << out.toByteArray()
        response.outputStream.flush()
    }

    @Secured(["ROLE_ADMIN"])
    def printResumeDetailByDepartment(Integer year, String month, String department) {
        List assistances = assistanceService.getResumeDetailByDepartment(year, month, department)
        PdfDocumentBuilder pdfBuilder = new PdfDocumentBuilder(response.outputStream)
        Closure customTemplate = {
            "document" font: [ family: "Helvetica", size: 8.pt]
        }

        pdfBuilder.create {
            document (
                template: customTemplate,
                header: { info ->
                    paragraph "Impreso: ${info.dateGenerated.format('yyyy-MM-dd hh:mm')}"
                }
            ) {
                heading3 "DETALLE ${month.toUpperCase()} $year $department"

                table(margin: [top: 0.inches, bottom: 0.inches]) {
                    assistances.each { assistance ->
                        row {
                            cell assistance.user, colspan: 6
                        }

                        row {
                            cell "Creado"
                            cell "Actualizado"
                            cell "Atendido por"
                            cell "Tipo"
                            cell "Estado"
                            cell "Categoria"
                        }

                        assistance.assistances.each { a ->
                            row {
                                cell a.dateCreated.format("MM-dd")
                                cell a.lastUpdated.format("MM-dd hh:mm")
                                cell a.attendedBy
                                cell assistanceService.types[a.type]
                                cell ticket.state(state: a.state)
                                cell a.tags
                            }
                        }
                    }
                }
            }
        }

        response.contentType = "application/pdf"
        response.setHeader("Content-disposition", "attachment;filename=elDiaQueMeQuieras.pdf")
        response.outputStream << out.toByteArray()
        response.outputStream.flush()
    }
}
