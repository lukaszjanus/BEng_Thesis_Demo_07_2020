<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Add Menu Options</title>

<meta charset="utf-8">

<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<style type="text/css">
.wybierz {
	float: left;
	font-size: 11px;
	text-align: center;
	font-family: arial black;
	height: 3em;
	line-height: 3em;
	border-left: 1px solid;
	border-right: 1px solid;
	border-bottom: 1px solid;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	width: 15%;
}

.wybierz:hover {
	transition-duration: 0.9s;
}

#onSiteMenu1 {
	background-color: gold;
}

#onSiteMenu2 {
	background-color: silver;
}

#onSiteMenu3 {
	background-color: silver;
}

#zaw1 {
	display: block;
}

#zaw2, #zaw3 {
	display: none;
}

.instruct {
	font-size: 11px;
}

table {
	
}

td, th {
	width: 20%;
	font-size: 11px;
	text-align: left;
}

/* 'select' size */
.selectV {
	width: 152px;
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

.del {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 75px;
}

.save:hover, .del:hover {
	background: gold;
}
</style>

<script>
	/* functions decide, which tab should be now display. */
	function jsZaw1(form) {

		document.getElementById("zaw1").style.display = "block";
		document.getElementById("onSiteMenu1").style.background = "gold";

		document.getElementById("zaw2").style.display = "none";
		document.getElementById("onSiteMenu2").style.background = "silver";

		document.getElementById("zaw3").style.display = "none";
		document.getElementById("onSiteMenu3").style.background = "silver";
	}

	function jsZaw2(form) {

		document.getElementById("zaw1").style.display = "none";
		document.getElementById("onSiteMenu1").style.background = "silver";

		document.getElementById("zaw2").style.display = "block";
		document.getElementById("onSiteMenu2").style.background = "gold";

		document.getElementById("zaw3").style.display = "none";
		document.getElementById("onSiteMenu3").style.background = "silver";
	}

	function jsZaw3(form) {

		document.getElementById("zaw1").style.display = "none";
		document.getElementById("onSiteMenu1").style.background = "silver";

		document.getElementById("zaw2").style.display = "none";
		document.getElementById("onSiteMenu2").style.background = "silver";

		document.getElementById("zaw3").style.display = "block";
		document.getElementById("onSiteMenu3").style.background = "gold";
	}

	/* function check BookMark 'Add subfolder' */
	function checkFields1(form) {

		var nameOptionForm1 = document.getElementById("nameOptionForm1");
		var topBarForm1 = document.getElementById("topBarForm1");
		var subMenuSelectForm1 = document.getElementById("subMenuSelectForm1");

		if (nameOptionForm1.value == "") {

			alert("Field 'Object name' cannot be blank!");
			return false;
		}

		if (topBarForm1.value == "NONE") {

			alert("You must select Top Bar (role)! \n 'Select' is not a role!");
			return false;
		}

		if (subMenuSelectForm1.value == "NONE") {

			alert("You must select submenu. \n 'Select' is not a submenu!");
			return false;
		}
	}

	/* function check BookMark 'Add subfolder' */
	function checkFields2(form) {

		var nameSubfolder = document.getElementById("subfolderName2");

		var subSelect = document.getElementById("subSelect2");

		if (nameSubfolder.value == "") {

			alert("Field cannot be blank!");
			return false;
		}

		if (subSelect.value == "NONE") {

			alert("You must select Role. \n 'Select' is not a role!");
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

	//deleteOption

	function deleteOption(form) {

		var flag = confirm("Are you sure to delete this position?");
		if (flag == false) {
			return false;
		} else {
			return true;
		}
	}

	window
			.addEventListener(
					'load',
					function() {

						if (readCookie('subFolderBookMark') != null) {
							jsZaw2(this);

							var valueCookie = readCookie('subFolderBookMark');
							//	alert("Cookie value: "+valueCookie);
							if (valueCookie != "submenuAdded") {
								alert("Submenu '"
										+ valueCookie
										+ "' already exist. You must choose other name.");
							} else {
								alert("New submenu has been added Successfuly!");
							}

							/* delete cookie after load site - expires to '0' */

							var date = new Date();
							date.setTime(date.getTime() + (0));

							var expires = "";
							expires = "; expires=" + date.toUTCString();

							document.cookie = "subFolderBookMark=;" + expires;

						}

						if (readCookie('newOptionCookie') != null) {
							jsZaw1(this);

							var valueCookieOpt = readCookie('newOptionCookie');
							var valueCookieSub = readCookie('newOptionCookieSub');
							//alert("valueCookieOpt value: "+valueCookieOpt);
							//alert("valueCookieSub value: "+valueCookieSub);
							if (valueCookieOpt != "newOptionAdded") {
								alert("Option '"
										+ valueCookieOpt
										+ "' in submenu '"
										+ valueCookieSub
										+ "' already exist.\nYou must choose other name.");
							} else {
								alert("New Option has been added Successfuly!");
							}

							/* delete cookie after load site - expires to '0' */
							var date = new Date();
							date.setTime(date.getTime() + (0));

							var expires = "";
							expires = "; expires=" + date.toUTCString();

							document.cookie = "newOptionCookie=;" + expires;
							document.cookie = "newOptionCookieSub=;" + expires;
						}

						if (readCookie('delOptionCookie') != null) {
							jsZaw3(this);

							var valueCookie = readCookie('delOptionCookie');

							if (valueCookie != "check") {
								alert("Item " + valueCookie + " succesfully!");
								/* delete cookie after load site - expires to '0' */
								var date = new Date();
								date.setTime(date.getTime() + (0));

								var expires = "";
								expires = "; expires=" + date.toUTCString();

								document.cookie = "delOptionCookie=;" + expires;

							} else {
								alert("You must first delete all options from this subfolder.");
								/* delete cookie after load site - expires to '0' */
								var date = new Date();
								date.setTime(date.getTime() + (0));

								var expires = "";
								expires = "; expires=" + date.toUTCString();

								document.cookie = "delOptionCookie=;" + expires;

							}

						}
					})
</script>

<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>

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
		<div class="content">
			<div class="formPositions">
				<div class="upperForDown"></div>
				<div class="wybierz" id="onSiteMenu1" onclick="jsZaw1(this)">Add
					option</div>
				<div class="wybierz" id="onSiteMenu2" onclick="jsZaw2(this)">Add
					subfolder</div>
				<div class="wybierz" id="onSiteMenu3" onclick="jsZaw3(this)">Delete</div>
				<div class="forDown"></div>
				<div class="logonBackgroundImage">
					<div id="zaw1" class="positionOnSiteDiv">
						<br> Add new option to menu:<br> <br>
						<table>
							<tr>
								<th>Top Bar (role):</th>
								<th>Submenu:</th>
								<th>Object name:</th>
								<th>Add new Option:</th>
								<th>Save and return:</th>
							</tr>
							<form:form
								action="${pageContext.request.contextPath}/adminTools/extendMenu"
								modelAttribute="newOption" method="POST"
								onsubmit="return checkFields1(this);">
								<tr>
									<td><form:select path="roleName" id="topBarForm1"
											class="selectV">
											<form:option value="NONE" label="Select" />
											<form:options items="${roleName}" />
										</form:select></td>
									<td><form:select path="subMenu" class="selectV"
											id="subMenuSelectForm1">
											<form:option value="NONE" label="Select" />
											<form:options items="${subMenu}" />
										</form:select></td>
									<td><form:input path="menuOptionName" id="nameOptionForm1"></form:input></td>
									<td><input type="submit" name="submit" value="Save"
										class="save" id="submitSize" /></td>
									<td><input type="submit" name="submit"
										value="Save and Exit" class="save" id="submitSize" /></td>
							</form:form>
						</table>
						<br>
						<hr>
						<br> Actual list of all subfolders in menu:<br> <br>
						<table border=1px>
							<tr>
								<th>Top Bar name:</th>
								<th>Submenu name:</th>
							</tr>
							<c:forEach var="tempSub" items="${submenuName}">
								<tr>
									<td>${tempSub.subMenu}</td>
									<td>${tempSub.menuOptionName}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div id="zaw2" class="positionOnSiteDiv">
						<br> Add new subfolder to menu: <br> <br>
						<form:form
							action="${pageContext.request.contextPath}/adminTools/extendSubfolder"
							modelAttribute="newOption" method="POST"
							onsubmit="return checkFields2(this);">
							<table>
								<tr>
									<th>Choose Role:</th>
									<td><form:select path="roleName" id="subSelect2"
											class="selectV">
											<form:option value="NONE" label="Select" />
											<form:options items="${roleName}" />
										</form:select></td>
								</tr>
								<tr>
									<th>Subfolder's name:</th>
									<td><form:input path="menuOptionName" id="subfolderName2"></form:input></td>
								</tr>
								<tr>
									<th>Confirm:</th>

									<td><input type="submit" name="submit" value="Save"
										class="save" /></td>
								</tr>
								<tr>
									<th>Confirm and back to home:</th>
									<td><input type="submit" name="submit"
										value="Save and Exit" class="save" /></td>
								</tr>
							</table>
						</form:form>
					</div>
					<div id="zaw3" class="positionOnSiteDiv">
						<br>Delete wrong option - use only after mistake:<br> <br>
						<table border=1px>
							<tr>
								<th>Role:</th>
								<th>Item Subfolder:</th>
								<th>Item name:</th>
								<th>Item Type:</th>
								<th>Action:</th>
							</tr>
							<c:forEach var="tempOpt" items="${getAllMenuItems}">
								<form:form
									action="${pageContext.request.contextPath}/adminTools/deleteOption"
									modelAttribute="newOption" method="POST">
									<input type="hidden" value="${tempOpt.id}" name="id" />
									<tr>
										<td>${tempOpt.roleName}</td>
										<td>${tempOpt.subMenu}</td>
										<td>${tempOpt.menuOptionName}</td>
										<td>Option</td>
										<td><input type="submit" name="submit" value="Delete"
											class="del" id="submitSize" /></td>
									</tr>
								</form:form>
							</c:forEach>
							<c:forEach var="tempSub" items="${submenuName}">
								<form:form
									action="${pageContext.request.contextPath}/adminTools/deleteOption"
									modelAttribute="newOption" method="POST"
									onSubmit="return deleteOption(this);">
									<input type="hidden" value="${tempSub.id}" name="id" />
									<tr>
										<td>${tempSub.roleName}</td>
										<td>${tempSub.subMenu}</td>
										<td>${tempSub.menuOptionName}</td>
										<td>submenu</td>
										<td><input type="submit" name="submit" value="Delete"
											class="del" id="submitSize" /></td>
									</tr>
								</form:form>
							</c:forEach>
						</table>
						<div class="instruct">${knowledgeTest}</div>
					</div>
					<div class="forDown"></div>
				</div>
			</div>
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