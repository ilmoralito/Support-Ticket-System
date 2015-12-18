<g:applyLayout name="oneColumn">
    <head>
        <title>Ticket support resume</title>
    </head>

    <content tag="main">
        <ticket:printResume action="printResumeByTag"/>
        <div class="clearfix"></div>

        <g:if test="${byTags}">
            <g:each in="${byTags}" var="data">
                <caption>RESUMEN ASISTENCIAS POR CATEGORIA ${data.year}</caption>

                <table width="100%">
                    <thead>
                        <th>Categorias</th>
                        <g:each in="${months}" var="month">
                            <th>${month}</th>
                        </g:each>
                    </thead>
                    <tbody>
                        <g:each in="${tags}" var="tag">
                            <tr>
                                <td>${tag.name}</td>
                                <td>${data.months.find { it.name == "Enero" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Febrero" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Marzo" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Abril" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Mayo" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Junio" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Julio" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Agosto" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Septiembre" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Octubre" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Noviembre" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                                <td>${data.months.find { it.name == "Diciembre" }.tags.find { it.name == tag.name }.count ?: 0}</td>
                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </g:each>
        </g:if>
        <g:else>
            <div class="alert">Nada que mostrar</div>
        </g:else>
    </content>
</g:applyLayout>
