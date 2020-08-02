<!DOCTYPE html>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Manage Functions</title>

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

						if (readCookie('function') != null) {

							var valueCookie = readCookie('function');

							if (valueCookie == "added") {
								alert("New function has been added succesfully.");
							}
							if (valueCookie == "duplicated") {
								alert("Function already exists!\nYou must choose different name.");
							}

							if (valueCookie == "descriptionUpdate") {
								alert("Description has been updated succesfully.");
							}

							/* delete cookie after load site - expires to '0' */
							var date = new Date();
							date.setTime(date.getTime() + (0));

							var expires = "";
							expires = "; expires=" + date.toUTCString();

							document.cookie = "function=;" + expires;
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

		var userName = document.getElementById("userName");
		var companyName = document.getElementById("companyName");
		var functionName = document.getElementById("functionName");

		if (userName.value == "") {
			alert("You must choose one or more users!");
			return false;
		}

		if (companyName.value == "") {
			alert("Choose Company!");
			return false;
		}

		if (functionName.value == "") {
			alert("Choose Function!");
			return false;
		}
	}

	/* remove function */

	function removeFunction(form) {

		var flag = confirm("Are you sure to remove this function from user?");
		if (flag == false) {
			return false;
		} else {
			return true;
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
				<br> <br>Assign to Function:<br> <br>
				<form:form
					action="${pageContext.request.contextPath}/manage/AssignFunctionM"
					modelAttribute="User_Function_String_Contener" method="POST"
					onsubmit="return checkFields(this);">
					<table class="universalTable">
						<tr class="universalTable">
							<td>User:</td>
							<td>Company Name:</td>
							<td>Function:</td>
						</tr>
						<tr>
							<td><form:select size="5" multiple="true" path="userName"
									id="userName" items="${activeUsersListString}"
									class="universalSelect" /></td>
							<td><form:select size="5" path="companyName"
									id="companyName" class="universalSelect">
									<form:options items="${companiesListStringName}" />
								</form:select></td>
							<td><form:select size="5" path="functionName"
									id="functionName" items="${functionsListStringName}"
									class="universalSelect" /></td>
							<td><input type=submit value="Assign function"
								class="universalBtnWide" /></td>
						</tr>
					</table>
				</form:form>
				<br> <br>
				<table>
					<tr>
						<td>Actual Functions:</td>
					</tr>
					<tr>
						<td height=16px>
							<div class="communicateLabel">${operationResult}</div>
						</td>
					</tr>
				</table>
				<br>
				<table class="universalTable" border=1px>

					<tr class="universalTable">
						<td>User:</td>
						<td>Company:</td>
						<td>Function:</td>
						<td>Remove:</td>
					</tr>
					<c:forEach var="temp" items="${userFunctionsString}">
						<form:form
							action="${pageContext.request.contextPath}/manage/RemoveFunctionM"
							modelAttribute="User_Function_String_Contener" method="POST"
							onSubmit="return removeFunction(this);">
							<input type="hidden" value="${temp.id}" name="id" />
							<input type="hidden" value="${temp.userName}" name="userName" />
							<input type="hidden" value="${temp.companyName}"
								name="companyName" />
							<input type="hidden" value="${temp.functionName}"
								name="functionName" />
							<tr>
								<td>${temp.userName}</td>
								<td>${temp.companyName}</td>
								<td>${temp.functionName}</td>
								<td><input type=submit value="Remove"
									class="universalBtnMedium" /></td>
							</tr>
						</form:form>
					</c:forEach>

				</table>
			</div>
			<div class="forDown"></div>
			<div class="eraserFloat"></div>
		</div>
		<div class="bottomPanel">
			<!-- 			<div class="BottomTablePosition"></div> -->
			<div class="BottomLeftColumn">
				<a href="${pageContext.request.contextPath}/">Home Page</a>
			</div>
			<div class="BottomRightColumn">
				<div class="helpClick" onclick="adminContact()">${helpLabel}</div>
			</div>
			<div class="eraserFloat"></div>
		</div>
	</div>

</body>
</html>