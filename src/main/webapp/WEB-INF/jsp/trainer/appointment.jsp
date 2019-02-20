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
        <p class="name"><fmt:message key="trainer.for"/> ${requestScope.visitor.name} ${requestScope.visitor.surname}</p>
    </div>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="appoint">
        <input type="hidden" name="userId" value="${requestScope.visitor.id}">
        <div style="margin-top: 50px">
            <div class="row">
                <div class="col-8">
                    <table class="table table-bordered">
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
                            <tr bgcolor="#EBEBE4" id="trEx-${exercise.id}">
                                <td><c:out value="${exercise.name}"/></td>
                                <td><input type="number" min="5" max="200" name="repCount-${exercise.id}" disabled>
                                </td>
                                <td><input type="number" min="1" max="20" name="setCount-${exercise.id}" disabled>
                                </td>
                                <td><input type="number" min="1" max="200" name="weight-${exercise.id}" disabled>
                                </td>
                                <td><input type="checkbox" name="checkerEx-${exercise.id}"
                                           onclick="checkExercise('${exercise.id}')" style="transform: scale(1.8)"></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-3 ml-auto">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th style="width: 25%"><fmt:message key="common.product"/></th>
                            <th style="width: 25%"><fmt:message key="common.gram"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.appointment.products}" varStatus="status" var="product">
                            <tr bgcolor="#EBEBE4" id="trPr-${product.id}">
                                <td><c:out value="${product.name}"/></td>
                                <td><input type="number" min="5" max="200" name="gram-${product.id}" disabled>
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
    <script>
        function checkExercise(id) {
            var rep = 'repCount-' + id;
            var repEl = document.getElementsByName(rep).item(0);
            var set = 'setCount-' + id;
            var setEl = document.getElementsByName(set).item(0);
            var weight = 'weight-' + id;
            var weightEl = document.getElementsByName(weight).item(0);
            var checker = 'checkerEx-' + id;
            var checkerEl = document.getElementsByName(checker).item(0);
            var tr = 'trEx-' + id;
            var trEl = document.getElementById(tr);
            if (checkerEl.checked) {
                repEl.disabled = false;
                setEl.disabled = false;
                weightEl.disabled = false;
                trEl.bgColor = "#ffffff";
            } else {
                repEl.disabled = true;
                setEl.disabled = true;
                weightEl.disabled = true;
                trEl.bgColor = "#EBEBE4";
            }
        }
        function checkProduct(id) {
            var gram = 'gram-' + id;
            var gramEl = document.getElementsByName(gram).item(0);
            var checker = 'checkerPr-' + id;
            var checkerEl = document.getElementsByName(checker).item(0);
            var tr = 'trPr-' + id;
            var trEl = document.getElementById(tr);
            if (checkerEl.checked) {
                gramEl.disabled = false;
                trEl.bgColor = "#ffffff";
            } else {
                gramEl.disabled = true;
                trEl.bgColor = "#EBEBE4";
            }
        }
    </script>
</div>
</body>
</html>
