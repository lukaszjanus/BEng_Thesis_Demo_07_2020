<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Customize Reports Upload</title>

<meta charset="utf-8">

<meta name="author" content="Lukasz Janus" />

<link
	href="https://fonts.googleapis.com/css?family=Lato|Josefin+Sans&subset=latin,latin-ext"
	rel="stylesheet" type="text/css">

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

		/* delete '0' values from text-inputs */
		document.getElementById("type").value = "";
		document.getElementById("closeDate").value = "";
		document.getElementById("category").value = "";
		document.getElementById("agent").value = "";
		document.getElementById("date").value = "";
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

	function checkFields(form) {

		document.getElementById("typeValidate").innerHTML = "";
		document.getElementById("closeDateValidate").innerHTML = "";
		document.getElementById("categoryValidate").innerHTML = "";
		document.getElementById("agentValidate").innerHTML = "";
		document.getElementById("registerValidate").innerHTML = "";
		document.getElementById("prefixValidate").innerHTML = "";
		document.getElementById("splitterValidate").innerHTML = "";

		var flag = true;
		/*********************************************************/
		var companyName = document.getElementById("activeCompaniesList");

		if (companyName.value == "") {
			document.getElementById("companyValidate").innerHTML = "Select Company!";
			flag = false;
		}

		/*********************************************************/
		/********Regual expresion validation - digit only*********/
		/*********************************************************/
		const reg = /\D/;
		var maxSize = 2;
		/*********************************************************/
		var type = document.getElementById("type");
		var typeL = type.value.length;

		if (type.value == "") {
			document.getElementById("typeValidate").innerHTML = "Type is empty!";
			flag = false;
		}
		if ((reg.test(type.value))) {
			document.getElementById("typeValidate").innerHTML = "Please only enter numeric characters only for \"Type\" field! (Allowed input:0-9)";
			flag = false;
		}
		if (typeL > maxSize) {
			document.getElementById("typeValidate").innerHTML = "Please up to "
					+ maxSize + " digits";
			flag = false;
		}

		/*********************************************************/
		var closeDate = document.getElementById("closeDate");
		var closeDateL = closeDate.value.length;

		if (closeDate.value == "") {
			document.getElementById("closeDateValidate").innerHTML = "Close Date is empty!";
			flag = false;
		}
		if ((reg.test(closeDate.value))) {
			document.getElementById("closeDateValidate").innerHTML = "Please only enter numeric characters only for \"Close Date\" field! (Allowed input:0-9)";
			flag = false;
		}
		if (closeDateL > maxSize) {
			document.getElementById("closeDateValidate").innerHTML = "Please up to "
					+ maxSize + " digits";
			flag = false;
		}

		/*********************************************************/
		var category = document.getElementById("category");
		var categoryL = category.value.length;

		if (category.value == "") {
			document.getElementById("categoryValidate").innerHTML = "Category is empty!";
			flag = false;
		}
		if ((reg.test(category.value))) {
			document.getElementById("categoryValidate").innerHTML = "Please only enter numeric characters only for \"Category\" field! (Allowed input:0-9)";
			flag = false;
		}
		if (categoryL > maxSize) {
			document.getElementById("categoryValidate").innerHTML = "Please up to "
					+ maxSize + " digits";
			flag = false;
		}

		/*********************************************************/
		var agent = document.getElementById("agent");
		var agentL = agent.value.length;

		if (agent.value == "") {
			document.getElementById("agentValidate").innerHTML = "Agent is empty!";
			flag = false;
		}
		if ((reg.test(agent.value))) {
			document.getElementById("agentValidate").innerHTML = "Please only enter numeric characters only for \"Agent\" field! (Allowed input:0-9)";
			flag = false;
		}
		if (agentL > maxSize) {
			document.getElementById("agentValidate").innerHTML = "Please up to "
					+ maxSize + " digits";
			flag = false;
		}

		/*********************************************************/
		var date = document.getElementById("date");
		var dateL = date.value.length;

		if (date.value == "") {
			document.getElementById("registerValidate").innerHTML = "Date is empty!";
			flag = false;
		}
		if ((reg.test(date.value))) {
			document.getElementById("registerValidate").innerHTML = "Please only enter numeric characters only for \"Date of register ticket\" field! (Allowed input:0-9)";
			flag = false;
		}
		if (dateL > maxSize) {
			document.getElementById("registerValidate").innerHTML = "Please up to "
					+ maxSize + " digits";
			flag = false;
		}

		/*********************************************************/
		var prefix = document.getElementById("prefix");
		var prefixL = prefix.value.length;

		if (prefix.value == "") {
			document.getElementById("prefixValidate").innerHTML = "Prefix is empty!\nPlease enter up to 10 characters.";
			flag = false;
		}
		if (prefixL > 10) {
			document.getElementById("prefixValidate").innerHTML = "Prefix is to long!\nPlease enter up to 10 characters";
			flag = false;
		}

		/*********************************************************/
		var splitter = document.getElementById("splitter");
		if (splitter.value == " ") {
			document.getElementById("splitterValidate").innerHTML = "Select Splitter!";
			flag = false;
		}

		if (flag == false) {
			return false;
		}
	}

	/* ajax - actual customize */
	$(document).ready(function() {
		$("#activeCompaniesList").click(function() {
			var id = $('#activeCompaniesList').val();

			$.ajax({

				type : 'GET',
				url : 'showActualCustomizing',
				data : {
					id : id
				},
				success : function(result) {
					$("#actualCustomizing").html(result);
				}
			})
		})
	})
</script>

<style type="text/css">
#rightForms {
	margin: 5px;
	width: 45%;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 260px;
}

#leftForms {
	margin: 5px;
	width: 45%;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 260px;
}

#rightForms fieldset, #leftForms fieldset {
	width: 100%;
	height: 100%;
}
</style>

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
				<br>
				<h3>Customize report uploading:</h3>
				<br>
				<form:form
					action="${pageContext.request.contextPath}/manage/UploadCustomize"
					method="post" modelAttribute="newSchema"
					onsubmit="return checkFields(this);">
					<div id="leftForms">
						<fieldset>
							<legend>Select company to edit schema:</legend>
							<form:select size="13" path="id" class="universalSelect"
								id="activeCompaniesList">
								<form:options itemValue="id" items="${activeCompaniesList}"
									itemLabel="companyName" />
							</form:select>
							<div id="companyValidate" class="communicateLabel"></div>
						</fieldset>
					</div>
					<div id="rightForms">
						<fieldset>
							<legend>Current schema values:</legend>
							<span id="actualCustomizing"></span>
						</fieldset>
					</div>
					<div class="eraserFloat"></div>
					<br>
					<fieldset>
						<legend>Associate columns with data-type of uploaded
							report:</legend>
						<br>
						<table class="universalTable">
							<tr>
								<td height=16px colspan=2>
									<div class="communicateLabel">${operationResult}</div>
								</td>
							</tr>
							<tr>
								<td>Ticket Number:</td>
								<td>Close Date:</td>
								<td>Category/Description:</td>
								<td>Agent:</td>
								<td>Date of register ticket:</td>
							</tr>
							<tr>
								<td><form:input path="type" id="type" name="type" /></td>
								<td><form:input path="closeDate" id="closeDate"
										name="closeDate" /></td>
								<td><form:input path="category" id="category"
										name="category" /></td>
								<td><form:input path="agent" id="agent" name="agent" /></td>
								<td><form:input path="date" id="date" name="date" /></td>
							</tr>
							<tr>
								<td><div id="typeValidate" class="communicateLabel"></div></td>
								<td><div id="closeDateValidate" class="communicateLabel"></div></td>
								<td><div id="categoryValidate" class="communicateLabel"></div></td>
								<td><div id="agentValidate" class="communicateLabel"></div></td>
								<td><div id="registerValidate" class="communicateLabel"></div></td>
							</tr>
						</table>
					</fieldset>
					<br>
					<fieldset>
						<legend>Other schema options:</legend>
						<table class="universalTable">
							<tr>
								<td>Prefix of incident:</td>

								<td>
									<div id="prefixValidate" class="communicateLabel"></div> <form:input
										path="prefix" id="prefix" name="prefix" value="" />
								</td>
							</tr>
							<tr>
								<td>Only closed tickets:</td>
								<td><form:select path="isClosed"
										class="universalSelectSmall">
										<form:option value="0">All tickets</form:option>
										<form:option value="1">Only closed tickets</form:option>
									</form:select>
							</tr>
							<tr>
								<td>CSV - splitter:</td>
								<td>
									<div id="splitterValidate" class="communicateLabel"></div> <form:select
										path="splitter" class="universalSelectSmall">
										<form:option value=" ">Select</form:option>
										<form:option value=";">" ; "</form:option>
										<form:option value=",">" , "</form:option>
										<form:option value=".">" . "</form:option>
									</form:select>
								</td>
							</tr>
						</table>
					</fieldset>
					<br>
					<input type="submit" name="submit" class="universalBtnWide"
						value="Save Schema">
				</form:form>
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

</body>
</html>