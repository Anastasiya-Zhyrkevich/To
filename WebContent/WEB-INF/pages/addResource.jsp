<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <link href="<c:url value="/resources/css/united.css"/>" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Log In</title>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-responsive-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

        </div>
        <div class="navbar-collapse collapse navbar-responsive-collapse">

            <ul class="nav navbar-nav navbar-right">
                <li> <a href="${pageContext.request.contextPath}/" title="Home">Back</a></li>
                <c:if test="${currentuser == null}">
                    <li><a href="${pageContext.request.contextPath}/login" title ="LogIn" id="LoginPopup">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/registration" title="Registration">Registration</a></li>
                </c:if>
                <c:if test="${currentuser != null}">
                    <li><a href="${pageContext.request.contextPath}/admin" title ="User" >${currentuser}</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" title="LogOut">LogOut</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<div class="container body-content">
    <h1>Resource </h1>
    <form:form commandName="resource" cssClass="form-horizontal">
        <div class="control-group">
            <label class="control-label">Name:</label>
            <div class="controls">
                <form:input cssClass="input-xlarge" path="name" value=""/>
                <span class="error"><form:errors path="name" /></span>
            </div>
            <label class="control-label">Info:</label>
            <div class="controls">
                <form:textarea cssClass="input-xlarge" path="info" value=""/>
                <span class="error"><form:errors path="info" /></span>
            </div>
            <div class="controls">
                <form:input cssClass="input-xlarge" path="resourceId" value="" type="hidden"/>
                <span class="error"><form:errors path="resourceId" /></span>
            </div>
        </div>
        <br>
        <div class="form-actions">
            <tr><td><input type="submit" value="Submit" class="btn btn-primary"></td></tr>
        </div>

    </form:form>
    <hr />
    <footer>
    </footer>
</div>
</body>
</html>
