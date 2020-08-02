<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Error Captured!</title>

<meta name="description"
	content="Support Service Desk - handler exception site.">

<meta name="author" content="Lukasz Janus">

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>

<style type="text/css">
body {
	font-weight: bold;
	font: 20px Arial, sans-serif;
}
</style>

<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>

</head>
<body>

	<div class="container">
		<div class="TopPanel">
			<div class="TopLeftColumn">
				<a href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/resources/styles/img/logoTopPanel.png" />
				</a>
			</div>
			<div class="TopCenterColumn">${nameApp}</div>
			<div class="TopRightColumn"></div>
			<div class="eraserFloat"></div>
		</div>
		<div class="MainMenu"></div>
		<br> <br>
		<div class="content">
			<div class="forDown"></div>
			<div class="forDown"></div>
			<div class="logonBackgroundImage">
				<br> <br>
				<fieldset style="margin: 5%;">
					<legend>Exception has been handled:</legend>
					<br> <br>
					<div style="margin: 5%;">
						<h4>${message}</h4>
					</div>
					<br> <br>
					<form:form action="${pageContext.request.contextPath}/"
						method="GET">
						<input type="submit" value="Back to home page"
							class="universalBtnBigWide" />
					</form:form>
				</fieldset>
			</div>
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