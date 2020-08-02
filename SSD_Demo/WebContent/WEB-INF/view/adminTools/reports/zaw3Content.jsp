<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<br>
Show tickets quantity by selected agent and category.
<br>
<br>
<form method="get" onsubmit="return checkFields3(this);"
	action="${pageContext.request.contextPath}/adminTools/excelGenerateFullTab3">
	<div id="leftForms3">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td><fieldset>
						<legend>Select range of months:</legend>
						<select name="period3" id="period3" class="universalSelect">
							<option value="0">select</option>
							<c:forEach var="tempPeriod" items="${period}">
								<option value="${tempPeriod.key}">${tempPeriod.value}</option>
							</c:forEach>
						</select>
						<div id="tab3Validate" class="communicateLabel"></div>
					</fieldset></td>
			</tr>
		</table>
	</div>
	<div id="rightForms3">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td>
					<fieldset>
						<legend>Compare options:</legend>
						<input type="checkbox" name="showCompare3" id="showCompare3">
						show form to compare quantity
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div class="eraserFloat"></div>
	<div id="leftForms3">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td><fieldset>
						<legend>Select company:</legend>
						<select class="universalSelect" id="company3l" name="company3l"
							size="5" onchange="countSelect3l()">
							<c:forEach var="tempCompany" items="${uploadedCompanies}">
								<option value="${tempCompany}">${tempCompany}</option>
							</c:forEach>
						</select>
						<div id="tab3ValidateCompanyLeft" class="communicateLabel"></div>
					</fieldset></td>
				<td><span id="showAgents3l"></span></td>
			</tr>
			<tr>
				<td colspan="2"><span id="showCategories3l"></span></td>
			</tr>
		</table>
	</div>
	<div id="rightForms3">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td><span id="showAgents3r"></span></td>
			</tr>
			<tr>
				<td colspan="2"><span id="showCategories3r"></span></td>
			</tr>
		</table>
	</div>
	<div class="eraserFloat"></div>
	<div id="leftForms3">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td colspan="2">
					<fieldset>
						<legend>User's ticket quantity:</legend>
						<span id="resultSumAgentCategory3l"></span>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<fieldset>
						<legend>Quantity ordered by periods:</legend>
						<span id="resultReportAgentCategory3l"></span>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="AllResults3r">
		<div id="rightForms3">
			<table class="universalTable" style="width: 100%;">
				<tr>
					<td colspan="2">
						<fieldset>
							<legend>User's comparing ticket quantity:</legend>
							<span id="resultSumAgentCategory3r"></span>
						</fieldset>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<fieldset>
							<legend>Quantity ordered by periods:</legend>
							<span id="resultReportAgentCategory3r"></span>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="eraserFloat"></div>
	<input type="hidden" value="actionTabFull3" name="actionTab" /> <input
		type="submit" name="submit" value="Export table"
		class="universalBtnWide">
</form>
<div class="eraserFloat"></div>
<br>
<table class="universalTableWide">
	<tr>
		<td>
			<div id=lineChart_div3
				style="border: 1px solid #ccc; width: 1000; height: 400;"></div>
		</td>
	</tr>
</table>
<br>