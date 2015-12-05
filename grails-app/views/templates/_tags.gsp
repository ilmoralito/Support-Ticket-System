<g:each in="${tags}" var="tag">
    <div>
        <g:checkBox
            id="${tag.name}"
            name="tags"
            value="${tag.name}"
            checked="${tag.name in tagList}"/>
        <label>${tag.name}</label>
    </div>
</g:each>