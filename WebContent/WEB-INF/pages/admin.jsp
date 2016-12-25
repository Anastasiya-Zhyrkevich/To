<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <link href="<c:url value="/resources/css/united.css"/>" rel="stylesheet" type="text/css">
    <script src="<c:url value="/resources/scripts/jquery-2.1.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/scripts/bootstrap.js"/>" type="text/javascript"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
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
                <c:if test="${currentuser != null}">
                    <li><a href="${pageContext.request.contextPath}/admin" title ="User" >${currentuser}</a></li>
                </c:if>
                <c:if test="${currentuser != null}">
                    <li><a href="${pageContext.request.contextPath}/logout" title ="User" >Logout</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>
<div class="container body-content">
    <h1>Manage Users and Resources</h1>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/manageUsers" role="button">Manage Users</a>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/manageResources" role="button">Manage Resources</a>
    <a class="btn btn-default" href="${pageContext.request.contextPath}/addLink" role="button">Add User-Resource link</a>
    <table class="table table-striped table-hover">

        <!-- column headers -->
        <thead>
        <th>User</th>
        <th>Resource</th>
        <th>Edit</th>
        <th>Delete</th>
        <th>View</th>
        <th></th>
        <th></th>
        </thead>
        <!-- column data -->
        <tbody>
        <c:forEach var="row" items="${userResourceLinks}">
            <tr>
                <td><c:out value="${row.getUserName()}"/></td>
                <td><c:out value="${row.getResourceName()}"/></td>
                <td><c:if test="${row.getEditPermission() == true}">
                    <input type="checkbox" checked disabled/>
                </c:if>
                    <c:if test="${row.getEditPermission() == false}">
                        <input type="checkbox" disabled/>
                    </c:if>
                </td>
                <td><c:if test="${row.getDeletePermission() == true}">
                    <input type="checkbox" checked disabled/>
                </c:if>
                    <c:if test="${row.getDeletePermission() == false}">
                        <input type="checkbox" disabled/>
                    </c:if>
                </td>
                <td><c:if test="${row.getViewPermission() == true}">
                    <input type="checkbox" checked disabled/>
                </c:if>
                    <c:if test="${row.getViewPermission() == false}">
                        <input type="checkbox" disabled/>
                    </c:if>
                </td>
                <td><a class="btn btn-default" href="${pageContext.request.contextPath}/editLink?id=${row.getUserResourceLinkId()}" role="button">Edit Link</a></td>
                <td><a class="btn btn-default" href="${pageContext.request.contextPath}/deleteLink?id=${row.getUserResourceLinkId()}" role="button">Delete Link</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr />
    <footer>
    </footer>
</div>


</body>
</html>

