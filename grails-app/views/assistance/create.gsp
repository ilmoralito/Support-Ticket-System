<g:applyLayout name="oneColumn">
    <head>
        <title>Create ticket support</title>
    </head>

    <content tag="main">
        <g:link controller="${controllerName}" class="button secondary">Regresar</g:link>

        <g:form action="create" autocomplete="off">
            <g:render template="form"/>

            <g:submitButton name="send" value="Confirmar" class="button"/>
        </g:form>

        <g:hasErrors bean="${assistance}">
            <g:renderErrors bean="${assistance}"/>
        </g:hasErrors>
    </content>
</g:applyLayout>
