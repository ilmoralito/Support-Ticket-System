<h6>Estado</h6>
<div>
    <g:checkBox
        name="state"
        value="PENDING"
        checked="${params?.state ? 'PENDING' in params.list('state') : true}"/>
    <label>Pendiente</label>
</div>

<div>
    <g:checkBox
        name="state"
        value="PROCESS"
        checked="${params?.state ? 'PROCESS' in params.list('state') : true}"/>
    <label>Proceso</label>
</div>

<div>
    <g:checkBox
        name="state"
        value="CLOSED"
        checked="${'CLOSED' in params.list('state')}"/>
    <label>Cerrado</label>
</div>
