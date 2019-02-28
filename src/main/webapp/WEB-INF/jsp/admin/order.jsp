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
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div class="botHat">
        <div class="col-md-auto">
            <p class="name"><fmt:message key="order.of"/> <c:out value="${requestScope.user.name} ${requestScope.user.surname}"/></p>
        </div>
    </div>
    <c:set var="size" value="${requestScope.bookings.size()}"/>
    <c:if test="${size != 0}">
        <c:set var="lastBooking" value="${requestScope.bookings.get(size - 1)}"/>
        <c:set var="visitCountLeft" value="${lastBooking.visitCountLeft}"/>
    </c:if>
    <div class="row justify-content-center align-items-center" style="margin: 0; margin-top: 20px">
        <div class="col mx-auto">
            <form name="discountForm" action="controller" style="text-align: right; margin: 0">
                <p style="font-size: 24px; text-align: center; margin-bottom: 0"><fmt:message
                        key="order.discount"/>:
                    <input type="hidden" name="command" value="discount"/>
                    <input type="hidden" name="userId" value="${requestScope.user.id}"/>
                    <select name="discount" onchange="mySubmit('discountForm')" style="font-size: 100%">
                        <option value="0" ${requestScope.user.discount == '0' ? 'selected' : ''}>0</option>
                        <option value="5" ${requestScope.user.discount == '5' ? 'selected' : ''}>5</option>
                        <option value="10" ${requestScope.user.discount == '10' ? 'selected' : ''}>10</option>
                        <option value="15" ${requestScope.user.discount == '15' ? 'selected' : ''}>15</option>
                        <option value="20" ${requestScope.user.discount == '20' ? 'selected' : ''}>20</option>
                        <option value="25" ${requestScope.user.discount == '25' ? 'selected' : ''}>25</option>
                    </select>
                </p>
            </form>
        </div>
    </div>
    <c:choose>
        <c:when test="${size != 0}">
            <c:if test="${visitCountLeft > 0}">
                <div class="row justify-content-center align-items-center" style="margin: 0; margin-top: 40px">
                    <p style="font-size: 24px"><fmt:message
                            key="order.current"/>: ${visitCountLeft}
                    </p>
                    <form action="controller" method="post" style="margin-left: 20px">
                        <input type="hidden" name="command" value="reduce">
                        <input type="hidden" name="userId" value="${requestScope.user.id}">
                        <input type="hidden" name="bookingId" value="${lastBooking.id}">
                        <fmt:message key="order.reduce" var="reduce"/>
                        <input type="submit" value="${reduce}" class="btn btn-success">
                    </form>
                </div>
            </c:if>
            <table class="table" style="margin-top: 50px">
                <thead>
                <tr>
                    <th style="width: 5%">№</th>
                    <th style="width: 15%"><fmt:message key="order.membership"/></th>
                    <th style="width: 10%"><fmt:message key="order.visitsLeft"/></th>
                    <th style="width: 70%"><fmt:message key="order.feedback"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.bookings}" varStatus="i" var="booking">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td><c:out value="${booking.membership}"/></td>
                        <td><c:out value="${booking.visitCountLeft}"/></td>
                        <td><c:out value="${booking.feedback}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div style="text-align: center; margin-top: 5px">
                <h2><fmt:message key="order.no"/></h2>
                <fmt:message key="common.image" var="img"/>
                <img src="${pageContext.request.contextPath}/resources/img/noOrders.gif" alt="${img}"
                     style="width: 600px; height: 600px;">
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
