<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Show active users</title>

<meta charset="utf-8">
<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<style type="text/css">
td, th {
	width: 20%;
	font-size: 11px;
	text-align: left;
}

.editButton {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 50px;
}

.editButton:hover {
	background: gold;
}
</style>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>

</head>
<body onload="initMenu()">

	<div class="container">
		<div class="TopPanel">
			<div class="TopLeftColumn">
				<a href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/resources/styles/img/logoTopPanel.png" />
				</a>
			</div>
			<div class="TopCenterColumn">${nameApp}</div>
			<div class="TopRightColumn">
				User:
				<security:authentication property="principal.username" />
			</div>
			<div class="eraserFloat"></div>
		</div>
		<jsp:include page="../menu.jsp" />
		<div class="upperForDown"></div>
		<br>
		<div class="content">
			<div class="logonBackgroundImage">
				<p>List of all Users:</p>
				<table border="1px">
					<tr>
						<th>Login</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Active-status</th>
						<th>Created</th>
						<th>E-mail</th>
						<th>Edit user</th>
					</tr>
					<c:forEach var="tempUser" items="${users}">
						<tr>
							<td>${tempUser.userName}</td>
							<td>${tempUser.firstName}</td>
							<td>${tempUser.lastName}</td>
							<td>${tempUser.enabled}</td>
							<td>${tempUser.created}</td>
							<td>${tempUser.email}</td>
							<form:form
								action="${pageContext.request.contextPath}/adminTools/updateUserForm"
								method="post" modelAttribute="tempUser">
								<input type="hidden" value="${tempUser.id}" name="id" />
								<td><input type="submit" name="submit" value="Edit"
									class="editButton" /></td>
							</form:form>
						<tr />
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="upperForDown"></div>
		<div class="eraserFloat"></div>
	</div>
	<div class="bottomPanel">
		<div class="BottomLeftColumn">
			<a href="${pageContext.request.contextPath}/">Home Page</a>
		</div>
		<div class="BottomRightColumn">
			<div class="helpClick" onclick="adminContact()">${helpLabel}</div>
		</div>
		<div class="eraserFloat"></div>
	</div>

</body>
</html>