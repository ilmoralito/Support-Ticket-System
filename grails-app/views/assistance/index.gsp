<g:applyLayout name="twoColumns">
    <head>
        <title>Support</title>
    </head>

    <content tag="main">
        <g:link controller="control" class="button secondary">Regresar</g:link>
        <g:link controller="${controllerName}" action="create" class="button right">
            Crear una Ticket de soporte
        </g:link>

        <g:if test="${assistances}">
            <table role="grid" width="100%">
                <colgroup>
                   <col span="1" style="width: 5%;">
                   <col span="1" style="width: 75%;">
                   <col span="1" style="width: 10%;">
                   <col span="1" style="width: 10%;">
               </colgroup>
                <thead>
                    <tr>
                        <th style="text-align: center;">#</th>
                        <th>ASUNTO</th>
                        <th>ESTADO</th>
                        <th>ULTIMA ACTUALIZACION</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${assistances}" var="a">
                        <tr>
                            <td style="text-align: center;">
                                <g:link action="show" id="${a.id}">${a.id}</g:link>
                            </td>
                            <td>${a.description}</td>
                            <td>
                                <ticket:state attendedBy="${a?.attendedBy}" dateCompleted="${a?.dateCompleted}"/>
                            </td>
                            <td>${a.lastUpdated.format("yyyy-MM-dd")}</td>
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
        <h6>Filter</h6>
        
    </content>
</g:applyLayout>
