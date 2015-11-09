<g:applyLayout name="oneColumn">
  <head>
    <title>Users</title>
  </head>

  <content tag="main">
    <g:link controller="control" class="btn btn-default">Regresar</g:link>

    <g:if test="${users}">
      <table role="grid" width="100%">
        <thead>
          <tr>
            <th>Usuarios</th>
          </tr>
        </thead>
        <tbody>
          <g:each in="${users}" var="user">
            <tr>
              <td>
                <g:link action="show" id="${user.id}">${user.fullName}</g:link>
              </td>
            </tr>
          </g:each>
        </tbody>
      </table>
    </g:if>
    <g:else>
      <div class="alert-box info">
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sed quidem minima cum commodi, at magni numquam ipsa sunt delectus culpa quaerat tempora architecto porro, recusandae nihil alias reprehenderit neque accusantium.
      </div>
    </g:else>
  </content>
</g:applyLayout>
