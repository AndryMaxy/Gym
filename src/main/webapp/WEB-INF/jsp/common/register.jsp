<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" pageEncoding="utf-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="register.registration"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="row form-group">
        <div class="mt-5 mx-auto">
            <h2><fmt:message key="register.registration"/></h2>
        </div>
    </div>
</div>
<div class="container">
    <div class="row form-group">
        <div class="mx-auto">
            <form action="controller" method="post" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="signIn">
                <div class="container">
                    <div class="row form-group">
                        <div class="mx-auto">
                            <fmt:message key="login.login" var="login"/>
                            <input type="text" pattern="[A-z0-9А-я]{6,}" name="login" class="form-control"
                                   placeholder="${login}"
                                   required>
                            <div class="invalid-feedback">
                                Login must contain minimum 6 letters or digits.
                            </div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="mx-auto">
                            <fmt:message key="login.password" var="password"/>
                            <input type="password" pattern="(?=.*\d)(?=.*[A-zА-я).{6,}" name="password"
                                   class="form-control"
                                   placeholder="${password}" autocomplete="current-hash" required>
                            <div class="invalid-feedback">
                                Password must contain minimum 5 letters and one digit.
                            </div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="mx-auto">
                            <fmt:message key="common.name" var="name"/>
                            <input type="text" name="name" pattern="[A-zА-я]+" class="form-control"
                                   placeholder="${name}" autocomplete="current-hash" required>
                            <div class="invalid-feedback">
                                Name must contain only letters.
                            </div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="mx-auto">
                            <fmt:message key="common.surname" var="surname"/>
                            <input type="text" name="surname" pattern="[A-zА-я]+" class="form-control"
                                   placeholder="${surname}" autocomplete="current-hash" required>
                            <div class="invalid-feedback">
                                Surname must contain only letters.
                            </div>
                        </div>
                    </div>
                    <div class="row form-group">
                        <div class="mx-auto">
                            <fmt:message key="login.signIn" var="sigIn"/>
                            <input type="submit" value="${sigIn}" class="btn btn-success btn-md">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="container">
    <div class="row form-group">
        <div class="mx-auto">
            <a href="${pageContext.request.contextPath}" class="btn btn-secondary btn-sm">
                <fmt:message key="register.main"/></a>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</body>
</html>
