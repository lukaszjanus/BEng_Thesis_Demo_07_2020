package com.ssd.tools;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
import com.ssd.entity.containers.StandardReportContainer;


/**
 * Tool-class used to prepare *.xlsx file from generated list.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 2 mar 2020
 *
 */
public class XLSXExport extends AbstractXlsxView {

	/**
	 * AbstractXlsxView method to initiate generate file.
	 */
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest req,
			HttpServletResponse response) throws Exception {

		String actionTab = req.getParameter("actionTab"); // generate file for chosen tab

		switch (actionTab) {

		case "actionTab0": {
			tab0Generate(model, workbook);
			break;
		}
		case "actionTabFull1": {
			FullTab1Generate(model, workbook);
			break;
		}
		case "actionTabFull2": {			
			String company = req.getParameter("company2l");
			FullTab2Generate(model, workbook, company);
			break;
		}		
		case "actionTabFull3":{		
			FullTab3Generate(model, workbook);		
			break;
		}
		}
	}

	private void FullTab3Generate(Map<String, Object> model, Workbook workbook) {
		
		List<StandardReportContainer> list = (List<StandardReportContainer>) model.get("excelExportTab3");

		Sheet sheet = workbook.createSheet("categoryCount");
		
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Id:");
		header.createCell(1).setCellValue("Period:");
		header.createCell(2).setCellValue("Agent:");
		header.createCell(3).setCellValue("Category:");
		header.createCell(4).setCellValue("Quantity:");

		int rowNum = 1;
		for (int i = 0; i < list.size(); i++) {

			StandardReportContainer row = list.get(i);

			Row rowSheet = sheet.createRow(rowNum++);
			rowSheet.createCell(0).setCellValue(row.getId());
			rowSheet.createCell(1).setCellValue(row.getMonth_0());
			rowSheet.createCell(2).setCellValue(row.getAgent());
			rowSheet.createCell(3).setCellValue(row.getCategory());
			rowSheet.createCell(4).setCellValue(row.getCount());
		}
	}

	/**
	 * Generate excel for Standard Report - columns: No, Company, (current month),
	 * (current month-1), (current month-2), Quarter before, Year before
	 * 
	 * @param model
	 * @param workbook
	 */
	private void tab0Generate(Map<String, Object> model, Workbook workbook) {
		List<StandardReportContainer> list = (List<StandardReportContainer>) model.get("excelExportTab0");

		Sheet sheet = workbook.createSheet("Standard Report");

		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue(list.get(0).getId());
		header.createCell(1).setCellValue(list.get(0).getCompanyName());
		header.createCell(2).setCellValue(list.get(0).getMonth_0());
		header.createCell(3).setCellValue(list.get(0).getMonth_1());
		header.createCell(4).setCellValue(list.get(0).getMonth_2());
		header.createCell(5).setCellValue(list.get(0).getQuarter());
		header.createCell(6).setCellValue(list.get(0).getYear());

		CellStyle dateStyle = workbook.createCellStyle();

		for (int i = 0; i < list.size(); i++) {

			StandardReportContainer row = list.get(i);
			Row rowSheet = sheet.createRow(i);
			rowSheet.createCell(0).setCellValue(row.getId());
			rowSheet.createCell(1).setCellValue(row.getCompanyName());
			rowSheet.createCell(2).setCellValue(row.getMonth_0());
			rowSheet.createCell(3).setCellValue(row.getMonth_1());
			rowSheet.createCell(4).setCellValue(row.getMonth_2());
			rowSheet.createCell(5).setCellValue(row.getQuarter());
			rowSheet.createCell(6).setCellValue(row.getYear());
		}
	}

	/**
	 * Generate excel for view - columns: No, Period, Company, Count
	 * 
	 * @param model
	 * @param workbook
	 */
	private void FullTab1Generate(Map<String, Object> model, Workbook workbook) {

		List<StandardReportContainer> list = (List<StandardReportContainer>) model.get("excelExportTab1");

		Sheet sheet = workbook.createSheet("Reports Data");

		int rowNum = 1;
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("No");
		header.createCell(1).setCellValue("Period");
		header.createCell(2).setCellValue("Company");
		header.createCell(3).setCellValue("Count");

		for (int i = 0; i < list.size(); i++) {

			StandardReportContainer row = list.get(i);

			Row rowSheet = sheet.createRow(rowNum++);
			rowSheet.createCell(0).setCellValue(row.getId());
			rowSheet.createCell(1).setCellValue(row.getCompanyName());
			rowSheet.createCell(2).setCellValue(row.getMonth_0());
			rowSheet.createCell(3).setCellValue(row.getCount());
		}
	}

	/**
	 * method not used - to delete after tests
	 * 
	 * Generate excel for view - columns: No, Company, Count
	 * 
	 * @param model
	 * @param workbook
	 */
	private void FullTab2Generate(Map<String, Object> model, Workbook workbook, String company) {
		
		List<StandardReportContainer> list = (List<StandardReportContainer>) model.get("excelExportTab2");

		Sheet sheet = workbook.createSheet("company");
		
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Id:");
		header.createCell(1).setCellValue("Agent:");
		header.createCell(2).setCellValue("Period:");
		header.createCell(3).setCellValue("Quantity:");

		int rowNum = 1;
		for (int i = 0; i < list.size(); i++) {

			StandardReportContainer row = list.get(i);

			Row rowSheet = sheet.createRow(rowNum++);
			rowSheet.createCell(0).setCellValue(row.getId());
			rowSheet.createCell(1).setCellValue(row.getAgent());
			rowSheet.createCell(2).setCellValue(row.getMonth_0());
			rowSheet.createCell(3).setCellValue(row.getCount());
		}
	}
}
