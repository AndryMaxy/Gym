<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<h3 align="center"><fmt:message key="visitor.appointment"/></h3>
<div class="row">
    <div class="col-auto mx-auto">
        <table class="table">
            <thead>
            <tr>
                <th style="width: 25%"><fmt:message key="common.exercise"/></th>
                <th style="width: 25%"><fmt:message key="common.repCount"/></th>
                <th style="width: 25%"><fmt:message key="common.setCount"/></th>
                <th style="width: 25%"><fmt:message key="common.weight"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.appointment.exercises}" var="exercise">
                <tr>
                    <td><c:out value="${exercise.name}"/></td>
                    <td><c:out value="${exercise.repCount}"/></td>
                    <td><c:out value="${exercise.setCount}"/></td>
                    <td><c:out value="${exercise.weight}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<div class="row">
    <div class="col-md-auto mx-auto">
        <table class="table">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="common.product"/></th>
                <th scope="col"><fmt:message key="common.gram"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.appointment.products}" var="product">
                <tr>
                    <td><c:out value="${product.name}"/></td>
                    <td><c:out value="${product.gramInDay}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>



