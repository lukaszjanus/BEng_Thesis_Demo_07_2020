<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Modify option name.</title>

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
	width: 30%;
	font-size: 11px;
}

.saveTD {
	width: 50px;
}

.saveExtiTD {
	width: 110px;
}
</style>

<script>

function checkField(id) {

	var newName = document.getElementById(id);

	if (newName.value == "") {

		alert("Field cannot be blank!");
		return false;
	}
}

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

	window
			.addEventListener(
					'load',
					function() {

						if (readCookie('updatedOptionCookie') != null) {

							var valueCookie = readCookie('updatedOptionCookie');
							//	alert("Cookie value: "+valueCookie);
							if (valueCookie == "updated") {
								alert("Option Name has been updated succesfully.");
							}
							if (valueCookie == "duplicated") {
								alert("Option with this name already exists.\nYou must choose different name.");
							}

							/* delete cookie after load site - expires to '0' */

							var date = new Date();
							date.setTime(date.getTime() + (0));

							var expires = "";
							expires = "; expires=" + date.toUTCString();

							document.cookie = "updatedOptionCookie=;" + expires;
						}
					})
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
				<br> <br> List of actual options: <br> <br>
				<table border="1px">
					<tr>
						<th>Role:</th>
						<th>Submenu:</th>
						<th>Actual name:</th>
						<th>New name:</th>
						<th colspan="2">Action:</th>
					</tr>
					<c:forEach var="tempOpt" items="${getAllMenuItems}">
						<form:form
							action="${pageContext.request.contextPath}/adminTools/modifyMenuOption"
							modelAttribute="newOption" method="POST"
							onsubmit="return checkField(${tempOpt.id});">
							<input type="hidden" value="${tempOpt.id}" name="id" />
							<input type="hidden" value="${tempOpt.roleName}" name="roleName" />
							<input type="hidden" value="${tempOpt.subMenu}" name="subMenu" />
							<input type="hidden" value="${tempOpt.menuOptionName}"
								name="menuOptionName" />
							<input type="hidden" value="${tempOpt.path}" name="path" />
							<tr>
								<td>${tempOpt.roleName}</td>
								<td>${tempOpt.subMenu}</td>
								<td>${tempOpt.menuOptionName}</td>
								<td><input type="text" name="newName" id="${tempOpt.id}" /></td>
								<td class="saveTD"><input type="submit" name="submit"
									value="Save" class="saveBtn" /></td>
								<td class="saveExtiTD"><input type="submit" name="submit"
									value="Save and Exit" class="saveExitBtn" /></td>
							</tr>
						</form:form>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="forDown"></div>
	</div>
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