<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/header.jsp"/>

<head>
    <meta charset="utf-8">
    <title>User Home</title>
</head>

<body>
<div class="container">
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <form:form action="${contextPath}/logout" method="post">
        <input type="submit" value="Logout">
    </form:form>

    <h1>Welcome, ${user.firstName} </h1>

    <a href="${contextPath}/update">Update your profile</a> <br/>
</div>

<jsp:include page="../fragments/footer.jsp"/>

</body>
</html>