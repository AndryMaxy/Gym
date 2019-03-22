<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" pageEncoding="utf-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html lang="${sessionScope.locale}">
<head>
    <title><fmt:message key="login.welcome"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div style="margin-top: 70px">
        <div class="row form-group">
            <div class="mt-5 mx-auto">
                <h2><fmt:message key="login.welcome"/></h2>
            </div>
        </div>
        <c:out value="${requestScope.text}"/>
        <form action="controller" method="post" class="needs-validation" id="loginForm" novalidate>
            <input type="hidden" name="command" id="command" value="logIn"/>
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.login" var="login"/>
                    <input id="loginIn" type="text" pattern="[A-Za-z0-9А-я]{6,15}" name="login" class="form-control"
                           placeholder="${login}"
                           required>
                    <div class="invalid-feedback">
                        <fmt:message key="login.correctLogin"/>
                    </div>
                </div>
            </div>
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.password" var="password"/>
                    <input id="passwordIn" type="password" pattern="^(?=.*[A-Za-zА-яёЁ])(?=.*\d)[A-Za-zА-яёЁ\d]{6,}$" name="password" class="form-control"
                           placeholder="${password}"
                           autocomplete="current-pass" required>
                    <div class="invalid-feedback">
                        <fmt:message key="login.correctPassword"/>
                    </div>
                </div>
            </div>
            <c:if test="${param.error == 'true'}">
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.incorrectData"/>
                </div>
            </div>
            </c:if>
            <div id="invalidInputMsg" class="row form-group" style="display: none">
                <div class="mx-auto">
                    <fmt:message key="login.incorrectData"/>
                </div>
            </div>
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.logIn" var="logIn"/>
                    <input id="submitBnt" type="submit" value="${logIn}" class="btn btn-success btn-lg"/>
                </div>
            </div>
        </form>
        <div class="row form-group">
            <div class="row mx-auto">
                <p class="my-auto mr-3">
                    <fmt:message key="login.noAccount"/>
                </p>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-secondary btn-sm">
                    <fmt:message key="login.signIn"/>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
