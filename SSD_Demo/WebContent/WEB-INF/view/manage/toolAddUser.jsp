<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Add new user</title>

<meta charset="UTF-8">
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

.inputForm {
	width: 150px;
}

.save {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 152px;
}

.save:hover {
	background: gold;
}
</style>

<script>
	window.addEventListener('load', function() {

		if (readCookie('newUser') != null) {
			var valueCookie = readCookie('newUser');
			if (valueCookie == "username") {
				alert("Login already exists!\nYou must type different.");
			} else if (valueCookie == "email") {
				alert("email already exists!\nYou must type different.");
			} else {
				alert("New user: " + valueCookie + "' has been added!");
			}

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

	/* function check BookMark 'Add subfolder' */
	function checkFields(form) {

		var firstName = document.getElementById("firstName");

		var lastName = document.getElementById("lastName");
		var email = document.getElementById("email")

		if (firstName.value == "") {

			alert("Field 'First name' cannot be blank!");
			return false;
		}
		if (lastName.value == "") {

			alert("Field 'Last name' cannot be blank!");
			return false;
		}
		if (email.value == "") {

			alert("Field 'email' cannot be blank!");
			return false;
		}
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
		<div class="forDown"></div>
		<jsp:include page="../menu.jsp" />
		<div class="forDown"></div>
		<div class="content">
			<div class="logonBackgroundImage">
				<div class="ForDown"></div>
				<h3>Add user</h3>
				<br>
				<form:form
					action="${pageContext.request.contextPath}/manage/AddUser"
					modelAttribute="newUser" method="POST"
					onsubmit="return checkFields(this);">
					<table>
						<tr>
							<td><label for="userName">Login*:</label></td>
							<td><form:input path="userName" class="inputForm" /></td>
						</tr>
						<tr>
							<td><label for="firstName">First name:</label></td>
							<td><form:input path="firstName" class="inputForm"
									id="firstName" /></td>
						</tr>
						<tr>
							<td><label for="lastName">Last name:</label></td>
							<td><form:input path="lastName" class="inputForm"
									id="lastName" /></td>
						</tr>

						<tr>
							<td><label for="password">Password*:</label></td>
							<td><form:input path="password" type="password"
									class="inputForm" value="SSD1234" /></td>
						</tr>

						<tr>
							<td><label for="email">E-mail:</label></td>
							<td><form:input path="email" class="inputForm" id="email"
									type="email" /></td>
						</tr>
						<tr>
							<td>Action:</td>
							<td><input type="submit" name="submit" value="Save"
								class="save" /></td>
							<td><input type="submit" name="submit" value="Save and Exit"
								class="save" /></td>
						</tr>
					</table>
				</form:form>
				<br />
				<div style="font-size: 11px;">
					*Default values:
					<ul>
						<li>default login : 'firstName.lastName'</li>
						<li>'start' password: 'SSD1234'</li>
						<li>role : Employee, Settings</li>
					</ul>
				</div>
				<br />
				<div class="forDown"></div>
				<div class="eraserFloat"></div>
			</div>
		</div>
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