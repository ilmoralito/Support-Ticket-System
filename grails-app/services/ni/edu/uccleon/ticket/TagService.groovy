package ni.edu.uccleon.ticket

import grails.transaction.Transactional

@Transactional
class TagService {

    def getTags() {
        Tag.list()
    }
}
