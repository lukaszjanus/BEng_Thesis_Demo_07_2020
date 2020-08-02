<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br>
Quantity of tickets by selected Company:
<br>
<br>
<form method="get" onsubmit="return checkFields1(this);"
	action="${pageContext.request.contextPath}/manage/excelGenerateFullTab1">
	<div id="leftForms1">
		<table class="universalTable">
			<tr>
				<td>
					<fieldset>
						<legend>Select range of months:</legend>
						<select name="period1" id="period1" class="universalSelect">
							<option value="0">select</option>
							<c:forEach var="tempPeriod" items="${period}">
								<option value="${tempPeriod.key}">${tempPeriod.value}</option>
							</c:forEach>
						</select>
						<div id="tab1Validate" class="communicateLabel"></div>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="rightForms1">
		<table class="universalTable" style="width: 100%;">
			<tr>
				<td>
					<fieldset>
						<legend>Compare options:</legend>
						<input type="checkbox" name="showCompare1" id="showCompare1">
						show form to compare quantity
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div class="eraserFloat"></div>
	<div id="leftForms1">
		<table class="universalTable">
			<tr>
				<td>
					<fieldset>
						<legend>Select company:</legend>
						<select class="universalSelect" id="company1l" name="company1l"
							size=5>
							<c:forEach var="tempCompany" items="${uploadedCompanies}">
								<option value="${tempCompany}">${tempCompany}</option>
							</c:forEach>
						</select>
						<div id="tab1ValidateCompanyLeft" class="communicateLabel"></div>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="rightSelect1r">
		<div id="rightForms1">
			<table class="universalTable">
				<tr>
					<td>
						<fieldset>
							<legend>Select company to compare:</legend>
							<select class="universalSelect" id="company1r" name="company1r"
								size=5>
								<!-- 	<option value="All">All</option>  -->
								<c:forEach var="tempCompany" items="${uploadedCompanies}">
									<option value="${tempCompany}">${tempCompany}</option>
								</c:forEach>
							</select>
							<div id="tab1ValidateCompanyRight" class="communicateLabel"></div>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="eraserFloat"></div>
	<div id="leftForms1">
		<table class="universalTable">
			<tr>
				<td>
					<fieldset>
						<legend>Company's 1 tickets quantity:</legend>
						<span id="resultTableCount1l"></span> <br>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<legend>Tickets quantity of Company no 1 ordered by
							periods:</legend>
						<span id="resultReporsFullTab1l"></span><br>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	<div id="AllResults1r">
		<div id="rightForms1">
			<table class="universalTable">
				<tr>
					<td><fieldset>
							<legend>Company's 2 tickets quantity:</legend>
							<span id="resultTableCount1r"></span> <br>
						</fieldset></td>
				</tr>
				<tr>
					<td><fieldset>
							<legend>Tickets quantity of Company no 2 ordered by
								periods:</legend>
							<span id="resultReporsFullTab1r"></span><br>
						</fieldset></td>
				</tr>
			</table>
		</div>
	</div>
	<br>
	<div class="eraserFloat"></div>
	<input type="hidden" value="actionTabFull1" name="actionTab" /> <input
		type="submit" name="submit" value="Export table"
		class="universalBtnWide">
</form>
<div class="eraserFloat"></div>
<table class="universalTableWide">
	<tr>
		<td>
			<div id=lineChart_div1
				style="border: 1px solid #ccc; width: 1000; height: 400;"></div>
		</td>
	</tr>
</table>
<br>