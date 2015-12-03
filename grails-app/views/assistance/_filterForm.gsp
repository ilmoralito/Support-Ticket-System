<g:set var="states" value="${params.list('states')}"/>

<h6>Estado</h6>
<div>
    <g:checkBox
        name="states"
        value="PENDING"
        checked="${states ? 'PENDING' in states : request.method == 'GET'}"/>
    <label>Pendiente</label>
</div>

<div>
    <g:checkBox
        name="states"
        value="PROCESS"
        checked="${states ? 'PROCESS' in states : request.method == 'GET'}"/>
    <label>Proceso</label>
</div>

<div>
    <g:checkBox
        name="states"
        value="CLOSED"
        checked="${'CLOSED' in states}"/>
    <label>Cerrado</label>
</div>
