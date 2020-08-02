<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<title>Add report</title>

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
						document.getElementById("functionNotAvailableInclude").style.display = "none";
						if (readCookie('isUserHaveFunctions') != null) {
							document
									.getElementById("functionNotAvailableInclude").style.display = "block";
							document
									.getElementById("showAvailableFunctionInclude").style.display = "none";

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
		<div class="upperForDown"></div>
		<div class="content">
			<div class="logonBackgroundImage">
				<p>Employee Credentials information.</p>
				<jsp:include page="modal/informationAvailableFunction.jsp" />
				<jsp:include page="modal/informationNoFunction.jsp" />
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