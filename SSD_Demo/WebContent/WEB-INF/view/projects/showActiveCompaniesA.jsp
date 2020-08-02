<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<title>Active Companies</title>

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

						if (readCookie('company') != null) {

							/* disable/enable company */

							var valueCookie = readCookie('company');

							if (valueCookie == "disabled") {
								alert("Company has been succesfully disabled.");
							}
							if (valueCookie == "enabled") {
								alert("Company has been succesfully enabled.");
							}

							/* add new company */

							if (valueCookie == "added") {
								alert("New company has been added succesfully.");
							}

							/* edit companyName */

							if (valueCookie == "changed") {
								alert("Company Name has been succesfully changed.");
							}

							/* add and edit error */

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

		var companyName = document.getElementById("newName");
		var listedName = document.getElementById("listedName");

		if (listedName.value == "") {
			alert("You have to choose Company to edit name!");
			return false;
		}

		if (companyName.value == "") {
			alert("Field 'New Name' cannot be blank!");
			return false;
		}
	}

	/* functions for check field in 'add form' */

	function checkFieldsAdd(form) {

		var addCompany = document.getElementById("addCompany");

		if (addCompany.value == "") {

			alert("Field 'Company Name' cannot be blank!");
			return false;
		}
	}

	/* disable company */

	function companyDisable(form) {

		var flag = confirm("Are you sure to remove this company from active projects?");
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
			<br>
			<div class="logonBackgroundImage">
				<div>
					<table>
						<tr>
							<td><div class="universalBtnBigWide">
									<a href="#openModalAdd"
										style="text-decoration: none; color: black;">Add new
										Company</a>
								</div>
								<div id="openModalAdd" class="modalDialog">
									<div>
										<a href="#close" title="Close" class="close">X</a>
										<jsp:include page="modal/AddNewCompanyA.jsp" />
									</div>
								</div></td>
							<td><div class="universalBtnBigWide">
									<a href="#openModal"
										style="text-decoration: none; color: black;">Edit Company
										Name</a>
								</div>
								<div id="openModal" class="modalDialog">
									<div>
										<a href="#close" title="Close" class="close">X</a>
										<jsp:include page="modal/EditCompanyNameA.jsp" />
									</div>
								</div></td>
						</tr>
					</table>
					<br>
					<p>Actual list of active companies:</p>
					<table class="universalTable" border=1px>
						<tr>
							<th>Company Name:</th>
							<th>Action:</th>
						</tr>
						<c:forEach var="tempCompany" items="${activeCompaniesList}">
							<tr>
								<td>${tempCompany.companyName}</td>
								<form:form
									action="${pageContext.request.contextPath}/adminTools/editCompanies"
									method="post" modelAttribute="newCompany"
									onSubmit="return companyDisable(this);">
									<input type="hidden" value="${tempCompany.id}" name="id" />
									<td><input type="submit" name="submit" value="Disable"
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
	</div>
</body>

</html>