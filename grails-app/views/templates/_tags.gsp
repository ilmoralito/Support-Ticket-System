<h6>Etiquetas</h6>
<g:each in="${tags}" var="t">
    <div>
        <g:checkBox
            name="tags"
            value="${t.name}"
            checked="${t.name in params.list('tags')}"/>
        <label>${t.name}</label>
    </div>
</g:each>