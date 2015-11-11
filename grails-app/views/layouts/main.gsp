<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><g:layoutTitle default="Support Ticket System"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="application.css"/>
        <!--<asset:javascript src="application.js"/>-->
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
                            <li class="has-dropdown">
                                <a href="#"><sec:loggedInUserInfo field="fullName"/></a>
                                <ul class="dropdown">
                                    <sec:ifAllGranted roles="ROLE_ADMIN">
                                        <li><g:link controller="user">Usuarios</g:link></li>
                                    </sec:ifAllGranted>
                                    <li><g:link controller="user" action="profile">Perfil</g:link></li>
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
    </body>
</html>
