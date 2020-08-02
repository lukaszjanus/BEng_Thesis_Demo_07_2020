<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Disabled users</title>
<meta charset="utf-8">

<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>

<style type="text/css">
td, th {
	width: 20%;
	font-size: 11px;
	text-align: left;
}

.enableButton {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 75px;
}

.enableButton:hover {
	background: gold;
}
</style>

<script>
	window.addEventListener('load', function() {

		if (readCookie('enableUser') != null) {

			var valueCookie = readCookie('enableUser');

			alert("User " + valueCookie + " has been enabled.");

			/* delete cookie after load site - expires to '0' */
			var date = new Date();
			date.setTime(date.getTime() + (0));

			var expires = "";
			expires = "; expires=" + date.toUTCString();

			document.cookie = "newUser=;" + expires;
		}
	})

	function readCookie(name) {

		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) == 0) {
				return c.substring(nameEQ.length, c.length);
			}
		}
		return null;
	}
</script>

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
		<div class="content">
			<div class="forDown"></div>
			<div class="logonBackgroundImage">
				<div>
					<div class="forDown"></div>
					<div class="forDown"></div>
					<p>Disabled Users:</p>
					<table border="1px">
						<tr>

							<th>Login</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Created</th>
							<th>E-mail</th>
							<th>Enable user</th>
						</tr>
						<c:forEach var="tempUser" items="${users}">
							<tr>
								<td>${tempUser.userName}</td>
								<td>${tempUser.firstName}</td>
								<td>${tempUser.lastName}</td>
								<td>${tempUser.created}</td>
								<td>${tempUser.email}</td>
								<form:form
									action="${pageContext.request.contextPath}/adminTools/enableUser"
									method="post" modelAttribute="tempUser">
									<input type="hidden" value="${tempUser.id}" name="id" />
									<td><input type="submit" name="submit" value="Enable"
										class="enableButton" /></td>
								</form:form>
							<tr />
						</c:forEach>
					</table>
				</div>
			</div>
			<div class="forDown"></div>
			<div class="eraserFloat"></div>
		</div>
		<div class="bottomPanel">
			<div class="BottomLeftColumn">
				<a href="${pageContext.request.contextPath}/">Home Page</a>
			</div>
			<div class="BottomRightColumn">
				<div class="BottomRightColumn" onclick='adminContact()'>${helpLabel}</div>
			</div>
			<div class="eraserFloat"></div>
		</div>
	</div>

</body>
</html>