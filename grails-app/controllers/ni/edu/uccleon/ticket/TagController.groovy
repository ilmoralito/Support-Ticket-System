package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TagController {
    static allowedMethods = [
        save: "POST"
    ]

    def save(Long assistanceId) {
        def assistance = Assistance.get assistanceId
        def tag = new Tag(name: params?.name)

        if (!assistance) {
            response.sendError 404
        }

        if (!tag.save()) {
            tag.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]"}

            flash.message = "A ocurrido un error al intentar agregar etiqueta. Intentalo nuevamente"
        } else {
            assistance.addToTags tag

            assistance.save(flush: true)
        }

        redirect controller: "assistance", action: "binnacle", params: [id: assistanceId], fragment: "${tag?.name ?: 'createTag'}"
    }
}
