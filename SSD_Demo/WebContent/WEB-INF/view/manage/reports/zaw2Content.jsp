<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<br>
Quantity by selected Company and agent:
<br>
<br>
<form method="get" onsubmit="return checkFields2(this);"
	action="${pageContext.request.contextPath}/manage/excelGenerateFullTab2">
	<div id="leftForms2">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td><fieldset>
						<legend>Select range of months:</legend>
						<select name="period2" id="period2" class="universalSelect">
							<option value="0">select</option>
							<c:forEach var="tempPeriod" items="${period}">
								<option value="${tempPeriod.key}">${tempPeriod.value}</option>
							</c:forEach>
						</select>
						<div id="tab2Validate" class="communicateLabel"></div>
					</fieldset></td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>Select company:</legend>
						<select class="universalSelect" id="company2l" name="company2l"
							size=5>
							<c:forEach var="tempCompany" items="${uploadedCompanies}">
								<option value="${tempCompany}">${tempCompany}</option>
							</c:forEach>
						</select>
						<div id="tab2ValidateCompanyLeft" class="communicateLabel"></div>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="rightForms2">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td>
					<fieldset>
						<legend>Compare options:</legend>
						<input type="checkbox" name="showCompare2" id="showCompare2">
						show form to compare quantity
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div class="eraserFloat"></div>
	<div id="leftForms2">
		<br>
		<table class="universalTable">
			<tr>
				<td><span id="resultSelect2l"></span></td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>User's 1 ticket quantity:</legend>
						<span id="resultCount2l"></span>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>Quantity ordered by periods:</legend>
						<span id="resultFullTab2l"></span>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="AllResults2r">
		<div id="rightForms2">
			<br>
			<table class="universalTable">
				<tr>
					<td><span id="resultSelect2r"></span></td>
				</tr>
				<tr>
					<td>
						<fieldset>
							<legend>User's 2 ticket quantity:</legend>
							<span id="resultCount2r"></span>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td>
						<fieldset>
							<legend>Quantity ordered by periods:</legend>
							<span id="resultFullTab2r"></span>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="eraserFloat"></div>
	<input type="hidden" value="actionTabFull2" name="actionTab" /> <input
		type="submit" name="submit" value="Export table"
		class="universalBtnWide">
</form>
<div class="eraserFloat"></div>
<br>
<table class="universalTableWide">
	<tr>
		<td>
			<div id=lineChart_div2
				style="border: 1px solid #ccc; width: 1000; height: 400;"></div>
		</td>
	</tr>
</table>
<br>