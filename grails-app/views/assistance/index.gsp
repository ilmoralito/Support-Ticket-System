<g:applyLayout name="twoColumns">
    <head>
        <title>Support</title>
    </head>

    <content tag="main">
        <div class="clearfix">
            <g:link controller="${controllerName}" action="create" class="button right">Crear ticket</g:link>
        </div>

        <g:if test="${assistances}">
            <table role="grid" width="100%">
                <colgroup>
                   <col span="1" style="width: 5%;">
                   <col span="1" style="width: 70%;">
                   <col span="1" style="width: 15%;">
                   <col span="1" style="width: 10%;">
               </colgroup>
                <thead>
                    <tr>
                        <th style="text-align: center;">#</th>
                        <th>ASUNTO</th>
                        <th>ESTADO</th>
                        <th>ACTUALIZACION</th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${assistances}" var="a">
                        <tr>
                            <td style="text-align: center;">
                                <g:link action="edit" id="${a.id}">${a.id}</g:link>
                            </td>
                            <td>${a.description}</td>
                            <td><ticket:state state="${a.state}"/></td>
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
        <g:form action="index" autocomplete="off">
            <ticket:usersByRole role="ROLE_ADMIN"/>

            <g:render template="filterForm"/>

            <g:submitButton name="send" value="Filtrar" class="button expand"/>
        </g:form>
        
    </content>
</g:applyLayout>
