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
<title>Home</title>

<meta charset="utf-8">

<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />


<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>


<style type="text/css">
body {
	background: white;
}
</style>

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

		<jsp:include page="menu.jsp" />
		<br> <br>
		<div class="content">



			<div class="logonBackgroundImage">
				<img src="resources/styles/img/main_background.png" />

					<p style="margin: 10px;">Welcome to the SSD Demo Version Web App home page!</p>
					<br><br><br><br>
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
	</div>
</body>

</html>