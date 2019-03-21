<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="content" />
<html>
<head>
    <title><fmt:message key="balance.title"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
    <style>
        .payment {
            display: inline-block;
            width: 120px;
            text-align: center;
        }
        .myLabel {
            font-size: 20px;
            text-align: right;
            vertical-align: middle;
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div class="botHat">
        <div class="col-md-auto">
            <p class="name"><fmt:message key="balance.refillBalance"/></p>
        </div>
    </div>
    <div>
        <div style="margin: 0; margin-top: 50px">
            <p style="font-size: 24px; text-align: center; margin-bottom: 0.4rem"><fmt:message
                    key="balance.currentBalance"/>: ${requestScope.user.balance}
            </p>
            <p style="font-size: 24px; text-align: center;"><fmt:message
                    key="balance.currentDiscount"/>: ${requestScope.user.discount}%
            </p>
            <form action="controller" method="post" style="margin-top: 50px; text-align: center">
                <input type="hidden" name="command" value="upBalance">
                <input type="hidden" name="userId" value="${requestScope.user.id}">
                <div class="row justify-content-start align-items-center">
                    <div class="col-4">
                        <p class="myLabel">
                            <fmt:message key="balance.payment"/>:
                        </p>
                    </div>
                    <div class="col-4" style="display: inline-block">
                        <div class="payment">
                            <label style="font-size: 16px; display: block" for="cash">
                                <fmt:message key="balance.cash"/>
                            </label>
                            <input style="transform: scale(1.5)" type="radio" id="cash" name="payment" value="cash"
                                   checked>
                        </div>
                        <div class="payment">
                            <label style="font-size: 16px; display: block" for="card">
                                <fmt:message key="balance.card"/>*
                            </label>
                            <input style="transform: scale(1.5)" type="radio" id="card" name="payment" value="card">
                        </div>
                    </div>
                </div>
                <div class="row justify-content-start align-items-center"  style="margin-top: 20px">
                    <div class="col-4">
                        <p class="myLabel">
                            <fmt:message key="balance.amount"/>:
                        </p>
                    </div>
                    <div class="col-4">
                        <input type="number" min="10" max="1000000" name="balance" style="width: 200px" required>
                    </div>
                </div>
                <div class="form-group" style="margin-top: 20px">
                    <div class="mx-auto">
                        <fmt:message key="balance.refillBtn" var="refill"/>
                        <input type="submit" value="${refill}" class="btn btn-success">
                    </div>
                </div>
            </form>
        </div>
        <p style="margin-top: 170px; margin-left: 150px">
            *<fmt:message key="balance.star"/>
        </p>
    </div>
</body>
</html>
