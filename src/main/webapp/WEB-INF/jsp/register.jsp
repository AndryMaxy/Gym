<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="header.jsp"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="row form-group">
        <div class="mt-5 mx-auto">
            <h2>Registration</h2>
        </div>
    </div>
</div>
<form action="controller" method="post" class="needs-validation" novalidate>
    <div class="container">
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="text" pattern="\w{6,}" name="login" class="form-control" placeholder="Login" required>
                <div class="invalid-feedback">
                    Login must contain minimum 6 letters or digits.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="password" pattern="(?=.*\d)(?=.*[A-z]).{6,}" name="password" class="form-control"
                       placeholder="Password" autocomplete="current-hash" required>
                <div class="invalid-feedback">
                    Password must contain minimum 5 letters and one digit.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="text" name="name" pattern="[A-z]+" class="form-control" placeholder="Name"
                       autocomplete="current-hash" required>
                <div class="invalid-feedback">
                    Name must contain only letters.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto">
                <input type="text" name="surname" pattern="[A-z]+" class="form-control" placeholder="Surname"
                       autocomplete="current-hash" required>
                <div class="invalid-feedback">
                    Surname must contain only letters.
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="col-md-3 mx-auto" style="text-align: center">
                <button type="submit" name="command" value="signIn" class="btn btn-success btn-lg">Sign in
                </button>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>
