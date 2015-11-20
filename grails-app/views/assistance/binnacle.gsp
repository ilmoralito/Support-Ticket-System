<g:applyLayout name="twoColumns">
    <head>
        <title>Bitacora</title>
    </head>

    <content tag="main">
        <g:link action="application" class="button secondary">Regresar</g:link>
        <ul class="button-group right">
            <li><g:link action="#" class="button">Button 1</g:link></li>
            <li><g:link action="#" class="button">Button 2</g:link></li>
        </ul>

        <h5>Descripcion</h5>
        <p>${assistance.description}</p>
    </content>
    <content tag="sidebar">
        <label>Por</label>
        ${assistance.user.fullName}

        <label>Departamento(s)</label>
        ${assistance.user.departments.join(", ")}

        <label>Estado</label>
        <ticket:state state="${assistance.state}"/>

        <label>Solicitado</label>
        ${assistance.dateCreated.format("yyyy-MM-dd")}

        <label>Actualizado</label>
        ${assistance.lastUpdated.format("yyyy-MM-dd")}

        <hr>

        <g:form action="addTags">
            <g:hiddenField name="id" value="${assistance.id}"/>
            <ticket:getTags/>

            <g:submitButton name="send" value="Agregar" class="button tiny expand"/>
        </g:form>

        <div class="panel">
            
        </div>
    </content>
</g:applyLayout>
