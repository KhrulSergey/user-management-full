<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/header.jsp"/>

<head>
    <meta charset="utf-8">
    <title>Error - ${errorMessage}</title>
</head>

<body>
<div class="container">
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <a href="${contextPath}/"><input type="submit" value="Home"></a><br/>
    <h1>Error Page</h1>
    <p>${errorMessage}</p>
</div>

<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>