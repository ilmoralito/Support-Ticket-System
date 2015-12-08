<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResumeDetail" params="[year: params?.year, month: params?.month]"/>

        <div class="clearfix"></div>

        <g:if test="${assistances}">
            ${assistances}
        </g:if>
        <g:else>
            <div class="alert">Nada que mostrar</div>
        </g:else>
    </content>
</g:applyLayout>
