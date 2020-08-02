<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>
	<br> <br>Add new function:<br> <br>
	<form:form
		action="${pageContext.request.contextPath}/adminTools/FunctionList"
		modelAttribute="newFunction" method="POST"
		onsubmit="return checkFields(this);">
		<table class="universalTable">
			<tr>
				<td>New Function Name:</td>
				<td><form:input path="functionName" id="newName" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><form:input path="description" id="description" /></td>
			</tr>
			<tr>
				<td></td>
				<td><br> <input type="submit" name="submit"
					class="universalBtnWide" value="Add new Function"></td>
			</tr>
		</table>
		<br>
	</form:form>
	<br>
</div>