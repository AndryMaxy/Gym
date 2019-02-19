<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="container mt-5" style="background-color: #dfdb9d">
        <div class="row justify-content-between">
            <div class=" my-auto col-9">
                <p style="font-size: 35px">${requestScope.user.name} ${requestScope.user.surname}</p>
                <p style="font-size: 20px"><fmt:message key="visitor.balance"/>: ${requestScope.user.balance}</p>
                <p style="font-size: 20px"><fmt:message key="visitor.discount"/>: ${requestScope.user.discount}%</p>
                <c:if test="${requestScope.booking != null}">
                    <p style="font-size: 20px"><fmt:message key="visitor.visitsLeft"/>: ${requestScope.booking.visitsLeft}</p>
                </c:if>
            </div>
            <form action="controller" method="post" class="my-auto text-right mr-3">
                <fmt:message key="login.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-secondary">
            </form>
        </div>
    </div>
    <div class="container" style="margin-top: 75px">
        <c:choose>
            <c:when test="${requestScope.booking == null}">
                <c:import url="/WEB-INF/jsp/visitor/noAppointment.jsp" charEncoding="utf-8"/>
            </c:when>
            <c:when test="${requestScope.appointment == null}">
                <h2 align="center"><fmt:message key="visitor.wait"/></h2>
                <p style="text-align: center">
                    <img src="${pageContext.request.contextPath}/img/jdyn.jpg" alt="can't load image sorry"
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
