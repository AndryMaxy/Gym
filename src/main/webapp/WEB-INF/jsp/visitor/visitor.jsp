<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="tag/user" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<head>
    <title><fmt:message key="common.personal"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
    <fmt:message key="visitor.buySure" var="sure"/>
    <script>
        function buyMembership(form) {
            return confirm('${sure}' + "?");
        }
    </script>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
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
            <p class="name"><my:userTag name="${requestScope.user.name}" surname="${requestScope.user.surname}"/></p>
        </div>
        <div class="col-3" style="display: inline-block; vertical-align: middle; float: none">
            <c:if test="${requestScope.booking != null}">
                <div class="row align-items-end">
                    <div class="col">
                        <p class="param" style="text-align: right"><fmt:message
                                key="visitor.currentMembership"/>: ${requestScope.booking.membership}</p>
                    </div>
                </div>
                <div class="row align-items-end">
                    <div class="col">
                        <p class="param" style="text-align: right"><fmt:message
                                key="visitor.visitsLeft"/>: ${requestScope.booking.visitCountLeft}</p>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <div class="row" style="margin-top: 50px">
        <div class="col-md-auto mx-auto">
            <c:choose>
                <c:when test="${requestScope.booking == null || requestScope.booking.visitCountLeft == 0}">
                    <c:import url="/WEB-INF/jsp/visitor/noAppointment.jsp" charEncoding="utf-8"/>
                </c:when>
                <c:when test="${requestScope.booking.needAppointment == false}">
                    <h2 align="center"><fmt:message key="visitor.thanks"/></h2>
                    <p style="text-align: center">
                        <fmt:message key="common.image" var="img"/>
                        <img src="${pageContext.request.contextPath}/resources/img/jdyn.jpg" alt="${img}"
                             style="width: 480px; height: 480px;">
                    </p>
                </c:when>
                <c:when test="${requestScope.appointment == null}">
                    <div class="row row justify-content-center align-items-center">
                        <h2 align="center" class="col-10"><fmt:message key="visitor.wait"/></h2>
                        <form action="controller" method="post" class="col-2" style="margin-bottom: 0">
                            <input type="hidden" name="command" value="refuse">
                            <input type="hidden" name="bookingId" value="${requestScope.booking.id}">
                            <button type="submit" class="btn btn-secondary">
                                <fmt:message key="visitor.refuse"/>
                            </button>
                        </form>
                    </div>
                    <p style="text-align: center">
                        <fmt:message key="common.image" var="img"/>
                        <img src="${pageContext.request.contextPath}/resources/img/jdyn.jpg" alt="${img}"
                             style="width: 480px; height: 480px;">
                    </p>
                </c:when>
                <c:otherwise>
                    <c:import url="/WEB-INF/jsp/visitor/appointment.jsp" charEncoding="utf-8"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <c:if test="${param.error == 'true'}">
        <div class="row">
            <div class="mx-auto" style="font-size: 24px">
                <fmt:message key="visitor.lowBalance"/>
            </div>
        </div>
    </c:if>
</div>
</body>
