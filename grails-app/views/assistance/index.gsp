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
        <h5>Filtrar</h5>
        <g:form action="index" autocomplete="off">
            <h6>Fecha de creacion</h6>
            <g:textField name="fromDateCreated" value="${params?.fromDateCreated}" placeholder="Desde"/>
            <g:textField name="toDateCreated" value="${params?.toDateCreated}" placeholder="Hasta"/>

            <g:if test="${'ROLE_ADMIN' in authorities}">
                <ticket:renderDepartments/>
            </g:if>

            <h5>Atendidos por</h5>
            <g:each in="${adminUsers}" var="u">
                <div>
                    <g:checkBox name="users" value="u.id" checked="false"/>
                    <label>${u.fullName}</label>
                </div>
            </g:each>

            <g:submitButton name="send" value="Filtrar" class="button expand"/>
        </g:form>
        
    </content>
</g:applyLayout>
