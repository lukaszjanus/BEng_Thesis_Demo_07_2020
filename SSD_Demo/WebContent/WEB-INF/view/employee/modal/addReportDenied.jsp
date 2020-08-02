<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="addReportDenied">
	<div class="communicateLabelFont12">You must have added by
		manager function "Add Report"!!</div>
	<br>
	<table class="universalTable">
		<tr>
			<td>Select Company:</td>
			<td><form:select path="listCompanies" name="listCompanies"
					class="universalSelect">
					<option value="none">select</option>
				</form:select></td>
		</tr>
		<tr>
			<td>Select period:</td>
			<td><form:select path="period" name="period" disabled="disabled"
					class="universalSelect">
					<option value="none">select</option>
				</form:select></td>
		</tr>
		<tr>
			<td>Import file:</td>
			<td>File to Upload: <input type="file" name="file"
				disabled="disabled"> <br />
			</td>
		</tr>
		<tr>
			<td>Action:</td>
			<td><input type="submit" value="Upload"
				class="universalBtnMedium" disabled="disabled"></td>
		</tr>
	</table>
</div>
