<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title>Personal</title>
    <c:import url="/WEB-INF/jsp/header.jsp" charEncoding="utf-8"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="container mt-5" style="background-color: #dfdb9d">
        <div class="row justify-content-between">
            <div class=" my-auto col-9">
                <p style="font-size: 35px">${requestScope.user.name} ${requestScope.user.surname}</p>
                <p style="font-size: 20px"><fmt:message key="visitor.balance"/>: ${requestScope.user.balance}</p>
                <p style="font-size: 20px"><fmt:message key="visitor.discount"/>: ${requestScope.user.discount}%</p>
            </div>
            <form action="controller" method="get" class="my-auto text-right mr-3">
                <fmt:message key="login.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-secondary">
            </form>
        </div>
    </div>
    <div class="container" style="margin-top: 100px">
        <c:choose>
            <c:when test="${requestScope.order == null}">
                <c:import url="/WEB-INF/jsp/visitorNoAppointment.jsp" charEncoding="utf-8"/>
            </c:when>
            <c:when test="${requestScope.appointment == null}">
                WAIT
            </c:when>
            <c:otherwise>
                <c:import url="/WEB-INF/jsp/visitorAppointment.jsp" charEncoding="utf-8"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
