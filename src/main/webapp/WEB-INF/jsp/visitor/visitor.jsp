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
    <select id="language" name="locale" onchange="mySubmit('lang')" style="font-size: 130%">
        <option value="ru-RU" ${sessionScope.locale.toLanguageTag() == 'ru-RU' ? 'selected' : ''}>Русский</option>
        <option value="en-US" ${sessionScope.locale.toLanguageTag() == 'en-US' ? 'selected' : ''}>English</option>
        <option value="be-BY" ${sessionScope.locale.toLanguageTag() == 'be-BY' ? 'selected' : ''}>Беларускі</option>
    </select>
</form>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div class="botHat">
        <div class="col-3">
            <div class="row">
                <div class="col-md-auto">
                    <p class="param"><fmt:message key="visitor.balance"/>: ${requestScope.user.balance}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-auto">
                    <p class="param"><fmt:message key="visitor.discount"/>: ${requestScope.user.discount}%</p>
                </div>
            </div>
        </div>
        <div class="col-6">
            <p class="name">${requestScope.user.name} ${requestScope.user.surname}</p>
        </div>
        <div class="col-3" style="display: inline-block; vertical-align: middle; float: none">
            <c:if test="${requestScope.booking != null}">
                <div class="row align-items-end">
                    <div class="col">
                        <p class="param" style="text-align: right"><fmt:message key="visitor.currentMembership"/>: ${requestScope.booking.membership}</p>
                    </div>
                </div>
                <div class="row align-items-end">
                    <div class="col">
                        <p class="param" style="text-align: right"><fmt:message key="visitor.visitsLeft"/>: ${requestScope.booking.visitCountLeft}</p>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <div class="row" style="margin-top: 50px">
        <div class="col-md-auto mx-auto">
            <c:choose>
                <c:when test="${requestScope.booking == null}">
                    <c:import url="/WEB-INF/jsp/visitor/noAppointment.jsp" charEncoding="utf-8"/>
                </c:when>
                <c:when test="${requestScope.appointment == null}">
                    <h2 align="center"><fmt:message key="visitor.wait"/></h2>
                    <p style="text-align: center">
                        <fmt:message key="common.image" var="img"/>
                        <img src="${pageContext.request.contextPath}/resources/img/jdyn.jpg" alt="${img}"
                             style="width: 440px; height: 440px;">
                    </p>
                </c:when>
                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/visitor/appointment.jsp" charEncoding="utf-8"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
