<g:textField name="fullName" value="${user?.fullName}" placeholder="Nombre completo"/>
<g:textField name="email" value="${user?.email}" placeholder="Correo institucional"/>

<g:each in="${ni.edu.uccleon.ticket.Role.list()}" var="role">
    <g:checkBox name="roles" value="${role}" checked="false"/>
    <label>${role.authority == "ROLE_ADMIN" ? "Administrador" : "Usuario"}</label>
</g:each>
<br>
