<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>
	<br> <br> Update Description: <br> <br>
	<form:form
		action="${pageContext.request.contextPath}/manage/editFunctionDescription"
		method="post" modelAttribute="newFunction"
		onsubmit="return checkFieldsNewDescription(this);">
		<table class="universalTable">
			<tr class="universalTable">
				<td>Function:</td>
				<td><form:select path="functionName" size="5">
						<form:options items="${functionsListStringName}" id="functionName" />
					</form:select></td>
			</tr>
			<tr>
				<td>New Description:</td>
				<td><textarea name="newDescription" id="newDescription"
						rows="5" cols="25"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td><br> <input type="submit" name="submit" value="Save"
					class="universalBtnMedium" /></td>
			</tr>
		</table>
	</form:form>
	<br>
</div>