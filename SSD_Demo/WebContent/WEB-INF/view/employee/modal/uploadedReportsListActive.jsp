<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.uploadTable {
	font-size: 14px;
	border: 1px solid black;
	width: 100%;
}

.uploadTable td, .uploadTable th {
	padding: 3px;
	font-weight: bold;
	border-bottom: 1px solid #ddd;
	padding: 15px;
	text-align: left;
}

.uploadTable th {
	font-size: 14px;
	background-color: Tan;
	padding: 15px;
	text-align: left;
}
</style>

<div id="uploadedReportsListActive">
	<br>Imported Reports:<br> <br>
	<table class="uploadTable">
		<tr>
			<th>Period:</th>
			<th>Company*:</th>
			<th>Uploaded by:</th>
		</tr>
		<c:forEach var="temp" items="${reportsUploadedByUser}">
			<tr>
				<td>${temp.month}-${temp.year}</td>
				<td>${temp.companyName}</td>
				<td>${temp.userName}</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<br>
	<div style="font-size: 10px;">*order by period, company</div>
	<br>
</div>
