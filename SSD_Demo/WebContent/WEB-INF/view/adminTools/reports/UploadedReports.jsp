<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>

<title>Uploaded Reports</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta name="author" content="Lukasz Janus" />

<link
	href="${pageContext.request.contextPath}/resources/styles/main_program.css"
	rel="stylesheet" />

<link
	href="${pageContext.request.contextPath}/resources/styles/mainMenu.css"
	rel="stylesheet" />

<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>

<style type="text/css">
.wybierz {
	/* background: olive; */
	float: left;
	font-size: 11px;
	text-align: center;
	font-family: arial black;
	height: 3em;
	line-height: 3em;
	border-left: 1px solid;
	border-right: 1px solid;
	border-bottom: 1px solid;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	width: 15%;
}

.wybierz:hover {
	transition-duration: 0.9s;
}

#onSiteMenu0 {
	background-color: gold;
}

#onSiteMenu1 {
	background-color: silver;
}

#onSiteMenu2 {
	background-color: silver;
}

#onSiteMenu3 {
	background-color: silver;
}

#zaw0 {
	display: block;
	width: 1000px;
}

#zaw1, #zaw2, #zaw3 {
	display: none;
	/* background-color: CORAL; */
	width: 1000px;
}

.instruct {
	font-size: 11px;
}

/* 'select' size */
.selectV {
	width: 152px;
}

#leftForms0 {
	width: 50%;
	display: flex;
	justify-content: left;
	float: left;
}

#leftForms1, #leftForms2, #leftForms3 {
	width: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 100%;
	/*margin: 2px;
	 background-color: CORAL; */
}

#rightForms0, #rightForms1, #rightForms2, #rightForms3 {
	width: 50%;;
	display: flex;
	align-items: center;
	justify-content: center;
	float: left;
	height: 100%;
}

#leftForms1 table, #rightForms1 table {
	width: 100%;
}

#leftForms2 table, #rightForms2 table {
	width: 100%;
}

#leftForms3 table, #rightForms3 table {
	width: 99%;
}

#rightForms1 fieldset {
	height: 30px;
	align-items: center;
	height: 100%;
}

#resultCount0Div {
	height: 15px;
	width: 100px;
	display: flex;
	align-items: center;
	justify-content: center;
}

#selectCategory3l, #selectCategory3r {
	width: 100%;
}
</style>

<script
	src="${pageContext.request.contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/menu.js"></script>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
	/* functions decide, which tab should be now display. */
	function jsZaw0(form) {

		document.getElementById("zaw1").style.display = "none";
		document.getElementById("onSiteMenu1").style.background = "silver";

		document.getElementById("zaw2").style.display = "none";
		document.getElementById("onSiteMenu2").style.background = "silver";

		document.getElementById("zaw3").style.display = "none";
		document.getElementById("onSiteMenu3").style.background = "silver";

		document.getElementById("zaw0").style.display = "block";
		document.getElementById("onSiteMenu0").style.background = "gold";
	}

	function jsZaw1(form) {

		document.getElementById("zaw1").style.display = "block";
		document.getElementById("onSiteMenu1").style.background = "gold";

		document.getElementById("zaw2").style.display = "none";
		document.getElementById("onSiteMenu2").style.background = "silver";

		document.getElementById("zaw3").style.display = "none";
		document.getElementById("onSiteMenu3").style.background = "silver";

		document.getElementById("zaw0").style.display = "none";
		document.getElementById("onSiteMenu0").style.background = "silver";

		/* right column to compare */

		var compareFlag1 = document.getElementById("showCompare1").checked;
		if (compareFlag1 == true) {
			document.getElementById("rightSelect1r").style.display = "";
			document.getElementById("AllResults1r").style.display = "";
		} else {
			document.getElementById("rightSelect1r").style.display = "none";
			document.getElementById("AllResults1r").style.display = "none";
		}
	}

	function jsZaw2(form) {
		//alert("Test2");
		document.getElementById("zaw1").style.display = "none";
		document.getElementById("onSiteMenu1").style.background = "silver";

		document.getElementById("zaw2").style.display = "block";
		document.getElementById("onSiteMenu2").style.background = "gold";

		document.getElementById("zaw3").style.display = "none";
		document.getElementById("onSiteMenu3").style.background = "silver";

		document.getElementById("zaw0").style.display = "none";
		document.getElementById("onSiteMenu0").style.background = "silver";

		/* right column to compare */
		var compareFlag2 = document.getElementById("showCompare2").checked;
		if (compareFlag2 == true) {
			document.getElementById("AllResults2r").style.display = "";
		} else {
			document.getElementById("AllResults2r").style.display = "none";
		}
	}

	function jsZaw3(form) {

		document.getElementById("zaw1").style.display = "none";
		document.getElementById("onSiteMenu1").style.background = "silver";

		document.getElementById("zaw2").style.display = "none";
		document.getElementById("onSiteMenu2").style.background = "silver";

		document.getElementById("zaw3").style.display = "block";
		document.getElementById("onSiteMenu3").style.background = "gold";

		document.getElementById("zaw0").style.display = "none";
		document.getElementById("onSiteMenu0").style.background = "silver";

		/* right column to compare */
		var compareFlag3 = document.getElementById("showCompare3").checked;
		if (compareFlag3 == true) {
			document.getElementById("AllResults3r").style.display = "";
		} else {
			document.getElementById("AllResults3r").style.display = "none";
		}
	}

	/* function check field with periods in tab1 */
	function checkFields0(form) {

		var company0 = $('#company0').val();
		if (company0 == "") {
			document.getElementById("tab0ValidateCompany").innerHTML = "Please select Company to generate Excel-file.";
			return false;
		}

		var monthCount = $('#period0').val();
		if (monthCount == "0") {
			document.getElementById("tab0Validate").innerHTML = "Please select period";
			return false;
		}
	}

	/* function check field with periods in tab1 */
	function checkFields1(form) {

		var monthCount = $('#period1').val();

		if (monthCount == "0") {
			document.getElementById("tab1Validate").innerHTML = "Please select period";
			return false;
		}

		var company1l = getMultipleSelectedValue('company1l');
		if (company1l == "") {
			document.getElementById("tab1ValidateCompanyLeft").innerHTML = "Please select company!";
			return false;
		}

		var compareFlag1 = document.getElementById("showCompare1").checked;
		var company1r = $('#company1r').val();
		if (company1r == null && compareFlag1 == true) {
			document.getElementById("tab1ValidateCompanyRight").innerHTML = "Please select company!";
			return false;
		}
	}

	/* function check BookMark 'Add subfolder' */
	function checkFields2(form) {

		var monthCount = $('#period2').val();
		if (monthCount == "0") {
			document.getElementById("tab2Validate").innerHTML = "Please select period!";
			return false;
		}

		var company2l = getMultipleSelectedValue('company2l');
		if (company2l == "") {
			document.getElementById("tab2ValidateCompanyLeft").innerHTML = "Please select company!";
			return false;
		}

		var selectUser2l = getMultipleSelectedValue('selectUser2l');
		var selectUser2r = getMultipleSelectedValue('selectUser2r');

		if (selectUser2l == "" && selectUser2r == "") {
			document.getElementById("selectUserCommunicate2l").innerHTML = "Please select user 1!";
			document.getElementById("selectUserCommunicate2r").innerHTML = "Please select user 2!";

			return false;
		}

		if (selectUser2l == "No agents for selected period"
				|| selectUser2r == "No agents for selected period") {
			//document.getElementById("selectUserCommunicate2l").innerHTML = "No uploaded reports for this criteria!";
			//document.getElementById("selectUserCommunicate2r").innerHTML = "No uploaded reports for this criteria!";
			document.getElementById("selectUserCommunicate2l").innerHTML = "";
			document.getElementById("selectUserCommunicate2r").innerHTML = "";
			document.getElementById("tab2Validate").innerHTML = "No uploaded reports for this criteria!";

			return false;
		}

	}

	function checkFields3(form) {
		var monthCount = $('#period3').val();
		if (monthCount == "0") {
			document.getElementById("tab3Validate").innerHTML = "Please select period!";
			return false;
		}

		var company3l = getMultipleSelectedValue('company3l');
		if (company3l == "") {
			document.getElementById("tab3ValidateCompanyLeft").innerHTML = "Please select at least one company!";
			return false;
		}

		var selectUser3l = getMultipleSelectedValue('selectUser3l');
		if (selectUser3l == "") {
			document.getElementById("selectUserCommunicate3l").innerHTML = "Please select user 1!";
			return false;
		}

		selectCategory3l = $('#selectCategory3l').val();
		if (selectCategory3l == null) {
			document.getElementById("selectCategoryCommunicate3l").innerHTML = "Please select Category!";

			return false;
		} else {

			var selectUser3r = $('#selectUser3r').val();
			var selectCategory3r = $('#selectCategory3r').val();
			if (selectUser3r == null && selectCategory3r != null) {
				document.getElementById("selectUserCommunicate3r").innerHTML = "Please select Agent to Compare!";
				return false;
			}
			if (selectUser3r != null && selectCategory3r == null) {
				document.getElementById("selectCategoryCommunicate3r").innerHTML = "Please select Category!";
				return false;
			}
		}
	}

	function readCookie(name) {

		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ')
				c = c.substring(1, c.length);
			if (c.indexOf(nameEQ) == 0) {
				return c.substring(nameEQ.length, c.length);
			}
		}
		return null;
	}

	/***************************************
	 **************AJAX methods**************
	 ****************************************/

	/*** All Tabs - universal functions ***/

	/* initialize ajax function for choosen element: */

	$(document).ready(function() {
		//$("#uploadReporsCountTab1")

		$("#period0").change(function() {
			//alert("p");
			countTab0();
		})
		$("#company0").change(function() {
			countTab0();
		})

		$("#period1").change(function() {
			countTab1();
		})

		$("#showCompare1").change(function() {
			$("resultTableCount1r").html("");
			$("resultReporsFullTab1r").html("");
			document.getElementById("AllResults1r").style.display = "none";
			document.getElementById("rightSelect1r").style.display = "none";

			countTab1();
		})

		$("#company1l").change(function() {
			countTab1();
		})

		$("#company1r").change(function() {
			countTab1();
		})

		$("#period2").change(function() {
			selectCompanyTab2();
		})

		$("#company2l").change(function() {
			selectCompanyTab2();
		})

		$("#showCompare2").change(function() {

			$("#selectUser2r").html(null);
			document.getElementById("AllResults2r").style.display = "none";
			selectCompanyTab2();
		})

		$("#period3").change(function() {
			selectCompanyTab3();
		})

		$("#showCompare3").change(function() {

			$("#showAgents3r").html("");
			$("#showCategories3r").html("");
			document.getElementById("AllResults3r").style.display = "none";

			selectCompanyTab3();
		})

		$("#company3l").change(function() {
			selectCompanyTab3();
		})

		$("#company3r").change(function() {
			selectCompanyTab3();
		})

	})

	/*********************** Tab 0 *****************************/

	function countTab0() {

		document.getElementById("tab0Validate").innerHTML = "";
		document.getElementById("tab0ValidateCompany").innerHTML = "";

		var monthCount = $('#period0').val();
		var resultCount0 = "#resultCount0";
		var company0 = getMultipleSelectedValue('company0');

		if (monthCount == "0") {
			document.getElementById("tab0Validate").innerHTML = "Please select period";
			return false;
		}

		if (company0 == "") {
			document.getElementById("tab0ValidateCompany").innerHTML = "Please select Company";
		}

		$.ajax({
			type : 'GET',
			url : 'allTicketsCount',
			data : {
				monthCount : monthCount,
				company0 : company0
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$(resultCount0).html(result);
			}
		})

		$.ajax({
			type : 'GET',
			url : 'uploadReporsCountTab0',
			data : {
				company0 : company0,
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$("#resultTableCount0").html(result);
			}
		})
	}

	/*********************** Tab 1 *****************************/

	function countTab1() {

		document.getElementById("tab1Validate").innerHTML = "";
		document.getElementById("tab1ValidateCompanyRight").innerHTML = "";
		document.getElementById("tab1ValidateCompanyLeft").innerHTML = "";

		var monthCount = $('#period1').val();
		var company1l = $('#company1l').val();
		var company1r = $('#company1r').val();

		var compareFlag1 = document.getElementById("showCompare1").checked;
		var tempFlag1r = true;
		if (compareFlag1 == true) {

			document.getElementById("AllResults1r").style.display = "";
			document.getElementById("rightSelect1r").style.display = "";

			if (company1r == null) {
				document.getElementById("tab1ValidateCompanyRight").innerHTML = "Select Company to compare!";
				tempFlag1r = false;
			}
		} else {
			document.getElementById("AllResults1r").style.display = "none";
			document.getElementById("rightSelect1r").style.display = "none";
		}

		if (monthCount == 0) {
			document.getElementById("tab1Validate").innerHTML = "Please select range of months!";
			return false;
		}

		if (company1l == null) {
			document.getElementById("tab1ValidateCompanyLeft").innerHTML = "Please select company!";
			return false;
		}

		$.ajax({
			type : 'GET',
			url : 'uploadReporsCountTab1',
			data : {
				company1l : company1l,
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$("#resultTableCount1l").html(result);
			}
		})
		$.ajax({
			type : 'GET',
			url : 'uploadReporsFullTab1',
			data : {
				company1l : company1l,
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$("#resultReporsFullTab1l").html(result);
			}
		})

		/********* chart tab 1 ***********/
		$.ajax({
			url : "chart",
			data : {
				action : 'tab1',
				companyL : company1l,
				company1r : company1r,
				monthCount : monthCount
			},
			dataType : "JSON",
			success : function(result) {
				google.charts.load('current', {
					'packages' : [ 'corechart' ]
				});
				google.charts.setOnLoadCallback(function() {
					drawChart(result);
				});
			}
		});

		function drawChart(result) {

			var data = new google.visualization.DataTable();

			data.addColumn("string", "column1");
			data.setColumnLabel(0, "Period");
			data.addColumn("number", "column2");
			data.setColumnLabel(1, company1l);

			/* chart - add second company to compare */
			if (compareFlag1 == true) {
				data.addColumn("number", "column3");
				data.setColumnLabel(2, company1r);
			}

			var dataArray = [];

			/* chart - compare two companies */
			if (compareFlag1 == true) {
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2, obj.column3 ]);
				})
			} else { //for one company
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2 ]);
				})
			}

			data.addRows(dataArray);

			var options = {
				title : 'Tickets per Company',
				width : 1000,
				height : 400,
				bar : {
					groupWidth : '15%'
				},
				legend : {
					position : 'right'
				},
				hAxis : {
					title : 'Periods',
					viewWindowMode : 'explicit'
				},
				vAxis : {
					title : "Quantity",
					viewWindowMode : 'explicit',
					viewWindow : {
						min : -0.1
					}
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('lineChart_div1'));
			chart.draw(data, options);
		}

		/* comparing */

		if (compareFlag1 == true) {
			if (tempFlag1r == false) {
				return false;
			}
			$
					.ajax({
						type : 'GET',
						url : 'uploadReporsCountTab1',
						data : {
							company1r : company1r,
							monthCount : monthCount
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$("#resultTableCount1r").html(result);
						}
					})
			$
					.ajax({
						type : 'GET',
						url : 'uploadReporsFullTab1',
						data : {
							company1r : company1r,
							monthCount : monthCount
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$("#resultReporsFullTab1r").html(result);
						}
					})
		}

		/* data for export reports */
		$.ajax({
			type : 'GET',
			url : 'excelPrepareFullTab1',
			data : {
				company1l : company1l,
				company1r : company1r,
				monthCount : monthCount,
				compareFlag1 : compareFlag1
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
		})
	}

	/*********************** Tab tab 2 *****************************/

	/*
	Function generate agent's list available in selected company and period and add to view-tab2.
	 */
	function selectCompanyTab2() {

		document.getElementById("tab2Validate").innerHTML = "";
		document.getElementById("tab2ValidateCompanyLeft").innerHTML = "";

		var monthCount = $('#period2').val();
		var company2l = getMultipleSelectedValue('company2l');
		var resultCount = "#resultCount2";

		var flag = true;
		if (monthCount == 0) {
			document.getElementById("tab2Validate").innerHTML = "Please select range of months!";
			flag = false;
		}
		if (company2l == "") {
			document.getElementById("tab2ValidateCompanyLeft").innerHTML = "Please select company!";
			flag = false;
		}
		if (flag == false) {
			return false;
		}
		$.ajax({
			type : 'GET',
			url : 'agentsSelect2',
			data : {
				company2l : company2l,
				action : 'left',
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$("#resultSelect2l").html(result);
				var resultCount2Span = "#resultCount2l";
				var resultFullTab2Span = "#resultFullTab2l";
				$(resultCount2Span).html("");
				$(resultFullTab2Span).html("");
			}
		})

		var compareFlag = document.getElementById("showCompare2").checked;

		if (compareFlag == true) {
			document.getElementById("AllResults2r").style.display = "";
			$
					.ajax({
						type : 'GET',
						url : 'agentsSelect2',
						data : {
							company2l : company2l,
							action : 'right',
							monthCount : monthCount
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							//	alert("rezultat");
							$("#resultSelect2r").html(result);

							var resultCount2Span = "#resultCount2r";
							var resultFullTab2Span = "#resultFullTab2r";

							$(resultCount2Span).html("");
							$(resultFullTab2Span).html("");
						}
					})
		}
	}

	/*
	Method call dynamic 'user-select' generated in constructor for tab2.
	 */
	function countSelect2l() {
		countTab2('left');
	}

	function countSelect2r() {
		countTab2('right');
	}

	function countTab2(action) {

		var monthCount = $('#period2').val();
		var company2l = $('#company2l').val();

		var resultCount2Span = "";
		var resultFullTab2Span = "";
		var selectUser2 = "";
		if (action == "left") {
			selectUser2 = $('#selectUser2l').val();
			resultCount2Span = "#resultCount2l";
			resultFullTab2Span = "#resultFullTab2l";
		}

		if (action == "right") {
			selectUser2 = $('#selectUser2r').val();
			resultCount2Span = "#resultCount2r";
			resultFullTab2Span = "#resultFullTab2r";
		}

		if (selectUser2 == "No reports at this period") {
			selectUser2 = "No agent";
		}

		$.ajax({
			type : 'GET',
			url : 'agentsTicketCount2',
			data : {
				company2l : company2l,
				selectUser : selectUser2,
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$(resultCount2Span).html(result);
			}
		})

		$.ajax({
			type : 'GET',
			url : 'agentsTicketPeriodCount2',
			data : {
				company2l : company2l,
				selectUser : selectUser2,
				monthCount : monthCount
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
			success : function(result) {
				$(resultFullTab2Span).html(result);
			}
		})

		var selectUser2l = $('#selectUser2l').val();
		var selectUser2r = $('#selectUser2r').val();

		var compareFlag2 = document.getElementById("showCompare2").checked;

		/********* chart 2***********/

		$.ajax({
			url : "chart",
			data : {
				monthCount : monthCount,
				companyL : company2l,
				action : 'tab2',
				selectUser2l : selectUser2l,
				selectUser2r : selectUser2r,
				compareFlag2 : compareFlag2
			},
			dataType : "JSON",
			success : function(result) {

				google.charts.load('current', {
					'packages' : [ 'corechart' ]
				});
				google.charts.setOnLoadCallback(function() {
					drawChart(result);
				});
			}
		});

		function drawChart(result) {

			var data = new google.visualization.DataTable();
			data.addColumn("string", "column1");
			data.setColumnLabel(0, "Period");
			data.addColumn("number", "column2");
			data.setColumnLabel(1, selectUser2l);

			/* chart - add second company to compare */
			if (compareFlag2 == true) {
				data.addColumn("number", "column3");
				data.setColumnLabel(2, selectUser2r);
			}

			var dataArray = [];

			/* chart - compare two companies */
			if (compareFlag2 == true) {
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2, obj.column3 ]);
				})
			} else { //for one company
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2 ]);
				})
			}

			data.addRows(dataArray);

			var options = {
				title : 'Tickets per Company and User',
				width : 1000,
				height : 400,
				bar : {
					groupWidth : '15%'
				},
				legend : {
					position : 'right'
				},
				hAxis : {
					title : 'Periods',
					viewWindowMode : 'explicit'
				},

				vAxis : {
					title : "Quantity",
					viewWindowMode : 'explicit',
					viewWindow : {
						min : 0
					}
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('lineChart_div2'));
			chart.draw(data, options);

		}

		/* data for export reports */
		$.ajax({
			type : 'GET',
			url : 'excelPrepareFullTab2',
			data : {
				company2l : company2l,
				selectUser2l : selectUser2l,
				selectUser2r : selectUser2r,
				monthCount : monthCount,
				compareFlag2 : compareFlag2
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},

		})
	}

	/*********************** Tab 3 *****************************/

	/*
	Function generate agent's list available in selected company and period and add to view-tab3.
	 */
	function selectCompanyTab3() {

		document.getElementById("tab3Validate").innerHTML = "";
		document.getElementById("tab3ValidateCompanyLeft").innerHTML = "";

		var monthCount = $('#period3').val();

		var company3l = $('#company3l').val();

		var flag = true;

		if (monthCount == 0) {
			document.getElementById("tab3Validate").innerHTML = "Please select range of months!";
			flag = false;
		}

		if (flag == false) {
			return false;
		}

		if (company3l != "") {
			/* generate 'select-agents' left */
			$
					.ajax({
						type : 'GET',
						url : 'showAgents3',
						data : {
							company3l : company3l,
							action : 'left',
							monthCount : monthCount
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$("#showAgents3l").html(result);
						}
					})
			/* generate 'select-categories' left */
			$
					.ajax({
						type : 'GET',
						url : 'showCategories3',
						data : {
							company3l : company3l,
							action : 'left',
							monthCount : monthCount
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$("#showCategories3l").html(result);
						}
					})

			/* check if user want to compare */

			var compareFlag = document.getElementById("showCompare3").checked;

			if (compareFlag == true) {

				document.getElementById("AllResults3r").style.display = "";
				/* generate 'select-agents' right */
				$
						.ajax({
							type : 'GET',
							url : 'showAgents3',
							data : {
								company3l : company3l,
								action : 'right',
								monthCount : monthCount
							},
							error : function() {
								alert('Connection Error - please contact to Administrator');
							},
							success : function(result) {
								//	alert("rezultat");
								$("#showAgents3r").html(result);
							}
						})

				/* generate 'select-categories' right */
				$
						.ajax({
							type : 'GET',
							url : 'showCategories3',
							data : {
								company3l : company3l,
								action : 'right',
								monthCount : monthCount
							},
							error : function() {
								alert('Connection Error - please contact to Administrator');
							},
							success : function(result) {
								$("#showCategories3r").html(result);
							}
						})
			}
		}
	}

	/*
	Method call dynamic 'user-select' generated in constructor for tab2.
	 */
	function countSelect3l() {

		document.getElementById("selectUserCommunicate3l").innerHTML = "";
		document.getElementById("selectCategoryCommunicate3l").innerHTML = "";

		var monthCount = $('#period3').val();
		var company3l = $('#company3l').val();
		var selectCategory3l = $('#selectCategory3l').val();
		var selectUser3l = $('#selectUser3l').val();
		var resultSumAgentCategory3 = "";
		var resultReportAgentCategory3 = "";
		resultSumAgentCategory3 = "#resultSumAgentCategory3l";
		resultReportAgentCategory3 = "#resultReportAgentCategory3l";

		var flag = true;

		if (selectUser3l == null || company3l == null
				|| selectCategory3l == null) {
			flag = false;
			$(resultReportAgentCategory3).html("");

		}
		if (selectUser3l == "No agents for selected period"
				|| selectCategory3l == "No category found at that period and company") {
			flag = false;
			$(resultReportAgentCategory3l).html(
					"No uploaded report in db for this criteria.");
		}

		if (flag == true) {

			/* general sum table */
			$
					.ajax({
						type : 'GET',
						url : 'resultSumAgentCategory3',
						data : {
							monthCount : monthCount,
							company3 : company3l,
							selectUser3 : selectUser3l,
							selectCategory3 : selectCategory3l
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$(resultSumAgentCategory3).html(result);
						}
					})

			/* table group by period and category */
			$
					.ajax({
						type : 'GET',
						url : 'uploadReportCategoryAgentCount3',
						data : {
							monthCount : monthCount,
							action : 'left',
							company3 : company3l,
							selectUser3 : selectUser3l,
							selectCategory3 : selectCategory3l
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$(resultReportAgentCategory3).html(result);
						}
					})
			chartPrepare3();
			excelPrepare3();
		}
	}

	function countSelect3r() {

		document.getElementById("selectUserCommunicate3r").innerHTML = "";
		document.getElementById("selectCategoryCommunicate3r").innerHTML = "";

		var monthCount = $('#period3').val();
		var company3r = $('#company3l').val();
		var selectCategory3r = $('#selectCategory3r').val();
		var selectUser3r = $('#selectUser3r').val();
		var resultSumAgentCategory3 = "";
		var resultReportAgentCategory3 = "";
		resultSumAgentCategory3 = "#resultSumAgentCategory3r";
		resultReportAgentCategory3 = "#resultReportAgentCategory3r";

		var flag = true;

		if (selectUser3r == null || company3r == null
				|| selectCategory3r == null) {
			flag = false;
			$(resultReportAgentCategory3).html("");

		}
		if (selectUser3r == "No agents for selected period"
				|| selectCategory3r == "No category found at that period and company") {
			flag = false;
			$(resultReportAgentCategory3r).html(
					"No uploaded report in db for this criteria.");
		}

		if (flag == true) {

			/* general sum table */
			$
					.ajax({
						type : 'GET',
						url : 'resultSumAgentCategory3',
						data : {
							monthCount : monthCount,
							company3 : company3r,
							selectUser3 : selectUser3r,
							selectCategory3 : selectCategory3r
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$(resultSumAgentCategory3).html(result);
						}
					})

			/* table group by period and category */
			$
					.ajax({
						type : 'GET',
						url : 'uploadReportCategoryAgentCount3',
						data : {
							monthCount : monthCount,
							action : 'right',
							company3 : company3r,
							selectUser3 : selectUser3r,
							selectCategory3 : selectCategory3r
						},
						error : function() {
							alert('Connection Error - please contact to Administrator');
						},
						success : function(result) {
							$(resultReportAgentCategory3).html(result);
						}
					})
			chartPrepare3();
			excelPrepare3();
		}
	}

	/********* chart 3 generate ***********/

	function chartPrepare3() {

		var monthCount = $('#period3').val();
		var company3l = $('#company3l').val();
		var company3r = $('#company3r').val();
		var selectCategory3l = $('#selectCategory3l').val();
		var selectCategory3r = $('#selectCategory3r').val();
		var selectUser3l = $('#selectUser3l').val();
		var selectUser3r = $('#selectUser3r').val();

		var compareFlag3 = document.getElementById("showCompare3").checked;

		$.ajax({
			url : "chart",
			data : {
				monthCount : monthCount,
				companyL : company3l,
				action : "tab3",
				selectUser3l : selectUser3l,
				selectUser3r : selectUser3r,
				selectCategory3l : selectCategory3l,
				selectCategory3r : selectCategory3r,
				compareFlag3 : compareFlag3
			},
			dataType : "JSON",
			success : function(result) {

				google.charts.load('current', {
					'packages' : [ 'corechart' ]
				});
				google.charts.setOnLoadCallback(function() {
					drawChart(result);
				});
			}
		});

		function drawChart(result) {

			var data = new google.visualization.DataTable();
			data.addColumn("string", "column1");
			data.setColumnLabel(0, "Period");
			data.addColumn("number", "column2");
			data.setColumnLabel(1, selectCategory3l);

			/* chart - add second company to compare */
			if (compareFlag3 == true) {
				data.addColumn("number", "column3");
				data.setColumnLabel(2, selectCategory3r);
			}

			var dataArray = [];

			/* chart - compare two companies */
			if (compareFlag3 == true) {
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2, obj.column3 ]);
				})
			} else { //for one company
				$.each(result, function(i, obj) {
					dataArray.push([ obj.column1, obj.column2 ]);
				})
			}

			data.addRows(dataArray);

			var options = {
				title : 'Tickets per Company, User and Category',
				width : 1000,
				height : 400,
				bar : {
					groupWidth : '15%'
				},
				legend : {
					position : 'right'
				},

				hAxis : {
					title : 'Periods',
					viewWindowMode : 'explicit'
				},
				vAxis : {
					title : "Quantity",
					viewWindowMode : 'explicit',
					viewWindow : {
						min : 0
					}
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('lineChart_div3'));
			chart.draw(data, options);
		}

	}

	/*
	Function call controller method, which generate table
	with quantity of category selected by user, company and period.
	 */
	function excelPrepare3() {

		var monthCount = $('#period3').val();
		var company3l = $('#company3l').val();
		var company3r = $('#company3r').val();
		var selectCategory3l = $('#selectCategory3l').val();
		var selectCategory3r = $('#selectCategory3r').val();
		var selectUser3l = $('#selectUser3l').val();
		var selectUser3r = $('#selectUser3r').val();
		var compareFlag3 = document.getElementById("showCompare3").checked;

		/* data for export reports to xlsx */
		$.ajax({
			type : 'GET',
			url : 'excelPrepareFullTab3',
			data : {
				monthCount : monthCount,
				company3l : company3l,
				company3r : company3l, //correct-> compare in one selected company
				selectUser3l : selectUser3l,
				selectUser3r : selectUser3r,
				selectCategory3l : selectCategory3l,
				selectCategory3r : selectCategory3r,
				compareFlag3 : compareFlag3
			},
			error : function() {
				alert('Connection Error - please contact to Administrator');
			},
		})

	}

	/************************** other **************************/

	/* Function get data from 'select: multiple=true' and save as simple string */
	function getMultipleSelectedValue(nameField) {

		var tempArray = "";
		var x = document.getElementById(nameField);

		for (var i = 0; i < x.options.length; i++) {
			if (x.options[i].selected == true) {
				tempArray += ";" + x.options[i].value;
			}
		}
		tempArray = tempArray.substr(1);
		return tempArray;
	}
</script>

</head>
<body onload="initMenu()">

	<div class="container">
		<div class="TopPanel">
			<div class="TopLeftColumn">
				<a href="${pageContext.request.contextPath}/"> <img
					src="${pageContext.request.contextPath}/resources/styles/img/logoTopPanel.png" />
				</a>
			</div>
			<div class="TopCenterColumn">${nameApp}</div>
			<div class="TopRightColumn">
				User:
				<security:authentication property="principal.username" />
			</div>
			<div class="eraserFloat"></div>
		</div>
		<jsp:include page="../../menu.jsp" />
		<div class="content">
			<div class="formPositions">
				<div class="upperForDown">
					<!-- content shift down - without this div two lines covered by menu -->
				</div>
				<div class="wybierz" id="onSiteMenu0" onclick="jsZaw0(this)">Standard
					Report</div>
				<div class="wybierz" id="onSiteMenu1" onclick="jsZaw1(this)">Compare
					by Company</div>
				<div class="wybierz" id="onSiteMenu2" onclick="jsZaw2(this)">
					Compare by Users</div>
				<div class="wybierz" id="onSiteMenu3" onclick="jsZaw3(this)">Compare
					by Categories</div>
				<div class="forDown"></div>
				<div class="logonBackgroundImage">
					<div id="zaw0" class="positionOnSiteDiv">
						<jsp:include page="zaw0Content.jsp" />
					</div>
					<div id="zaw1" class="positionOnSiteDiv">
						<jsp:include page="zaw1Content.jsp" />
					</div>
					<div id="zaw2" class="positionOnSiteDiv">
						<jsp:include page="zaw2Content.jsp" />
					</div>
					<div id="zaw3" class="positionOnSiteDiv">
						<jsp:include page="zaw3Content.jsp" />
					</div>
				</div>
			</div>
			<div class="forDown"></div>
			<div class="eraserFloat"></div>
		</div>
		<div class="bottomPanel">
			<div class="BottomLeftColumn">
				<a href="${pageContext.request.contextPath}/">Home Page</a>
			</div>
			<div class="BottomRightColumn">
				<div class="BottomRightColumn" onclick='adminContact()'>${helpLabel}</div>
			</div>
			<div class="eraserFloat"></div>
		</div>
	</div>
</body>
</html>