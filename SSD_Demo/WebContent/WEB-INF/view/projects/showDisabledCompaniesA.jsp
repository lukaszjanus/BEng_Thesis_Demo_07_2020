<!DOCTYPE html>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Disabled Companies</title>

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
	window.addEventListener('load', function() {

		if (readCookie('company') != null) {

			var valueCookie = readCookie('company');

			if (valueCookie == "disabled") {
				alert("Company has been succesfully disabled.");
			}
			if (valueCookie == "enabled") {
				alert("Company has been succesfully enabled.");
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

	/* Validation to check if fields in form are not empty */

	function checkFields(form) {

		var companyName = document.getElementById("newName");

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
				<br> Disabled companies: <br> <br>
				<table class="universalTable" border=1px>
					<tr>
						<th>Company Name:</th>
						<th>Action:</th>
					</tr>

					<c:forEach var="tempCompany" items="${disabledCompaniesList}">
						<tr>
							<td>${tempCompany.companyName}</td>
							<form:form
								action="${pageContext.request.contextPath}/adminTools/editCompanies"
								method="post" modelAttribute="newCompany">
								<input type="hidden" value="${tempCompany.id}" name="id" />
								<td><input type="submit" name="submit" value="Enable"
									class="universalBtnMedium" id="Disable" /></td>
							</form:form>
						<tr />

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