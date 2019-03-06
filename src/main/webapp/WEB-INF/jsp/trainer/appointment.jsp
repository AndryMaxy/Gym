<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="trainer.appointment"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div class="botHat">
        <div class="col-md-auto">
            <p class="name"><fmt:message key="trainer.for"/> <c:out value="${requestScope.visitor.name} ${requestScope.visitor.surname}"/></p>
        </div>
    </div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="appoint">
        <input type="hidden" name="userId" value="${requestScope.visitor.id}">
        <div style="margin-top: 50px">
            <div class="row">
                <div class="col-8">
                    <table class="table">
                        <thead>
                        <tr>
                            <th style="width: 25%"><fmt:message key="common.exercise"/></th>
                            <th style="width: 25%"><fmt:message key="common.repCount"/></th>
                            <th style="width: 25%"><fmt:message key="common.setCount"/></th>
                            <th style="width: 25%"><fmt:message key="common.weight"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <%--<tbody>--%>
                        <%--<c:forEach items="${requestScope.appointment.exerciseAppointments}" var="exercise">--%>
                            <%--<tr bgcolor="#EBEBE4" id="trEx-${exercise.id}">--%>
                                <%--<td><c:out value="${exercise.name}"/></td>--%>
                                <%--<td><input type="number" min="5" max="200" name="repCount-${exercise.id}" disabled>--%>
                                <%--</td>--%>
                                <%--<td><input type="number" min="1" max="20" name="setCount-${exercise.id}" disabled>--%>
                                <%--</td>--%>
                                <%--<td><input type="number" min="0" max="200" name="weight-${exercise.id}" disabled>--%>
                                <%--</td>--%>
                                <%--<td><input type="checkbox" name="checkerEx-${exercise.id}"--%>
                                           <%--onclick="checkExercise('${exercise.id}')" style="transform: scale(1.8)"></td>--%>
                            <%--</tr>--%>
                        <%--</c:forEach>--%>
                        <%--</tbody>--%>
                        <tbody>
                        <c:forEach items="${requestScope.appointment.exerciseAppointments}" var="exercise">
                            <tr bgcolor="#EBEBE4" id="trEx-${exercise.id}">
                                <td><c:out value="${exercise.name}"/></td>
                                <td><input type="number" min="5" max="200" name="exercise-${exercise.id}" disabled>
                                </td>
                                <td><input type="number" min="1" max="20" name="exercise-${exercise.id}" disabled>
                                </td>
                                <td><input type="number" min="0" max="200" name="exercise-${exercise.id}" disabled>
                                </td>
                                <td><input type="checkbox" name="checkerEx-${exercise.id}"
                                           onclick="checkExercise('${exercise.id}')" style="transform: scale(1.8)"></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-3 ml-auto">
                    <table class="table">
                        <thead>
                        <tr>
                            <th style="width: 25%"><fmt:message key="common.product"/></th>
                            <th style="width: 25%"><fmt:message key="common.gram"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <%--<tbody>--%>
                        <%--<c:forEach items="${requestScope.appointment.productAppointments}" varStatus="status" var="product">--%>
                            <%--<tr bgcolor="#EBEBE4" id="trPr-${product.id}">--%>
                                <%--<td><c:out value="${product.name}"/></td>--%>
                                <%--<td><input type="number" min="20" max="1000" name="gram-${product.id}" disabled>--%>
                                <%--</td>--%>
                                <%--<td><input type="checkbox" name="checkerPr-${product.id}"--%>
                                           <%--onclick="checkProduct('${product.id}')" style="transform: scale(1.8)"></td>--%>
                            <%--</tr>--%>
                        <%--</c:forEach>--%>
                        <%--</tbody>--%>
                        <tbody>
                        <c:forEach items="${requestScope.appointment.productAppointments}" varStatus="status" var="product">
                            <tr bgcolor="#EBEBE4" id="trPr-${product.id}">
                                <td><c:out value="${product.name}"/></td>
                                <td><input type="number" min="20" max="1000" name="product-${product.id}" disabled>
                                </td>
                                <td><input type="checkbox" name="checkerPr-${product.id}"
                                           onclick="checkProduct('${product.id}')" style="transform: scale(1.8)"></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="mx-auto">
                <fmt:message key="trainer.appoint" var="appoint"/>
                <input type="submit" value="${appoint}" class="btn btn-success btn-lg">
            </div>
        </div>
    </form>
</div>
</body>
</html>
