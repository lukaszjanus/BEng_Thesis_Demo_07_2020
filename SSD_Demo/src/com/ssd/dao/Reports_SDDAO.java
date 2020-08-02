package com.ssd.dao;

import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ssd.entity.Reports_SD;

/**
 * Interface with methods of manage reports:
 * - add to db
 * - show
 * - filters
 * - export to excel
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 lut 2020 
 *
 */
public interface Reports_SDDAO {

	
	/**
	 * Method prepare data uploaded from *.csv file to save in db.
	 * Before save in db, method @see {@link preventAddReportAgain} check added periods for given company,
	 * and stop upload for the same period again.
	 *
	 * @param userName - user which add report as String
	 * @param company_id - converted from String company id as int
	 * @param month - month substract in controller from period
	 * @param year - year substract in controller from period
	 * @param list - String list
	 * 
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	void uploadReportCSV(String userName, int company_id, String month, String year, List<String> list) throws SecurityException, IOException;

	
	/**
	 * Method check before upload data, if report for given month are not added earlier.
	 *
	 * @param company_id int
	 * @param month as string in format mm
	 * @param year as string in format yyyy
	 * 
	 * @return boolean flag, not null
	 */
	boolean preventAddReportAgain(int company_id, String month, String year);

	/**
	 * Method prepare data uploaded from *.xlsx file to save in db.
	 * Before save in db, method @see {@link preventAddReportAgain} check added periods for given company,
	 * and stop upload for the same period again.
	 *
	 * @param userName - user which add report as String
	 * @param company_id - converted from String company id as int
	 * @param month - month substract in controller from period
	 * @param year - year substract in controller from period
	 * @param workbook - uploaded excel object
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	void uploadReportXLSX(String userName, int company_id, String month, String year, XSSFWorkbook workbook) throws SecurityException, IOException;

	/**
	 * Method load data from db with - all uploaded reports and return as object list.
	 *
	 * @return list of Reports_SD-objects, list can be null 
	 */
	public List<Reports_SD> getDataReports();


	/**
	 * Method replace polish chars to english chars in given string
	 *
	 * @param word with polish chars
	 * 
	 * @return the same String without polish chars 
	 */
	public String deletePolishSigns(String word);
}