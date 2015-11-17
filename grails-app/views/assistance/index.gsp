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
            <h6>Creado</h6>
            <g:textField name="fromDateCreated" value="${params?.fromDateCreated}" placeholder="Desde"/>
            <g:textField name="toDateCreated" value="${params?.toDateCreated}" placeholder="Hasta"/>

            <h6>Atendidos por</h6>
            <g:each in="${ni.edu.uccleon.ticket.UserRole.findAllByRole(ni.edu.uccleon.ticket.Role.findByAuthority('ROLE_ADMIN')).user}" var="u">
                <div>
                    <g:checkBox
                        name="attendedBy"
                        value="${u.id}"
                        checked="${params.list('attendedBy').contains(u.id)}"/>
                    <label>${u.fullName}</label>
                </div>
            </g:each>

            <h6>Estado</h6>
            <div>
                <g:checkBox name="state" value="pending" checked="${'pending' in params.list('state')}"/>
                <label>Pendiente</label>
            </div>

            <div>
                <g:checkBox name="state" value="attanded" checked="${'attanded' in params.list('state')}"/>
                <label>Atendido</label>
            </div>

            <div>
                <g:checkBox name="state" value="closed" checked="${'closed' in params.list('state')}"/>
                <label>Cerrado</label>
            </div>

            <g:submitButton name="send" value="Filtrar" class="button expand"/>
        </g:form>
        
    </content>
</g:applyLayout>
