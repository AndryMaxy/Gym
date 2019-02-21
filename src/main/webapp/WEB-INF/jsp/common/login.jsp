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
<form name="lang" action="controller" style="text-align: right; margin: 0">
    <input type="hidden" name="command" value="locale"/>
    <select id="language" name="locale" onchange="mySubmit('lang')" style="font-size: 130%">
        <option value="ru-RU" ${sessionScope.locale.toLanguageTag() == 'ru-RU' ? 'selected' : ''}>Русский</option>
        <option value="en-US" ${sessionScope.locale.toLanguageTag() == 'en-US' ? 'selected' : ''}>English</option>
        <option value="be-BY" ${sessionScope.locale.toLanguageTag() == 'be-BY' ? 'selected' : ''}>Беларускі</option>
    </select>
</form>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div style="margin-top: 70px">
        <div class="row form-group">
            <div class="mt-5 mx-auto">
                <h2><fmt:message key="login.welcome"/></h2>
            </div>
        </div>
        <form action="controller" method="post" class="needs-validation" novalidate>
            <input type="hidden" name="command" value="logIn"/>
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.login" var="login"/>
                    <input type="text" pattern="[A-z0-9]{6,}" name="login" class="form-control"
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
                    <input type="password" pattern="[A-z0-9]{6,}" name="password" class="form-control"
                           placeholder="${password}"
                           autocomplete="current-hash" required>
                    <div class="invalid-feedback">
                        <fmt:message key="login.correctPassword"/>
                    </div>
                </div>
            </div>
            <div class="row form-group">
                <p class="mx-auto">
                    <c:if test="${requestScope.isExister}">
                        <fmt:message key="login.incorrectData"/>
                    </c:if>
                </p>
            </div>
            <div class="row form-group">
                <div class="mx-auto">
                    <fmt:message key="login.logIn" var="logIn"/>
                    <input type="submit" value="${logIn}" class="btn btn-success btn-lg"/>
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
