package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured
import com.craigburke.document.builder.PdfDocumentBuilder

@Secured(["ROLE_ADMIN"])
class ReportController {
    AssistanceService assistanceService

    static allowedMethods = [
        resume: "GET",
        printResume: "GET",
        resumeDetail: "GET",
        printResumeDetail: "GET",
        printResumeDetailByDepartment: "GET",
        resumeByTag: "GET"
    ]

    static defaulAction = "resume"

    def resume() {
        [assistances: assistanceService.resume]
    }

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

    def resumeDetail(Integer year, String month) {
        List byDepartments = assistanceService.getResumeDetail(year, month)

        [byDepartments: byDepartments]
    }

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

    def resumeDetailByDepartment(Integer year, String month, String department) {
        List assistances = assistanceService.getResumeDetailByDepartment(year, month, department)

        [assistances: assistances]
    }

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
                            cell "Etiquetas"
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

    def resumeByTag() {
        def (byTags, tags, months) = assistanceService.resumeByTag
    }
}
