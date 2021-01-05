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
    <title>Sign Up Form</title>
</head>

<body>

<div class="container">
    <%--@elvariable id="userNew" type="mvc.management.model.User"--%>
    <form:form class="form-horizontal" method="post" modelAttribute="userNew" action="register">

        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">UserName</label>
                <div class="col-sm-10">
                    <form:input path="username" type="text" class="form-control " id="username" placeholder="UserName"/>
                    <form:errors path="username" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <form:password path="password" class="form-control " id="password" placeholder="Password"/>
                    <form:errors path="password" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="firstName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">First Name</label>
                <div class="col-sm-10">
                    <form:input path="firstName" type="text" class="form-control " id="firstName"
                                placeholder="First Name"/>
                    <form:errors path="firstName" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="lastName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Last Name</label>
                <div class="col-sm-10">
                    <form:input path="lastName" type="text" class="form-control " id="lastName"
                                placeholder="Last Name"/>
                    <form:errors path="lastName" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="gender">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Gender</label>
                <div class="col-sm-5">
                    <form:select path="gender" class="form-control">
                        <form:option value="NONE" label="--- Select ---"/>
                        <form:options items="${genderList}"/>
                    </form:select>
                    <form:errors path="gender" class="control-label"/>
                </div>
                <div class="col-sm-5"></div>
            </div>
        </spring:bind>
        <spring:bind path="birthDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Birth Date</label>
                <div class="col-sm-10">
                    <form:input type="date" path="birthDate" spring:field="*{birthDate}"
                                pattern="(0[1-9]|1[0-9]|2[0-9]|3[01]).(0[1-9]|1[012]).[0-9]{4}" id="birthDate"/>
                    <form:errors path="birthDate" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <spring:bind path="about">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">About</label>
                <div class="col-sm-10">
                    <form:textarea path="about" rows="5" class="form-control" id="about" placeholder="About"/>
                    <form:errors path="about" class="control-label"/>
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <form:button type="submit" class="btn-lg btn-primary pull-center">Add</form:button>
            </div>
        </div>
    </form:form>

    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <a href="${contextPath}/"><input type="submit" value="Home"></a><br/>
</div>

<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>