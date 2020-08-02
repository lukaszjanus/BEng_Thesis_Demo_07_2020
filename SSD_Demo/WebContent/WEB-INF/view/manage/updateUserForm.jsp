<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Update User</title>

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
	font-size: 11px;
	text-align: left;
}

.saveButton {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 110px;
}

.returnButton {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 170px;
}

.saveButton:hover, .returnButton:hover {
	background: gold;
}

.selectID {
	width: 145px;
	margin: 5px;
}

.fieldUniwersal {
	width: 145px;
	margin: 5px;
}

#rightForms {
	width: 45%;;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 250px;
}

#leftForms {
	width: 45%;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 250px;
}

.rolesList th {
	font-size: 14px;
	height: 30px;
}

.backToUserList {
	margin-left: 17%;
}
</style>

<script>
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

	window.addEventListener('load', function() {

		if (readCookie('cookieUser') != null) {

			var valueCookie = readCookie('cookieUser');

			if (valueCookie == "removed") {
				alert("Role has been removed Successfuly!");
			}
			if (valueCookie == "added") {
				alert("Role has been added Successfuly!");
			}
			if (valueCookie == "update") {
				alert("User has been updated Successfuly!");
			}

			/* delete cookie after load site - expires to '0' */

			var date = new Date();
			date.setTime(date.getTime() + (0));

			var expires = "";
			expires = "; expires=" + date.toUTCString();

			document.cookie = "cookieRole=;" + expires;
		}

		if (readCookie('updatePass') != null) {

			alert("Password has been changed.");
			/* delete cookie after load site - expires to '0' */

			var date = new Date();
			date.setTime(date.getTime() + (0));

			var expires = "";
			expires = "; expires=" + date.toUTCString();

			document.cookie = "updatePass=;" + expires;
		}

		if (readCookie('updateUser') != null) {

			var valueCookie = readCookie('updateUser');

			if (valueCookie == "username") {
				alert("Login already exists!\nYou must type different.");
			} else if (valueCookie == "email") {
				alert("E-mail already exists!\nYou must type different.");
			} else {
				alert("User has been updated!");
			}

			/* delete cookie after load site - expires to '0' */
			var date = new Date();
			date.setTime(date.getTime() + (0));

			var expires = "";
			expires = "; expires=" + date.toUTCString();

			document.cookie = "newUser=;" + expires;
		}

	})

	/* Function check fields of updated user: username, first name, last name, password.*/
	function confirmFields() {

		var firstN = document.forms["confirmF"]["firstName"].value;
		var lastN = document.forms["confirmF"]["lastName"].value;
		var userN = document.forms["confirmF"]["userName"].value;

		var l2 = firstN.length;
		var l3 = lastN.length;
		var l4 = userN.length;

		var minSize = 4;
		
		if (l2 < minSize) {
			alert("First Name must have at least 4 characters.");
			return false;
		}
		if (l3 < 4) {
			alert("Last Name must have at least 4 characters.");
			return false;
		}
		if (l4 < 4) {
			alert("User Name must have at least 4 characters.");
			return false;
		}
	}

	/**
	 * Function check passwords on form - field p1 and field p2 must be the same.
	 */
	function confirmPasswords() {

		var pass1 = document.forms["changePass"]["p1"].value;

		var s1 = pass1.length;
		if (s1 < 4) {
			alert("Password must have at least 4 characters.")
			return false;
		}
	}

	/* function check BookMark 'add role' */
	function checkFieldsAdd(form) {

		var chooseList = document.getElementById("selectRoleAdd");

		if (chooseList.value == "NONE") {

			alert("You must select Role. \n 'Select' is not a role!");
			return false;
		}
		return true;
	}

	/* function check BookMark 'remove role' */
	function checkFieldsDel(form) {

		var chooseList = document.getElementById("selectRoleDel");

		if (chooseList.value == "NONE") {

			alert("You must select Role. \n 'Select' is not a role!");
			return false;
		}
		return true;
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
		<div class="upperForDown"></div>
		<div class="content">
			<div class="logonBackgroundImage">
				<h3>Update user details:</h3>
				<div id="leftForms">
					<form:form
						action="${pageContext.request.contextPath}/manage/SaveUpdatedUser"
						modelAttribute="tempUser" method="POST"
						onsubmit="return confirmFields();" name="confirmF">
						<form:hidden path="id" />
						<table>
							<tr>
								<td><label for="userName">User:</label></td>
								<td><form:input path="userName"
										value="${tempUser.userName}" name="userName" id="userName"
										class="fieldUniwersal" /></td>
							</tr>
							<tr>
								<td><label for="firstName">First name:</label></td>
								<td><form:input path="firstName" name="firstName"
										id="firstName" class="fieldUniwersal" /></td>
							</tr>
							<tr>
								<td><label for="lastName">Last name:</label></td>
								<td><form:input path="lastName" name="lastName"
										id="lastName" class="fieldUniwersal" /></td>
							</tr>
							<tr>
								<td><label for="enabled">Active user:</label></td>
								<td><form:select path="enabled" class="selectID">
										<form:options items="${disableUser}" />
									</form:select></td>
							</tr>
							<tr>
								<td><label for="created">Created:</label></td>
								<td><form:hidden path="created" />
									<div class="fieldUniwersal">${tempUser.created}</div></td>
							</tr>
							<tr>
								<td><label for="email">E-mail:</label></td>
								<td><form:input path="email" class="fieldUniwersal" /></td>
							</tr>
							<tr>
								<td><label for="submit">Action:</label></td>
								<td><input type="submit" value="Update User" name="submit"
									class="saveButton" /></td>
							</tr>
						</table>
					</form:form>
				</div>
				<div id="rightForms">
					<table>
						<tr>
							<td>
								<table>
									<tr>
										<th>Reset password:</th>
									</tr>
									<form:form
										action="${pageContext.request.contextPath}/manage/changePassword"
										method="POST" onsubmit="return confirmPasswords();"
										name="changePass">
										<tr>
											<td><input type="text" class="fieldUniwersal" name="p1"
												id="p1"></td>
											<td><input type="submit" value="Reset"
												class="saveButton" name="submit" /></td>
										</tr>
									</form:form>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form
									action="${pageContext.request.contextPath}/manage/removeRoleToUser"
									method="GET" onsubmit="return checkFieldsDel(this);">
									<table>
										<tr>
											<th colspan="2"><br>Choose role to remove from
												user:</th>
										</tr>
										<tr>
											<td><form:select path="roles_active" name="t1"
													id="selectRoleDel" class="selectID">
													<form:option value="NONE" label="Select" />
													<form:options items="${roles_active}" />
												</form:select></td>
											<td><input type="submit" name="submit"
												value="Remove Role" class="saveButton" /></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td>
								<form
									action="${pageContext.request.contextPath}/manage/addRoleToUser"
									method="GET" onsubmit="return checkFieldsAdd(this);">
									<table>
										<tr>
											<th colspan="2"><br>Choose role to add to user:</th>
										</tr>
										<tr>
											<td><form:select path="roles_other" name="t1"
													id="selectRoleAdd" class="selectID">
													<form:option value="NONE" label="Select" />
													<form:options items="${roles_other}" />
												</form:select></td>
											<td><input type="submit" name="submit" value="Add role"
												class="saveButton"></td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
						<tr>
							<td><div class="rolesList">
									<table>
										<tr>
											<th><br>Actual roles of user:</th>
										</tr>

										<tr>
											<td>
												<ul>
													<c:forEach var="roleTemp" items="${roles_active}">
														<li>${roleTemp}</li>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</table>
								</div></td>
						</tr>
					</table>
				</div>
				<div class="eraserFloat"></div>
				<br>
				<div class="backToUserList">
					<form:form
						action="${pageContext.request.contextPath}/manage/ActiveUsers"
						method="GET">
						<input type="submit" value="Back to Active Users"
							class="returnButton" />
					</form:form>
				</div>
			</div>
			<br>
		</div>
	</div>
	<div class="forDown"></div>
	<div class="eraserFloat"></div>
	<div class="bottomPanel">
		<div class="BottomLeftColumn">
			<a href="${pageContext.request.contextPath}/">Home Page</a>
		</div>
		<div class="BottomRightColumn">
			<div class="BottomRightColumn" onclick='adminContact()'>${helpLabel}</div>
		</div>
		<div class="eraserFloat"></div>
	</div>
</body>
</html>