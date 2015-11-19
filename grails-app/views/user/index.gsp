<g:applyLayout name="twoColumns">
    <head>
        <title>Users</title>
    </head>

    <content tag="main">
        <g:link controller="${controllerName}" action="create" class="button right">Crear</g:link>

        <g:if test="${users}">
            <table role="grid" width="100%">
                <thead>
                    <tr>
                        <th>Usuarios</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${users}" var="user">
                        <tr>
                            <td>
                                <g:link action="edit" id="${user.id}">${user.fullName}</g:link>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </g:if>
        <g:else>
            <div class="alert-box info">Nada que mostrar</div>
        </g:else>
    </content>
    <content tag="sidebar">
        <g:set var="check" value="${params?.enabledStatus || params?.roles}"/>

        <g:form action="index" autocomplete="off">
            <h6>Filtrar</h6>
            <g:each in="${ni.edu.uccleon.ticket.Role.list()}" var="role">
                <g:checkBox
                    name="roles"
                    value="${role.authority}"
                    checked="${params.list('roles').contains(role.authority)}"/>
                <label>${rolesNickname[role.authority]}</label>
                <br>
            </g:each>

            <h6>Estado</h6>
            <g:checkBox
                name="enabledStatus"
                value="true"
                checked="${check ? params.list('enabledStatus').contains('true') : true}"/>
            <label>Habilitado</label>
            <br>

            <g:checkBox
                name="enabledStatus"
                value="false"
                checked="${params.list('enabledStatus').contains('false')}"/>
            <label>Desabilitado</label>

            <g:submitButton name="send" value="Filrar" class="button expand"/>
        </g:form>
    </content>
</g:applyLayout>
