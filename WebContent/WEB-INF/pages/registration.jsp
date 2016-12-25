<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            padding-top : 40px !important;
        }

    </style>
    <link href="<c:url value="/resources/css/united.css"/>" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration</title>
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
                    <li><a href="${pageContext.request.contextPath}/registration" title="Registration">Register</a></li>
                </c:if>
                <c:if test="${currentuser != null}">
                    <li>${currentuser}</li>
                    <li><a href="${pageContext.request.contextPath}/logout" title="LogOut">LogOut</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<div class="container body-content">
    <fieldset>
    <c:choose>
    	<c:when test="${username != null}">
	         <div class = "control-group">
	         <h1>Verification:</h1>
					<img
						src="${pageContext.request.contextPath}/verification?username=${username}">
	         </div>
	         <form action="${pageContext.request.contextPath}/verifyRegistration"
					method="post">
					<div class="control-group">
						<label class="control-label">Enter Your Key:</label>
						<div class="controls">
							<input type="text" cssClass="input-xlarge" name="code" value="" />
							<input type="hidden" name="username" value="${username}"/>
							<input type="hidden" name="email" value="${email}"/>
							<input type="hidden" name="password" value="${password}"/>
						</div>
					</div>
					<br>
					<input type="submit" value="Submit" class="btn btn-primary">
				</form>
				<c:if test="${success == false}">
					<h2>Incorrect key! Please, try again</h2>
				</c:if>
	     </c:when>
	     <c:otherwise>
        <h1>Registration</h1>
        <form:form commandName="person" cssClass="form-horizontal">

        <div class="control-group">
            <label class="control-label">Login:</label>
            <div class="controls">
                <form:input  cssClass="input-xlarge" path="login"/>
                <span class="error"><form:errors path="login" /></span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">E-mail:</label>
            <div class="controls">
                <form:input cssClass="input-xlarge" path="email" value=""/>
                <span class="error"><form:errors path="email" /></span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">Password:</label>
            <div class="controls">
                <form:password cssClass="input-xlarge" path="password" value=""/>
                <span class="error"><form:errors path="password" /></span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">Repeat Password:</label>
            <div class="controls">
                <form:password cssClass="input-xlarge" path="repeatPassword"/>
                <span class="error"><form:errors path="repeatPassword" /></span>
            </div>
            <br>
            <div class="form-actions">
                <input type="submit" value="Submit" class="btn btn-primary">
            </div>
         </div>
         
         </form:form>
         </c:otherwise>
    </c:choose>
    </fieldset>
    <hr />
    <footer>
    </footer>
</div>
</body>

</html>
