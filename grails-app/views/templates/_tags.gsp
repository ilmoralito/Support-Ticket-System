<g:each in="${tags}" var="t">
    <div>
        <g:checkBox
            id="${t.name}"
            name="tags"
            value="${t.name}"
            checked="${t.name in assistance.tags.name}"/>
        <label>${t.name}</label>
    </div>
</g:each>