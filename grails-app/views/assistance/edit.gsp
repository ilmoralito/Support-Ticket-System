<g:applyLayout name="oneColumn">
    <head>
        <title>Edit/Update ticket support</title>
    </head>

    <content tag="main">
        <g:link controller="${controllerName}" class="button secondary">Regresar</g:link>

        <g:if test="${assistance?.state == 'PENDING'}">
            <g:form action="update" autocomplete="off">
                <g:hiddenField name="id" value="${assistance?.id}"/>
                <g:render template="form"/>

                <g:submitButton name="send" value="Confirmar" class="button"/>
            </g:form>
        </g:if>
        <g:else>
            <g:set var="tags" value="${assistance.tags}"/>

            <div class="row">
                <div class="medium-9 columns">
                    <h6>Descripcion</h6>
                    <p>${assistance.description}</p>
                </div>
                <div class="medium-3 columns">
                    <g:render template="detail" model="[assistance: assistance]"/>
                </div>
            </div>
        </g:else>

        <g:if test="${!assistance.attendedBy}">
            <g:link action="delete" id="${assistance?.id}" class="button alert right">Eliminar</g:link>
        </g:if>

        <g:hasErrors bean="${assistance}">
            <g:renderErrors bean="${assistance}"/>
        </g:hasErrors>
    </content>
</g:applyLayout>
