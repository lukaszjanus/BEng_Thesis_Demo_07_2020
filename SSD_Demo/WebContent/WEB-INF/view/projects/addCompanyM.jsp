<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- security tag libraries: -->
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Add Company</title>

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

<script>
	window
			.addEventListener(
					'load',
					function() {

						if (readCookie('company') != null) {

							var valueCookie = readCookie('company');

							if (valueCookie == "added") {
								alert("New company has been added succesfully.");
							}
							if (valueCookie == "duplicated") {
								alert("Company already exists!\nYou must choose different name.");
							}

							/* delete cookie after load site - expires to '0' */
							var date = new Date();
							date.setTime(date.getTime() + (0));

							var expires = "";
							expires = "; expires=" + date.toUTCString();
							document.cookie = "company=;" + expires;
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

	/* functions to check if fields are empty */

	function checkFields(form) {

		var companyName = document.getElementById("companyName");

		if (companyName.value == "") {
			alert("Field 'Company Name' cannot be blank!");
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
		<jsp:include page="../menu.jsp" />
		<br> <br>
		<div class="content">
			<div class="forDown"></div>
			<div class="logonBackgroundImage">
				<div>
					<p>Add new Company (manager) - not used, moved as 'include' to
						'modal'.</p>
					<br> <br>
					<form:form
						action="${pageContext.request.contextPath}/manage/AddCompany"
						modelAttribute="newCompany" method="POST"
						onsubmit="return checkFields(this);">
						<table border="1px">
							<tr>
								<td>Company Name</td>
								<td>Action</td>
							</tr>
							<tr>
								<td><form:input path="companyName" id="newName" /></td>
								<td><input type="submit" name="submit" class="save"
									class="universalBtnWide" value="Add new Company"></td>
							</tr>
						</table>
					</form:form>
				</div>
				<div class="forDown"></div>
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
		</div>
	</div>
</body>

</html>