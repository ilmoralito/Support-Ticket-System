<g:set var="resource" value="${actionName == 'edit' ? user.departments : params.list('departments')}"/>

<h5>Departamentos</h5>
<div>
    <g:each in="${departments}" var="d">
        <h6>${grailsApplication.config.ni.edu.uccleon.ticket.departmentsNickname[d.area]}</h6>

        <g:each in="${d.data}" var="data">
            <div>
                <g:checkBox
                    name="departments"
                    value="${data}"
                    checked="${data in resource}"/>
                <label>${data}</label>
            </div>
        </g:each>
    </g:each>
</div>