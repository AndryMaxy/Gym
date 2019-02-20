<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />
<html>
<head>
    <title><fmt:message key="order.title"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<div class="container mainCont">
    <div class="hat">
        <div class="row justify-content-end align-items-center">
            <form action="controller" method="post" class="col-md-auto"
                  style="margin: 0; margin-right: 25px; padding: 0;">
                <fmt:message key="common.logOut" var="logOut"/>
                <input type="hidden" name="command" value="logOut">
                <input type="submit" value="${logOut}" class="btn btn-outline-primary">
            </form>
        </div>
    </div>
    <div class="botHat">
        <p class="name"><fmt:message key="order.of"/> ${requestScope.user.name} ${requestScope.user.surname}</p>
    </div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="reduce">
        <input type="hidden" name="userId" value="${requestScope.user.id}">
        <div style="margin-top: 50px">
            <div class="row">
                <div class="col-8">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 10%">№</th>
                            <th style="width: 25%"><fmt:message key="order.membership"/></th>
                            <th style="width: 15%"><fmt:message key="order.visitsLeft"/></th>
                            <th style="width: 50%"><fmt:message key="order.feedback"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.bookings}" varStatus="i" var="booking">
                            <tr>
                                <td>${i.index}</td>
                                <td><c:out value="${booking.membership}"/></td>
                                <td><c:out value="${booking.visitCountLeft}"/></td>
                                <td><c:out value="${booking.feedback}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
