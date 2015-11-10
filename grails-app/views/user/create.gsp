<g:applyLayout name="oneColumn">
    <head>
        <title>Create user</title>
    </head>

    <content tag="main">
        <g:link controller="user" class="button secondary">Regresar</g:link>

        <g:form action="create" autocomplete="off">
            <g:render template="form"/>

            <g:submitButton name="send" value="Confirmar" class="button"/>
        </g:form>

        <g:hasErrors bean="${user}">
            <g:renderErrors bean="${user}"/>
        </g:hasErrors>
    </content>
</g:applyLayout>
