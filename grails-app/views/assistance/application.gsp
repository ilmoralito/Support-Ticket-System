<g:applyLayout name="twoColumns">
    <head>
        <title>Applications</title>
    </head>

    <content tag="main">
        
    </content>
    <content tag="sidebar">
        <g:form action="application" autocomplete="off">
            <h5>Filtros</h5>
            <g:render template="filterForm"/>

            <ticket:renderDepartments/>

            <g:submitButton name="filter" value="Filtrar" class="button expand"/>
        </g:form>
    </content>
</g:applyLayout>
