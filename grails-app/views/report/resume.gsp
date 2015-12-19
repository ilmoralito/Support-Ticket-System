<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResume"/>

        <div class="clearfix"></div>

        <g:if test="${assistances}">
            <g:each in="${assistances}" var="assistance">
                <caption>RESUMEN ASISTENCIAS ${assistance.year}</caption>

                <table width="100%">
                    <thead>
                        <tr>
                            <th>Mes</th>
                            <th>Programados</th>
                            <th>No programados</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${assistances.months}" var="month">
                            <tr>
                                <td>
                                    <g:link
                                        action="resumeDetail"
                                        params="[year: assistance.year, month: month[0].month]">
                                        ${month[0].month}
                                    </g:link>
                                </td>
                                <td>${month[0].programmed}</td>
                                <td>${month[0].nonscheduled}</td>
                                <td>${month[0].total}</td>
                            </tr>
                        </g:each>
                        <tr>
                            <td>TOTAL</td>
                            <td>${assistance.months.programmed.sum()}</td>
                            <td>${assistance.months.nonscheduled.sum()}</td>
                            <td>${assistance.months.programmed.sum() + assistance.months.nonscheduled.sum()}</td>
                        </tr>
                    </tbody>
                </table>
            </g:each>
        </g:if>
        <g:else>
            <div class="alert">Nada que mostrar</div>
        </g:else>
    </content>
</g:applyLayout>
