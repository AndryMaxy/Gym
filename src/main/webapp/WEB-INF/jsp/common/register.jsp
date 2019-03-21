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
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div style="margin-top: 70px">
        <div class="row form-group">
            <div class="mt-5 mx-auto">
                <h2><fmt:message key="register.registration"/></h2>
            </div>
        </div>
        <div class="row form-group">
            <div class="mx-auto">
                <form action="controller" method="post" class="needs-validation" novalidate>
                    <input type="hidden" name="command" value="signIn">
                    <div class="container">
                        <div class="row form-group">
                            <div class="mx-auto">
                                <fmt:message key="login.login" var="login"/>
                                <input type="text" pattern="[A-Za-z0-9А-я]{6,15}" name="login" class="form-control"
                                       placeholder="${login}"
                                       required>
                                <div class="invalid-feedback">
                                    <fmt:message key="register.incorrectLogin"/>
                                </div>
                            </div>
                        </div>
                        <c:if test="${param.error == 'true'}">
                            <div class="row form-group">
                                <div class="mx-auto">
                                    <fmt:message key="register.exists"/>
                                </div>
                            </div>
                        </c:if>
                        <div class="row form-group">
                            <div class="mx-auto">
                                <fmt:message key="login.password" var="password"/>
                                <input type="password" pattern="^(?=.*[A-Za-zА-яёЁ])(?=.*\d)[A-Za-zА-яёЁ\d]{6,}$" name="password"
                                       class="form-control"
                                       placeholder="${password}" autocomplete="current-hash" required>
                                <div class="invalid-feedback">
                                    <fmt:message key="register.incorrectPassword"/>
                                </div>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="mx-auto">
                                <fmt:message key="common.name" var="name"/>
                                <input type="text" name="name" pattern="[A-Za-zА-я]+" class="form-control"
                                       placeholder="${name}" autocomplete="current-hash" required>
                                <div class="invalid-feedback">
                                    <fmt:message key="register.incorrectName"/>
                                </div>
                            </div>
                        </div>
                        <div class="row form-group">
                            <div class="mx-auto">
                                <fmt:message key="common.surname" var="surname"/>
                                <input type="text" name="surname" pattern="[A-Za-zА-я]+" class="form-control"
                                       placeholder="${surname}" autocomplete="current-hash" required>
                                <div class="invalid-feedback">
                                    <fmt:message key="register.incorrectSurname"/>
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
    <div class="row form-group">
        <div class="mx-auto">
            <a href="${pageContext.request.contextPath}" class="btn btn-secondary btn-sm">
                <fmt:message key="register.main"/></a>
        </div>
    </div>
</div>
</body>
</html>
