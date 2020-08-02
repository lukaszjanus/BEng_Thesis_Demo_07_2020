
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div>
	<br>

	<p>Add new Company:</p>

	<br>
	
	<table>
		<form:form
			action="${pageContext.request.contextPath}/adminTools/AddCompany"
			modelAttribute="newCompany" method="POST"
			onsubmit="return checkFieldsAdd(this);">
			<tr>
				<td><form:input path="companyName" id="addCompany"
						name="addCompany" value="" /></td>
				<td><input type="submit" name="submit" class="universalBtnWide"
					value="Add new Company"></td>
			</tr>
		</form:form>
	</table>


	<br>
</div>