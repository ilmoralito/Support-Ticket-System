<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume detail</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResumeDetail" params="[year: params?.year, month: params?.month]"/>

        <div class="clearfix"></div>

        <h6>DETALLE ${params?.month?.toUpperCase()} ${params?.year?.toUpperCase()}</h6>

        <table width="100%">
            <thead>
                <tr>
                    <th>Solicitado por</th>
                    <th>Programado</th>
                    <th>No programado</th>
                    <th>Pendiente</th>
                    <th>Proceso</th>
                    <th>Cerrado</th>
                </tr>
            </thead>
            <tbody>
                <g:each in="${byDepartments}" var="department">
                    <tr>
                        <td colspan="6"><b>${department.departments.join(", ")}</b></td>
                    </tr>
                    <g:each in="${department.users}" var="u">
                        <tr>
                            <td>${u.user}</td>
                            <td>${u.programmed}</td>
                            <td>${u.nonscheduled}</td>
                            <td>${u.pending}</td>
                            <td>${u.process}</td>
                            <td>${u.closed}</td>
                        </tr>
                    </g:each>
                </g:each>
            </tbody>
        </table>
    </content>
</g:applyLayout>
