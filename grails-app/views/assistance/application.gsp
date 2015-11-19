<g:applyLayout name="twoColumns">
    <head>
        <title>Applications</title>
    </head>

    <content tag="main">
        <g:if test="${assistances}">
            <table width="100%">
                <colgroup>
                   <col span="1" style="width: 5%;">
                   <col span="1" style="width: 15%;">
                   <col span="1" style="width: 70%;">
                   <col span="1" style="width: 10%;">
                </colgroup>
                <thead>
                    <tr>
                        <th style="text-align: center;">#</th>
                        <th>Por</th>
                        <th>Descripcion</th>
                        <th>Estado</th>
                    </tr>
                </thead>
                    <g:each in="${assistances}" var="a">
                    <tbody>
                        <tr>
                            <td colspan="4">${a.dateCreated.format("yyyy-MM-dd")}</td>
                            <g:each in="${a.assistances}" var="assistance">
                                <tr>
                                    <td style="text-align: center;">
                                        <g:link action="binnacle" id="${assistance.id}">
                                            ${assistance.id}
                                        </g:link>
                                    </td>
                                    <td>${assistance.user.fullName}</td>
                                    <td>${assistance.description}</td>
                                    <td>
                                        <ticket:state state="${assistance.state}">
                                            ${assistance.state}
                                        </ticket:state>
                                    </td>
                                </tr>
                            </g:each>
                        </tr>
                    </tbody>
                    </g:each>
            </table>
        </g:if>
        <g:else>
            <div class="alert">Sin datos que mostrar</div>
        </g:else>
    </content>
    <content tag="sidebar">
        <g:form action="application" autocomplete="off">
            <h5>Filtros</h5>
            <ticket:usersByRole role="ROLE_ADMIN"/>

            <g:render template="filterForm"/>

            <ticket:renderDepartments/>

            <g:submitButton name="filter" value="Filtrar" class="button expand"/>
        </g:form>
    </content>
</g:applyLayout>
