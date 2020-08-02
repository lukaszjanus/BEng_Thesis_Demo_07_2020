<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	/* functions to check if fields are empty */

	function checkFields(form) {

		var companyReported = document.getElementById("companyReported");
		var period = document.getElementById("period");
		var file = document.getElementById("file");

		if (companyReported.value == "none" || companyReported.value == "") {
			document.getElementById("CompanyResult").innerHTML = "Choose Company!";
			document.getElementById("FileResult").innerHTML = "";
			document.getElementById("PeriodResult").innerHTML = "";
			return false;
		} else if (period.value == "none" || period.value == "") {

			document.getElementById("CompanyResult").innerHTML = "";
			document.getElementById("PeriodResult").innerHTML = "Choose reported month!";
			document.getElementById("FileResult").innerHTML = "";
			return false;
		} else if (file.value == "") {
			document.getElementById("CompanyResult").innerHTML = "";
			document.getElementById("PeriodResult").innerHTML = "";
			document.getElementById("FileResult").innerHTML = "You have not choose file with reported data!";
			return false;
		} else {
			document.getElementById("CompanyResult").innerHTML = "";
			document.getElementById("PeriodResult").innerHTML = "";
			document.getElementById("FileResult").innerHTML = "";
		}
	}
</script>

<div id="addReportActive">
	<form method="POST"
		action="${pageContext.request.contextPath}/employee/reportUpload?${_csrf.parameterName}=${_csrf.token}"
		enctype="multipart/form-data" onSubmit="return checkFields(this);">
		<table class="universalTable">
			<tr>
				<td>Select Company:</td>
				<td><form:select path="listCompanies" name="companyReported"
						id="companyReported" class="universalSelect">
						<option value="none">select</option>
						<c:forEach var="tempCompany" items="${listCompanies}">
							<form:option value="${tempCompany.id_company}">${tempCompany.companyName}</form:option>
						</c:forEach>
					</form:select></td>

				<td><label id="CompanyResult" class="communicateLabel"></label></td>
			</tr>
			<tr>
				<td>Select period:</td>
				<td><form:select path="period" name="period" id="period"
						class="universalSelect">
						<option value="none">select</option>
						<c:forEach var="tempPeriod" items="${period}">
							<form:option value="${tempPeriod}" />
						</c:forEach>
					</form:select></td>

				<td><label id="PeriodResult" class="communicateLabel"></label></td>
			</tr>
			<tr>
				<td>Import file:</td>
				<td><input type="file" name="file" id="file"
					class="universalSelect"></td>

				<td><label id="FileResult" class="communicateLabel"></label></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Upload"
					class="universalBtnMedium"></td>
			</tr>
			<tr>
				<td colspan="2"><div class="communicateLabel">${operationResult}</div></td>
			</tr>
		</table>
	</form>
</div>
