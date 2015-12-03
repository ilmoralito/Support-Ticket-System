<g:applyLayout name="twoColumns">
    <head>
        <title>Bitacora</title>
    </head>

    <content tag="main">
        <g:link action="application" class="button secondary">Regresar</g:link>

        <h5>DESCRIPCION</h5>
        <p>${assistance.description}</p>
        
        <g:if test="${isAttendedByCurrentUser && assistance.state != 'CLOSED'}">
            <g:form controller="task" action="save" autocomplete="off">
                <g:hiddenField name="assistanceId" value="${assistance.id}"/>
                <g:textArea name="description" placeholder="Descripcion" style="min-height: 150px; min-width: 100%;"/>
                <small class="right special-formating-text">
                    <b>*bold*</b>
                    <i>_italics_</i>
                    ~strike~
                    <span>- list</span>
                    <a href="http://asciidoctor.org/docs/asciidoc-writers-guide/" target="_blank">
                        ...mas opciones
                    </a>
                </small>

                <div class="clearfix"></div>

                <g:submitButton name="send" value="Agregar tarea" class="button"/>
            </g:form>
        </g:if>

        <g:if test="${tasks}">
            <h5>${tasks.size()} TAREAS</h5>

            <g:each in="${tasks}" var="task">
                <div class="panel" id="${task.id}">
                    <g:if test="${isAttendedByCurrentUser && assistance.state != 'CLOSED'}">
                        <div>
                            <ul class="button-group right">
                                <li>
                                    <g:if test="${isAttendedByCurrentUser}">
                                        <g:link
                                            controller="task"
                                            action="updateStatus"
                                            params="[id: task.id, assistanceId: assistance.id]"
                                            class="button  tiny">
                                            <ticket:taskStatus status="${task.status}"/>
                                        </g:link>
                                    </g:if>
                                    <g:else>
                                        <ticket:taskStatus status="${task.status}"/>
                                    </g:else>
                                </li>
                                <li>
                                    <g:if test="${isAttendedByCurrentUser}">
                                        <g:link
                                            controller="task"
                                            action="delete"
                                            params="[id: task.id, assistanceId: assistance.id]"
                                            class="button secundary tiny">
                                            <i class="fi-trash"></i>
                                        </g:link>
                                    </g:if>
                                </li>
                            </ul>
                        </div>

                        <div class="clearfix"></div>
                    </g:if>

                    <div>
                        <small class="right">
                            ATENDIDO: ${task.dateCreated.format("MM-dd, HH:mm")},
                            POR: ${task.user.fullName.toUpperCase()}
                        </small>
                    </div>

                    <g:set var="html" value="${asciidoctor.convert(task.description, new HashMap<String, Object>())}"/>

                    <p>${raw(html)}</p>
                </div>
            </g:each>
        </g:if>
    </content>
    <content tag="sidebar">
        <g:if test="${isAttendedByCurrentUser && assistance.state != 'CLOSED'}">
            <g:link
                action="updateAttendedBy"
                id="${assistance.id}"
                class="button expand">
                ${isAttendedByCurrentUser ? "ATENDIENDO" : "SIN ATENDER"}
            </g:link>
        </g:if>

        <g:if test="${isAttendedByCurrentUser}">
            <g:link
                action="setOrUnsetDateCompleted"
                id="${assistance.id}"
                class="button warning expand">
                <assistance:dateCompletedStatus status="${assistance?.dateCompleted}"/>
            </g:link>
        </g:if>

        <g:render template="detail" model="[assistance: assistance]"/>

        <g:if test="${isAttendedByCurrentUser && assistance.state != 'CLOSED'}">
            <div class="panel" id="listTag">
                <h5>ETIQUETAS</h5>

                <g:form action="addTags">
                    <g:hiddenField name="id" value="${assistance.id}"/>
                    <ticket:getTags/>
                
                    <g:submitButton name="send" value="Agregar" class="button tiny expand" style="margin-bottom: 3px;"/>
                </g:form>
            </div>
                
            <div class="panel" id="createTag">
                <g:form controller="tag" action="save">
                    <g:hiddenField name="assistanceId" value="${assistance.id}"/>
                    <g:textField name="name" value="${tag?.name}" placeholder="Nueva etiqueta"/>
                
                    <g:submitButton name="send" value="Confirmar" class="button expand" style="margin-bottom: 3px;"/>
                </g:form>
            </div>
        </g:if>

    </content>
</g:applyLayout>
