<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><g:layoutTitle default="Support Ticket System"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="application.css"/>
        <g:layoutHead/>
    </head>
    <body>
        <sec:ifLoggedIn>
            <div class="fixed">
                <nav class="top-bar" data-topbar role="navigation">
                     <ul class="title-area">
                        <li class="name">
                            <h1><a href="#">Support Ticket System</a></h1>
                        </li>
                        <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
                      </ul>

                      <section class="top-bar-section">
                        <ul class="right">
                            <sec:ifAllGranted roles="ROLE_ADMIN">
                                <li class="has-dropdown">
                                    <a href="#">Reportes</a>
                                    <ul class="dropdown">
                                        <li class="${actionName == 'resume' ? 'active' : ''}">
                                            <g:link action="resume">
                                                Resumen
                                            </g:link>
                                        </li>
                                    </ul>
                                </li>
                                <li class="${controllerName == 'assistance' && actionName in ['application', 'binnacle'] ? 'active' : ''}">
                                    <g:link controller="assistance" action="application">
                                        Solicitudes
                                        <span class="label alert">#</span>
                                    </g:link>
                                </li>
                            </sec:ifAllGranted>
                            <li class="${controllerName == 'assistance' && !(actionName in ['application', 'binnacle', 'resume'])  ? 'active' : ''}">
                                <g:link controller="assistance">Asistencias</g:link>
                            </li>
                            <li class="has-dropdown">
                                <a href="#"><sec:loggedInUserInfo field="fullName"/></a>
                                <ul class="dropdown">
                                    <sec:ifAllGranted roles="ROLE_ADMIN">
                                        <li class="${controllerName == 'user' && actionName != 'profile' ? 'active' : ''}">
                                            <g:link controller="user">Usuarios</g:link>
                                        </li>
                                    </sec:ifAllGranted>
                                    <li class="${actionName == 'profile' ? 'active' : ''}">
                                        <g:link controller="user" action="profile">Perfil</g:link>
                                    </li>
                                    <li><g:link controller="logout">Salir</g:link></li>
                                </ul>
                            </li>
                        </ul>
                      </section>
                </nav>
            </div>
        </sec:ifLoggedIn>
        <div class="row">
            <g:layoutBody/>
        </div>
        <asset:javascript src="application.js"/>
    </body>
</html>
