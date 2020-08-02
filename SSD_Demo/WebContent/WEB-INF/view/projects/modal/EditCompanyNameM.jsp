<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- security tag libraries: -->
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<br> <br>
	<p>Edit Company Name:
	<p>
		<br>
	<table>
		<form:form
			action="${pageContext.request.contextPath}/manage/editCompanies"
			method="post" modelAttribute="newCompany"
			onsubmit="return checkFields(this);">
			<tr class="universalTable">
				<th>Company:</th>
				<td><br></td>
				<th><label for="companyName">New name:</label></th>
			<tr>
				<td><form:select path="companyName" size="5"
						items="${companiesListStringName}" id="listedName"
						class="universalSelectSmall" /></td>
				<td><br></td>
				<td><input name="newName" id="newName" /></td>
			</tr>
			<tr>
				<td><br> <input type="submit" name="submit" value="Save"
					class="universalBtnMedium" /></td>
			</tr>
		</form:form>
	</table>
	<br> <br>
</div>