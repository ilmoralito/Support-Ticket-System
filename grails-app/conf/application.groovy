// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/assistance"
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.userLookup.userDomainClassName = 'ni.edu.uccleon.ticket.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'ni.edu.uccleon.ticket.UserRole'
grails.plugin.springsecurity.authority.className = 'ni.edu.uccleon.ticket.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/':                ['permitAll'],
	'/error':           ['permitAll'],
	'/index':           ['permitAll'],
	'/shutdown':        ['permitAll'],
	'/assets/**':       ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/**/favicon*':     ['permitAll']
]

// App configuration settings
ni.edu.uccleon.ticket.rolesNickname = [
	ROLE_ADMIN: "Administrador",
	ROLE_USER: "Usuario"
]

ni.edu.uccleon.ticket.departmentsNickname = [
	Academic: "Academico",
	Administrative: "Administrativo"
]
