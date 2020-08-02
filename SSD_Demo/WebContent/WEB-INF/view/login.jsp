<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>

<head>

<!--  <meta charset="utf-8"> -->

<title>SSD Logon Page</title>
<meta name="description" content="Support Service Desk">
<meta name="author" content="Lukasz Janus">
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-2">

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

<style>
.failed {
	color: red;
}
</style>

<link
	href="${pageContext.request.contextPath}/resources/styles/logon-schema.css"
	rel="stylesheet" />
<script src="${pageContext.request.contextPath}/resources/js/cookie.js"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

</head>

<!--<body onload='cookieCheck()'> -->
<body>


	<div class="container">
		<div class="TopPanel">
			<div class="TopLeftColumn">
				<img src="resources/styles/img/logoTopPanel.png" />
			</div>
			<div class="TopCenterColumn">${nameApp}</div>
			<div class="TopRightColumn">Demo Login & Password: 'admin'</div>
			<div class="eraserFloat"></div>
		</div>

		<div class="content">
			
			<div class="logonBackgroundImage">
				<img src="resources/styles/img/logon_background.jpg" />

				<div class="LoginTablePosition">
					<div class="LoginTableStyle">

						<form:form
							action="${pageContext.request.contextPath}/authenticateTheUser"
							method="POST" onsubmit="return checkForm(this);">

							<div class="userNameLblPosition">
								<label for="loginField">User name:</label>
							</div>
							<div>
								<input type="text" name="userName" id="loginField"
									placeholder="type your login" class="loginBoxPosition"/>
							</div>
							<div class="passwordLblPosition">
								<label for="passwordField">Password:</label>
							</div>
							<div>
								<input type="password" name="password" id="passwordField"
									class="passwordBoxPosition" placeholder="type your password" />
							</div>
							<div>
								<div class="LoginBtnPosition">
									<input type="submit" value="Login" class="button" id="button"
										name="submit" />
								</div>
							</div>
							<!-- Check for login error -->
							<div class="errorLabelPosition" id="errorLabelPosition">
								<c:if test="${param.error != null}">
									<i>Sorry! You entered invalid userName/password.</i>
									<div id="errorLogin">
									</div>
								</c:if>
							</div>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> <!-- protection before csrf attacks -->
						</form:form>

					</div>
				</div>

			</div>

			<div class="eraserFloat"></div>
		</div>

		<div class="bottomPanel">
			<!-- 			<div class="BottomTablePosition"></div> -->
			<div class="BottomLeftColumn">Login to access</div>
			<div class="BottomRightColumn" onclick='adminContact()'>Contact</div>
			<div class="eraserFloat"></div>
		</div>

	</div>





</body>

</html>












