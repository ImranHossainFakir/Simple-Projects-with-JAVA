<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <br/>

    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form"
          action="<c:url value="/signup"/>"
          method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control"
                   id="username"
                   value="${userDTO.username}"
                   name="username"
                   placeholder=""/>

            <c:if test="${errors.username != null}">
                <small class="text-danger"> ${errors.username} </small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password"
                   class="form-control"
                   id="password"
                   name="password"/>
            <c:if test="${errors.password != null}">
                <small class="text-danger"> ${errors.password} </small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" class="form-control"
                   id="confirmPassword"
                   name="confirmPassword"/>
            <c:if test="${errors.confirmPassword != null}">
                <small class="text-danger"> ${errors.confirmPassword} </small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-primary btn-lg"
                    type="submit">
                Signup
            </button>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp"%>