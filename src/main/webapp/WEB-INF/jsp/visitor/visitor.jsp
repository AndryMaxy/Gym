<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<form name="lang" action="controller" style="text-align: right; margin: 0">
    <input type="hidden" name="command" value="locale"/>
    <select id="language" name="locale" onchange="mySubmit()" style="font-size: 130%">
        <option value="ru-RU" ${sessionScope.locale.toLanguageTag() == 'ru-RU' ? 'selected' : ''}>Русский</option>
        <option value="en-US" ${sessionScope.locale.toLanguageTag() == 'en-US' ? 'selected' : ''}>English</option>
        <option value="be-BY" ${sessionScope.locale.toLanguageTag() == 'be-BY' ? 'selected' : ''}>Беларускі</option>
    </select>
    <script>
        function mySubmit() {
            document.lang.submit();
        }
    </script>
</form>
<div class="container mainCont">
    <div class="hat">
        <div class="row justify-content-between align-items-center">
            <div class="col-md-auto">
                <p class="name">${requestScope.user.name} ${requestScope.user.surname}</p>
            </div>
            <form action="controller" method="post" class="col-md-auto"  style="margin: 0; margin-right: 25px; padding: 0;">
                <fmt:message key="common.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-outline-primary" style="color: #f4ffff">
            </form>
        </div>
        <div class="row" style="margin-top: 5px">
            <div class="col-md-auto">
            <p class="param"><fmt:message key="visitor.balance"/>: ${requestScope.user.balance}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-auto">
            <p class="param"><fmt:message key="visitor.discount"/>: ${requestScope.user.discount}%</p>
            </div>
        </div>
        <c:if test="${requestScope.booking != null}">
            <div class="row">
                <div class="col-md-auto">
                <p class="param"><fmt:message key="visitor.currentMembership"/>: ${requestScope.booking.membership}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-auto">
                    <p class="param"><fmt:message key="visitor.visitsLeft"/>: ${requestScope.booking.visitCountLeft}</p>
                </div>
            </div>
        </c:if>
    </div>
    <div class="container" style="margin-top: 75px">
        <c:choose>
            <c:when test="${requestScope.booking == null}">
                <c:import url="/WEB-INF/jsp/visitor/noAppointment.jsp" charEncoding="utf-8"/>
            </c:when>
            <c:when test="${requestScope.appointment == null}">
                <h2 align="center"><fmt:message key="visitor.wait"/></h2>
                <p style="text-align: center">
                    <fmt:message key="common.image" var="img"/>
                    <img src="${pageContext.request.contextPath}/img/jdyn.jpg" alt="${img}"
                         style="width: 440px; height: 440px;">
                </p>
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/jsp/visitor/appointment.jsp" charEncoding="utf-8"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
