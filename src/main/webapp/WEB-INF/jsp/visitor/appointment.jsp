<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<h3 align="center"><fmt:message key="visitor.appointment"/></h3>
<c:if test="${requestScope.appointment.exerciseAppointments.size() > 0}">
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
            <c:forEach items="${requestScope.appointment.exerciseAppointments}" var="exerciseAppointment">
                <tr>
                    <td><c:out value="${exerciseAppointment.name}"/></td>
                    <td><c:out value="${exerciseAppointment.repCount}"/></td>
                    <td><c:out value="${exerciseAppointment.setCount}"/></td>
                    <td><c:out value="${exerciseAppointment.weight}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</c:if>
<c:if test="${requestScope.appointment.productAppointments.size() > 0}">
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
                <c:forEach items="${requestScope.appointment.productAppointments}" var="productAppointment">
                    <tr>
                        <td><c:out value="${productAppointment.name}"/></td>
                        <td><c:out value="${productAppointment.gramInDay}"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</c:if>



