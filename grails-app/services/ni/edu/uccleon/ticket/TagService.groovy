package ni.edu.uccleon.ticket

import grails.transaction.Transactional

@Transactional
class TagService {

    def getAll() {
        Tag.list()
    }
}
