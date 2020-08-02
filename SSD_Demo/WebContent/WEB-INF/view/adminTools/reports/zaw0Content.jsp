<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
Standard report.
<br>
<br>
<div id="leftForms0">
	<table class="universalTable">
		<tr>
			<td>
				<fieldset style="height: 42px;">
					<legend>Select started month:</legend>
					<select name="period0" id="period0" class="universalSelectSmall">
						<option value="0">select</option>
						<c:forEach var="tempPeriod" items="${periodStandard}">
							<option value="${tempPeriod.key}">${tempPeriod.value}</option>
						</c:forEach>
					</select>
					<div id="tab0Validate" class="communicateLabel"></div>
				</fieldset>
			</td>
			<td rowspan=2><fieldset>
					<legend>Select company:</legend>
					<select multiple="multiple" class="universalSelect" id="company0"
						name="company0" size=5>

						<option value="All">All</option>

						<c:forEach var="tempCompany" items="${uploadedCompanies}">

							<option value="${tempCompany}">${tempCompany}</option>

						</c:forEach>
					</select>
					<div id="tab0ValidateCompany" class="communicateLabel"></div>
				</fieldset></td>
		</tr>
		<tr>
			<td>
				<fieldset style="height: 34px;">
					<legend>Quantity of all tickets:</legend>
					<div id="resultCount0Div">
						<div style="margin-top: 8px;">
							<span id="resultCount0"></span>
						</div>
					</div>
				</fieldset>
			</td>
		</tr>
	</table>
</div>
<div id="rightForms0"></div>
<br>
<div class="eraserFloat"></div>
<div>
	<table class="universalTableWide">
		<tr>
			<td>
				<fieldset>
					<legend>Result:</legend>
					<span id="resultTableCount0"></span>

					<form method="get" onsubmit="return checkFields0(this);"
						action="${pageContext.request.contextPath}/adminTools/excelGenerateTab0">
						<br> <input type="hidden" value="actionTab0" name="actionTab" />
						<input type="submit" name="submit" value="Export count-table"
							class="universalBtnWide">
					</form>
				</fieldset>
			</td>
		</tr>
	</table>
</div>