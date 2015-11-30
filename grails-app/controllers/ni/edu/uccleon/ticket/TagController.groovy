package ni.edu.uccleon.ticket

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class TagController {
    static allowedMethods = [
        save: "POST"
    ]

    def save(Long id) {
        def tag = new Tag(name: params?.name)

        if (!tag.save()) {
            tag.errors.allErrors.each { err -> log.error "[$err.field: $err.defaultMessage]"}

            flash.message = "A ocurrido un error al intentar agregar etiqueta. Intentalo nuevamente"
        }

        redirect controller: "assistance", action: "binnacle", id: id, fragment: "${tag?.name ?: 'createTag'}"
    }
}
