<h6>Atendidos por</h6>
<g:each in="${ni.edu.uccleon.ticket.UserRole.findAllByRole(ni.edu.uccleon.ticket.Role.findByAuthority('ROLE_ADMIN')).user}" var="u">
    <div>
        <g:checkBox
            name="attendedBy"
            value="${u.id}"
            checked="${params.list('attendedBy').contains(u.id.toString())}"/>
        <label>${u.fullName}</label>
    </div>
</g:each>

<h6>Estado</h6>
<div>
    <g:checkBox name="state" value="PENDING" checked="${'PENDING' in params.list('state')}"/>
    <label>Pendiente</label>
</div>

<div>
    <g:checkBox name="state" value="PROCESS" checked="${'PROCESS' in params.list('state')}"/>
    <label>Proceso</label>
</div>

<div>
    <g:checkBox name="state" value="CLOSED" checked="${'CLOSED' in params.list('state')}"/>
    <label>Cerrado</label>
</div>
