<g:applyLayout name="oneColumn">
    <head>
        <title>Edit user</title>
    </head>

    <content tag="main">
        <g:link controller="user" class="button secondary">Regresar</g:link>

        <g:form action="update" autocomplete="off">
            <g:hiddenField name="id" value="${user?.id}"/>
            <g:render template="form"/>

            <h5>Departamentos</h5>
            <ticket:renderDepartments departmentList="${user?.departments}"/>

            <g:submitButton name="send" value="Confirmar" class="button"/>
        </g:form>

        <g:hasErrors bean="${user}">
            <g:renderErrors bean="${user}"/>
        </g:hasErrors>
    </content>
</g:applyLayout>
