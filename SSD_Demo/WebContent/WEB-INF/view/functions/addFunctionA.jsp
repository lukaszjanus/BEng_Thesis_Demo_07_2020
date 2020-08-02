<!DOCTYPE html>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Function List</title>

<meta charset="utf-8">

<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/modalWindow.css"
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

						/* not used -> implement as ${operationResult} */

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

		var companyName = document.getElementById("newName");
		var description = document.getElementById("description");

		if (companyName.value == "") {

			alert("Field 'New Function Name' cannot be blank!");
			return false;
		}

		if (description.value == "") {

			alert("Field 'Description' cannot be blank!");
			return false;
		}
	}

	function checkFieldsNewDescription(form) {
		var newDescription = document.getElementById("newDescription");
		var functionName = document.getElementById("functionName");

		if (functionName.value == "") {
			alert("You must chose function to update description.");
			return false;
		}

		if (newDescription.value == "") {
			alert("Field with 'new Description' cannot be blank!");
			return false;
		}
	}

	/* disable function */

	function disableFunction(form) {

		var flag = confirm("Are you sure to disable this function?\nAcceptation means, that all assignation users to this function will bee deleted too.");
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
				<hr>
				<table>
					<tr>
						<td><div class="universalBtnBigWide">
								<a href="#openModalAdd"
									style="text-decoration: none; color: black;">Add new
									Function</a>
							</div>
							<div id="openModalAdd" class="modalDialog">
								<div>
									<a href="#close" title="Close" class="close">X</a>
									<jsp:include page="modal/addFunctionA.jsp" />
								</div>
							</div></td>
						<td><div class="universalBtnBigWide">
								<a href="#openModal"
									style="text-decoration: none; color: black;">Edit
									Description</a>
							</div>
							<div id="openModal" class="modalDialog">
								<div>
									<a href="#close" title="Close" class="close">X</a>
									<jsp:include page="modal/updateFunctionDescriptionA.jsp" />
								</div>
							</div></td>
					</tr>
				</table>
				<br>
				<div class="communicateLabel">${operationResult}</div>
				<br> Active Functions:<br> <br>
				<table class="universalTable" border=1px>
					<tr class="universalTable">
						<td>Function:</td>
						<td>Description:</td>
						<td>Action:</td>
					</tr>
					<c:forEach var="tempFunc" items="${activeFunctionList}">
						<form:form
							action="${pageContext.request.contextPath}/adminTools/disableFunction"
							modelAttribute="newOption" method="POST"
							onSubmit="return disableFunction(this);">
							<input type="hidden" value="${tempFunc.id}" name="id" />
							<tr>
								<td>${tempFunc.functionName}</td>

								<td>${tempFunc.description}</td>
								<td><input type="submit" name="submit" value="Disable"
									class="universalBtnMedium" /></td>
							</tr>
						</form:form>
					</c:forEach>
				</table>
				<br>
				<hr>
				<br> Disabled Functions:<br> <br>
				<table class="universalTable" border=1px>
					<tr class="universalTable">
						<td>Function:</td>
						<td>Description:</td>
						<td>Action:</td>
					</tr>
					<c:forEach var="tempFunc" items="${disabledCompaniesList}">
						<form:form
							action="${pageContext.request.contextPath}/adminTools/enableFunction"
							modelAttribute="newOption" method="POST">
							<input type="hidden" value="${tempFunc.id}" name="id" />
							<tr>
								<td>${tempFunc.functionName}</td>

								<td>${tempFunc.description}</td>
								<td><input type="submit" name="submit" value="Enable"
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