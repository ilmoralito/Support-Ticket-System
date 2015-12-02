<g:applyLayout name="twoColumns">
    <head>
        <title>Bitacora</title>
    </head>

    <content tag="main">
        <g:link action="application" class="button secondary">Regresar</g:link>

        <h5>DESCRIPCION</h5>
        <p>${assistance.description}</p>
        
        <g:if test="${isAttendedByCurrentUser}">
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

                <g:submitButton name="send" value="Agregar tarea" class="button"/>
            </g:form>
        </g:if>

        <g:if test="${tasks}">
            <caption>${tasks.size()} TAREAS</caption>

            <g:each in="${tasks}" var="task">
                <div class="panel" id="${task.id}">
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

                    <div>
                        <small class="right">
                            ATENDIDO: ${task.dateCreated.format("MM-dd, HH:mm")}, POR: ${task.user.fullName}
                        </small>
                    </div>

                    <g:set var="html" value="${asciidoctor.convert(task.description, new HashMap<String, Object>())}"/>

                    <p>${raw(html)}</p>
                </div>
            </g:each>
        </g:if>
    </content>
    <content tag="sidebar">
        <g:link
            action="updateAttendedBy"
            id="${assistance.id}"
            class="button expand">
            ${isAttendedByCurrentUser ? "ATENDIENDO" : "SIN ATENDER"}
        </g:link>

        <g:if test="${isAttendedByCurrentUser}">
            <g:link
                action="setOrUnsetDateCompleted"
                id="${assistance.id}"
                class="button warning expand">
                <assistance:dateCompletedStatus status="${assistance?.dateCompleted}"/>
            </g:link>
        </g:if>

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
