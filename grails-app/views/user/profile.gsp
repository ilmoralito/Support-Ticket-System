<g:applyLayout name="oneColumn">
    <head>
        <title>Profile</title>
    </head>

    <content tag="main">
        <h6>Perfil</h6>
        <g:form action="profile" autocomplete="off">
          <g:textField name="fullName" value="${user?.fullName}" placeholder="Nombre completo"/>

          <g:submitButton name="send" value="Confirmar" class="button"/>
        </g:form>

        <h6>Cambiar clave</h6>
        <g:form action="updatePassword" autocomplete="off">
          <g:passwordField name="currentPassword" placeholder="Clave actual"/>
          <g:passwordField name="newPassword" placeholder="Nueva clave"/>
          <g:passwordField name="repeatNewPassword" placeholder="Repetir nueva clave"/>

          <g:submitButton name="send" value="Confirmar" class="button"/>
        </g:form>
    </content>
</g:applyLayout>
