<div class="panel">
    <h5>DETALLE</h5>

    <g:if test="${actionName == 'binnacle'}">
        <h6>Por</h6>
        <p>${assistance.user.fullName}</p>

        <h6>Departamento</h6>
        <p>${assistance.user.departments.join(", ")}</p>
    </g:if>

    <h6>Estado</h6>
    <p><ticket:state state="${assistance.state}"/></p>

    <h6>Solicitado</h6>
    <p>${assistance.dateCreated.format("yyyy-MM-dd")}</p>

    <g:if test="${assistance.attendedBy}">
        <h6>Atendido por</h6>
        <p>${assistance.attendedBy.user.fullName.join(", ")}</p>

        <h6>Atendido</h6>
        <p>${assistance.lastUpdated.format("yyyy-MM-dd")}</p>

        <g:if test="${assistance.dateCompleted}">
            <h6>Cerrado</h6>
            <p>${assistance.dateCompleted.format("yyyy-MM-dd")}</p>
        </g:if>
    </g:if>

    <g:if test="${assistance.tags}">
        <h6>ETIQUETAS</h6>
    
        <ticket:getAssistanceTags tags="${assistance.tags}"/>
    </g:if>
</div>