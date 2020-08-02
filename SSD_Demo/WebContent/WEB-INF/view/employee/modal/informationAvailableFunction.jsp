<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="showAvailableFunctionInclude">
	<br> Active functions: <br>
	<table border=1px>
		<tr>
			<td>Company:</td>
			<td>Function:</td>
		</tr>
		<c:forEach var="tempFunction" items="${listFunctions}">
			<tr>
				<td>${tempFunction.companyName}</td>
				<td>${tempFunction.functionName}</td>
			</tr>
		</c:forEach>
	</table>
</div>
