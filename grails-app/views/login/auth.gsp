<!doctype html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Support Ticket System - Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
    </head>
    <body>
        <h6>Support Ticket System</h6>
        
        <form action="${postUrl}" method="POST" id="loginForm" autocomplete="off">
            <input type="text" name="j_username" id="username" placeholder="Correo institucional"/>
            <input type="password" name="j_password" id="password" placeholder="Clave"/>

            <input type="submit" id="submit" value="${message(code: "springSecurity.login.button")}" class="button expand"/>
        </form>

        <g:if test="${flash.message}">
            <div class="alert-box">${flash.message}</div>
        </g:if>
    </body>
</html>
