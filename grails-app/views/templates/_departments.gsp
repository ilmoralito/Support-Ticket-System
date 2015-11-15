<h5>Departamentos</h5>
<div>
    <g:each in="${departments}" var="d">
        <h6>${d.area}</h6>

        <g:each in="${d.data}" var="data">
            <div>
                <g:checkBox
                    name="departments"
                    value="${data}"
                    checked="${data in params.list('departments')}"/>
                <label>${data}</label>
            </div>
        </g:each>
    </g:each>
</div>