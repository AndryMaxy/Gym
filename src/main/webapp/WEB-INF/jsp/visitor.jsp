<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Personal</title>
    <jsp:include page="header.jsp"/>
</head>
<body class="bg-light">
<div class="container">
    <div class="row">
        <div class="col">
            ${visitor.name}
        </div>
        <div class="col">
            ${visitor.surname}
        </div>
    </div>
</div>
</body>
</html>
