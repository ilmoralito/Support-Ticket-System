package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TagController {
    static allowedMethods = [
        save: "POST"
    ]

    def save(Long assistanceId) {
        def assistance = Assistance.get assistanceId

        if (!assistance) {
            response.sendError 404
        }

        def tag = new Tag(params)

        if (!tag.validate()) {
            tag.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]"}

            flash.message = "A ocurrido un error al intentar agregar etiqueta. Intentalo nuevamente"
        } else {
            assistance.addToTags tag

            assistance.save()
        }

        redirect controller: "assistance", action: "binnacle", id: assistanceId, fragment: "tagList"
    }
}
