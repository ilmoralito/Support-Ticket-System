<g:textField name="fullName" value="${user?.fullName}" placeholder="Nombre completo"/>
<g:textField name="email" value="${user?.email}" placeholder="Correo institucional"/>

<g:if test="${actionName == 'edit'}">
    <g:checkBox name="enabled" checked="${user?.enabled}"/>
    <label>Habilitado</label>
</g:if>

<h5>Roles</h5>
<g:each in="${ni.edu.uccleon.ticket.Role.list()}" var="role">
    <g:set
        var="shouldCheck"
        value="${actionName == 'edit' ? ni.edu.uccleon.ticket.UserRole.exists(user.id, role.id) : params.list('roles')?.contains(role.authority)}"/>

    <g:checkBox
        name="roles"
        value="${role.authority}"
        checked="${shouldCheck}"/>
    <label>${grailsApplication.config.ni.edu.uccleon.ticket.rolesNickname[role.authority]}</label>
</g:each>

<ticket:renderDepartments/>
