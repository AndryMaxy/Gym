<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Welcome</title>
    <jsp:include page="header.jsp"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="row form-group">
        <div class="mt-5 mx-auto">
            <h2>Welcome</h2>
        </div>
    </div>
</div>
<form action="controller" method="post" class="needs-validation" novalidate>
    <div class="container">
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="text" pattern="[A-z0-9]{6,}" name="login" class="form-control" placeholder="Login" required>
                <div class="invalid-feedback">
                    Please inter correct login.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="password" pattern="[A-z0-9]{6,}" name="password" class="form-control" placeholder="Password"
                       autocomplete="current-hash" required>
                <div class="invalid-feedback">
                    Please inter correct password.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto" style="text-align: center">
                <button type="submit" name="command" value="logIn" class="btn btn-success btn-lg">Log in
                </button>
            </div>
        </div>
    </div>
</form>
<div class="container">
    <div class="col-md-3 mx-auto">
        <form action="controller" method="get" class="row mx-auto">
            <p class="my-auto mr-3">Do not have account?</p>
            <button type="submit" name="get" value="/WEB-INF/jsp/register.jsp" id="signIn" class="btn btn-secondary">Sign in</button>
        </form>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>
