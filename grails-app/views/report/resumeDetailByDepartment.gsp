<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume detail by department</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResumeDetailByDepartment" params="${params}"/>

        <div class="clearfix"></div>

        <h6>${params?.department} ${params?.month} ${params?.year}</h6>
        <table width="100%">
            <thead>
                <tr>
                    <th>Creado</th>
                    <th>Actualizado</th>
                    <th>Atendido por</th>
                    <th>Tipo</th>
                    <th>Estado</th>
                    <th>Etiquetas</th>
                </tr>
            </thead>
            <g:each in="${assistances}" var="assistance">
                <tbody>
                    <tr>
                        <td colspan="6">${assistance.user}</td>
                    </tr>

                    <g:each in="${assistance.assistances}" var="a">
                        <tr>
                            <td>${a.dateCreated.format("MM-dd")}</td>
                            <td>${a.lastUpdated.format("MM-dd hh:mm")}</td>
                            <td>${a.attendedBy}</td>
                            <td><assistance:getType type="${a.type}"/></td>
                            <td><assistance:getState state="${a.state}"/></td>
                            <td>${a.tags}</td>
                        </tr>
                    </g:each>
                </tbody>
            </g:each>
        </table>
    </content>
</g:applyLayout>
