<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<h3>Add user</h3>
<br>
<form:form action="${pageContext.request.contextPath}/manage/AddUser"
	modelAttribute="tempUser" method="POST"
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
			<td><form:input path="lastName" class="inputForm" id="lastName" /></td>
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
				class="saveBtn" /></td>
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