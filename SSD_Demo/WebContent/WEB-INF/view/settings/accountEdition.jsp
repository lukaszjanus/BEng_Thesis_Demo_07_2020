<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Change Password</title>

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

<script type="text/javascript">
	/**
	 * Function check passwords on form - field p1 and field p2 must be the same.
	 */
	function confirmPasswords() {

		var pass1 = document.forms["changePass"]["p1"].value;
		var pass2 = document.forms["changePass"]["p2"].value;

		if (pass1 == "" || pass2 == "") {
			alert("Fill all fileds.");
			return false;
		}

		if (document.getElementById("p1").value != document
				.getElementById("p2").value) {
			alert("Password not the same!");
			return false;
		}

		var s1 = pass1.length;
		if (s1 < 4) {
			alert("Password must have at least 4 characters.")
			return false;
		}

		alert("Password has been changed.");
	}
</script>

<style type="text/css">
.save {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	width: 75px;
	background: lightgrey;
}

.saveExit {
	border-style: solid;
	border-color: black;
	border-width: 2px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-weight: bold;
	background: lightgrey;
	width: 152px;
}

.save:hover, .saveExit {
	background: gold;
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
		<br> <br>
		<div class="forDown"></div>
		<div class="content">
			<div class="logonBackgroundImage">
				<p>Personal Employee Settings.</p>
				<form:form
					action="${pageContext.request.contextPath}/settings/accountEdition"
					method="POST" onsubmit="return confirmPasswords();"
					name="changePass">
					<table>
						<tr>
							<th>New password:</th>
							<td><input type="password" name="p1" id="p1" /></td>
						</tr>
						<tr>
							<th>Confirm Password:</th>
							<td><input type="password" name="p2" id="p2" /></td>
						</tr>
						<tr>
						</tr>
						<tr>
							<td><input type="submit" name="submit" value="Save"
								class="save" /></td>
							<!-- onClick="clickA()"  - for tests -->
							<td><input type="submit" name="submit" value="Save and Exit"
								class="saveExit" /></td>
						</tr>
					</table>
				</form:form>
				<script>
					function clickA() {
						var onediv = document.createElement('div');
						onediv.id = 'oneblock';
						onediv.style = "width: 100%;height: 44px;top: 50px;background-color: red;border-top-style: solid;border-top-width: 3px;border-bottom-style: solid;border-bottom-width: 3px;vertical-align: middle;display: block;z-index: 111;";

						document.getElementsByTagName('body')[0]
								.appendChild(onediv);
					}
				</script>
			</div>
		</div>
		<div class="eraserFloat"></div>
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