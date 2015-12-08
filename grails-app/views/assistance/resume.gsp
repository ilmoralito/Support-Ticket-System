<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume</title>
    </head>

    <content tag="main">
        <div>
            <g:link action="printResume" class="button small right">
                <i class="fi-print"></i> Imprimir
            </g:link>
        </div>

        <div class="clearfix"></div>

        <g:if test="${assistances}">
            <g:each in="${assistances}" var="assistance">
                <caption>RESUMEN ASISTENCIAS ${assistance.year}</caption>

                <g:each in="${assistances.months}" var="month">
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
                            <tr>
                                <td width="20%">
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

                            <tr>
                                <td>TOTAL</td>
                                <td>${month.programmed.sum()}</td>
                                <td>${month.nonscheduled.sum()}</td>
                                <td>${month.programmed.sum() + month.nonscheduled.sum()}</td>
                            </tr>
                        </tbody>
                    </table>
                </g:each>
            </g:each>
        </g:if>
        <g:else>
            <div class="alert">Nada que mostrar</div>
        </g:else>
    </content>
</g:applyLayout>
