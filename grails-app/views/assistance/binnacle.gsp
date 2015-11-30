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

        <h5>DESCRIPCION</h5>
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
        <div class="panel">
            <h5>DETALLE</h5>

            <h6>Por</h6>
            <p>${assistance.user.fullName}</p>

            <h6>Departamento</h6>
            <p>${assistance.user.departments.join(", ")}</p>

            <h6>Estado</h6>
            <p><ticket:state state="${assistance.state}"/></p>

            <h6>Solicitado</h6>
            <p>${assistance.dateCreated.format("yyyy-MM-dd")}</p>

            <g:if test="${assistance.attendedBy}">
                <h6>Actualizado</h6>
                <p>${assistance.lastUpdated.format("yyyy-MM-dd")}</p>

                <h6>Atendido por</h6>
                <p>${assistance.attendedBy.user.fullName.join(", ")}</p>
            </g:if>
        </div>

        <g:if test="${isAttendedByCurrentUser}">
            <div class="panel">
                <h5>ETIQUETAS</h5>

                <g:form action="addTags">
                    <g:hiddenField name="id" value="${assistance.id}"/>
                    <ticket:getTags/>
                
                    <g:submitButton name="send" value="Agregar" class="button tiny expand" style="margin-bottom: 3px;"/>
                </g:form>
            </div>
                
            <div class="panel" id="createTag">
                <g:form controller="tag" action="save">
                    <g:hiddenField name="id" value="${assistance.id}"/>
                    <g:textField name="name" value="${tag?.name}" placeholder="Nueva etiqueta"/>
                
                    <g:submitButton name="send" value="Confirmar" class="button expand" style="margin-bottom: 3px;"/>
                </g:form>
            </div>
        </g:if>

    </content>
</g:applyLayout>
