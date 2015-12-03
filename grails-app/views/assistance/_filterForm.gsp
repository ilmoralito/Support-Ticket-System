<g:set var="states" value="${params.list('states')}"/>

<h6>Estado</h6>
<div>
    <g:checkBox
        name="states"
        value="PENDING"
        checked="${status ? 'PENDING' in states : true}"/>
    <label>Pendiente</label>
</div>

<div>
    <g:checkBox
        name="states"
        value="PROCESS"
        checked="${status ? 'PROCESS' in states : true}"/>
    <label>Proceso</label>
</div>

<div>
    <g:checkBox
        name="states"
        value="CLOSED"
        checked="${'CLOSED' in states}"/>
    <label>Cerrado</label>
</div>
