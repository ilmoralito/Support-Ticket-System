<g:applyLayout name="twoColumns">
    <head>
        <title>Bitacora</title>
    </head>

    <content tag="main">
        <g:link action="application" class="button secondary">Regresar</g:link>
        <g:link
            action="updateAttendedBy"
            id="${assistance.id}"
            class="button ${isAttendedByCurrentUser ? 'info' : ''} right">
            ${isAttendedByCurrentUser ? "Atendiendo" : "Sin atender"}
        </g:link>

        <h5>Descripcion</h5>
        <p>${assistance.description}</p>
    </content>
    <content tag="sidebar">
        <label>Por</label>
        ${assistance.user.fullName}
        
        <label>Departamento</label>
        ${assistance.user.departments.join(", ")}
        
        <label>Estado</label>
        <ticket:state state="${assistance.state}"/>
        
        <label>Solicitado</label>
        ${assistance.dateCreated.format("yyyy-MM-dd")}

        <g:if test="${assistance.attendedBy}">
            <label>Actualizado</label>
            ${assistance.lastUpdated.format("yyyy-MM-dd")}

            <label>Atendido por</label>
            ${assistance.attendedBy.user.fullName.join(", ")}
        </g:if>

        <g:if test="${assistance.attendedBy}">
            <g:form action="addTags">
                <g:hiddenField name="id" value="${assistance.id}"/>
                <ticket:getTags/>

                <g:submitButton name="send" value="Agregar" class="button tiny expand"/>
            </g:form>

            <g:form controller="tag" action="save">
                <g:hiddenField name="id" value="${assistance.id}"/>
                <g:textField name="name" value="${tag?.name}" placeholder="Nueva etiqueta"/>

                <g:submitButton name="send" value="Confirmar" class="button expand"/>
            </g:form>
        </g:if>

    </content>
</g:applyLayout>
