<h6>Atendidos por</h6>
<g:each in="${users}" var="u">
    <div>
        <g:checkBox
            name="attendedBy"
            value="${u.id}"
            checked="${params.list('attendedBy').contains(u.id.toString())}"/>
        <label>${u.fullName}</label>
    </div>
</g:each>