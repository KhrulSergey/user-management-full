<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<jsp:include page="../fragments/header.jsp"/>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<h1> You have been logged out.</h1>
	<a href="${contextPath}/"><input type="submit" value="Go to the home page"></a><br/><br>
	</div>

	<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>