<g:applyLayout name="twoColumns">
    <head>
        <title>Bitacora</title>
    </head>

    <content tag="main">
        <g:link action="application" class="button secondary">Regresar</g:link>
        <g:link
            action="updateAttendedBy"
            id="${assistance.id}"
            class="button ${isAttendedByCurrentUser ? 'info' : ''} right">
            ${isAttendedByCurrentUser ? "Atendiendo" : "Sin atender"}
        </g:link>

        <h5>Descripcion</h5>
        <p>${assistance.description}</p>
        
        <g:if test="${isAttendedByCurrentUser}">
            <g:form controller="task" action="save" autocomplete="off">
                <g:hiddenField name="assistanceId" value="${assistance.id}"/>
                <g:textArea name="description" placeholder="Descripcion"/>

                <g:submitButton name="send" value="Agregar tarea" class="button"/>
            </g:form>
        </g:if>

        <g:if test="${tasks}">
            <caption>${tasks.size()} TAREAS</caption>
            
            <table width="100%">
                <colgroup>
                   <col span="1" style="width: 1%;">
                   <col span="1" style="width: 70%;">
                   <col span="1" style="width: 9%;">
                   <col span="1" style="width: 15%;">
                   <col span="1" style="width: 4%;">
               </colgroup>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>DESCRIPCION</th>
                        <th>ESTADO</th>
                        <th>CREADO</th>
                        <th>
                            <i class="fi-trash"></i>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <g:each in="${tasks}" var="task" status="i">
                        <tr>
                            <td>${i + 1}</td>
                            <td>${task.description}</td>
                            <td>
                                <g:if test="${isAttendedByCurrentUser}">
                                    <g:link
                                        controller="task"
                                        action="updateStatus"
                                        params="[id: task.id, assistanceId: assistance.id]">
                                        <ticket:taskStatus status="${task.status}"/>
                                    </g:link>
                                </g:if>
                                <g:else>
                                    <ticket:taskStatus status="${task.status}"/>
                                </g:else>
                            </td>
                            <td>${task.dateCreated.format("MM-dd, HH:mm")}</td>
                            <td>
                                <g:if test="${isAttendedByCurrentUser}">
                                    <g:link
                                        controller="task"
                                        action="delete"
                                        params="[id: task.id, assistanceId: assistance.id]">
                                        <i class="fi-trash"></i>
                                    </g:link>
                                </g:if>
                                <g:else>
                                    <i class="fi-trash"></i>
                                </g:else>
                            </td>
                        </tr>
                    </g:each>
                </tbody>
            </table>
        </g:if>
    </content>
    <content tag="sidebar">
        <label>Por</label>
        ${assistance.user.fullName}
        
        <label>Departamento</label>
        ${assistance.user.departments.join(", ")}
        
        <label>Estado</label>
        <ticket:state state="${assistance.state}"/>
        
        <label>Solicitado</label>
        ${assistance.dateCreated.format("yyyy-MM-dd")}

        <g:if test="${assistance.attendedBy}">
            <label>Actualizado</label>
            ${assistance.lastUpdated.format("yyyy-MM-dd")}

            <label>Atendido por</label>
            ${assistance.attendedBy.user.fullName.join(", ")}
        </g:if>

        <g:if test="${isAttendedByCurrentUser}">
            <g:form action="addTags">
                <g:hiddenField name="id" value="${assistance.id}"/>
                <ticket:getTags/>

                <g:submitButton name="send" value="Agregar" class="button tiny expand"/>
            </g:form>

            <g:form controller="tag" action="save">
                <g:hiddenField name="id" value="${assistance.id}"/>
                <g:textField name="name" value="${tag?.name}" placeholder="Nueva etiqueta"/>

                <g:submitButton name="send" value="Confirmar" class="button expand"/>
            </g:form>
        </g:if>

    </content>
</g:applyLayout>
