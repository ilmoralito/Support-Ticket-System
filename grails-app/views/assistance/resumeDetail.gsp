<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume detail</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResumeDetail" params="[year: params?.year, month: params?.month]"/>

        <div class="clearfix"></div>

        <h6>DETALLE ${params?.month?.toUpperCase()} ${params?.year}</h6>
        <table width="100%">
            <tbody>
                <g:each in="${byDepartments}" var="department">
                    <tr>
                        <td>
                            <strong>${department.area.toUpperCase()}</strong>
                        </td>
                        <td>PROGRAMADOS</td>
                        <td>NO PROGRAMADOS</td>
                        <td>PENDIENTES</td>
                        <td>PROCESO</td>
                        <td>CERRADO</td>
                    </tr>

                    <g:each in="${department.departments}" var="d">
                        <tr id="${d}">
                            <td>
                                <g:link params="${params}">${d.name}</g:link>
                            </td>
                            <td>${d.programmed}</td>
                            <td>${d.nonscheduled}</td>
                            <td>${d.pending}</td>
                            <td>${d.process}</td>
                            <td>${d.closed}</td>
                        </tr>
                    </g:each>
                    <tr>
                        <td>TOTAL</td>
                        <td>${department.departments.sum { it.programmed }}</td>
                        <td>${department.departments.sum { it.nonscheduled }}</td>
                        <td>${department.departments.sum { it.pending }}</td>
                        <td>${department.departments.sum { it.process }}</td>
                        <td>${department.departments.sum { it.closed }}</td>
                    </tr>
                </g:each>
            </tbody>
        </table>
    </content>
</g:applyLayout>
