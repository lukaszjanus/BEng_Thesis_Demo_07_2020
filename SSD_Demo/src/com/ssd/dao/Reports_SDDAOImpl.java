package com.ssd.dao;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.dao.views.Reports_SD_UploadedDAO;
import com.ssd.dao.views.Reports_SD_UploadedDAOImpl;
import com.ssd.entity.Reports_SD;
import com.ssd.entity.Reports_Schema;
import com.ssd.entity.viewsDB.Reports_SD_Uploaded;
import com.ssd.logs.Logs;

/**
 * Class with methods of manage reports: - add to db - show - filters - export
 * to excel
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 lut 2020
 *
 */
public class Reports_SDDAOImpl implements Reports_SDDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method prepare data uploaded from *.csv file to save in db. Before save in
	 * db, method @see {@link preventAddReportAgain} check added periods for given
	 * company, and stop upload for the same period again.
	 *
	 * @param userName   - user which add report as String
	 * @param company_id - converted from String company id as int
	 * @param month      - month substract in controller from period
	 * @param year       - year substract in controller from period
	 * @param list       - String list
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@Override
	public void uploadReportCSV(String userName, int company_id, String month, String year, List<String> list)
			throws SecurityException, IOException {

		Reports_SD_PeriodDAO p = new Reports_SD_PeriodDAOImpl();
		int period_id = p.getPeriodId(month, year);

		if (period_id == 0) {
			p.addPeriod(month, year);
			period_id = p.getPeriodId(month, year);
		}

		UsersDAO user = new UsersDAOImpl();
		int user_id = user.getUserByUsername(userName).getId();

		/* ************ */

		List<Reports_SD> reportsData = objectsFromCSV(list, company_id, period_id, user_id);

		DateTimeFormatter dateLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		for (int i = 0; i < reportsData.size(); i++) {

			reportsData.get(i).setUpload_date(dateLog.format(now));

			session.saveOrUpdate(reportsData.get(i));
			Logs log = new Logs();
			log.addLogDataReport(SecurityContextHolder.getContext().getAuthentication().getName() + " added row to db:"
					+ reportsData.get(i) + " in " + dateLog + ".", 0);
		}

		session.getTransaction().commit();
		session.close();

	}

	/**
	 * Method convert objects from String-csv to object list.
	 * 
	 * @param list       - String
	 * @param userName   - String
	 * @param period_id  - int
	 * @param company_id - int
	 * 
	 * @return objects list of Reports_SD
	 */
	private ArrayList<Reports_SD> objectsFromCSV(List<String> list, int company_id, int period_id, int user_id) {

		int i = 0;
		ArrayList<Reports_SD> reports = new ArrayList<Reports_SD>();

		// List<Integer> schemaList = new ArrayList<Integer>();
		Reports_SchemaDAO getSchema = new Reports_SchemaDAOImpl();
		Reports_Schema schema = getSchema.getSchema(company_id);

		int typeColumn = schema.getType();
		int categoryColumn = schema.getCategory();
		int closeDateColumn = schema.getCloseDate();
		int agentColumn = schema.getAgent();
		int registerDateColumn = schema.getDate();
		int flagStatus = schema.getIsClosed();
		String prefix = schema.getPrefix();
		int subStringCount = prefix.length();
		char splitter = schema.getSplitter();

		int lineLength = 0;
		/* read all lines and divide to row and objects: */
		for (String line : list) {
			try {

				i++;

				String[] l = line.split(String.valueOf(splitter));

				// System.out.println(i+ " lista: "+line.toString());

				String agent = deletePolishSigns(l[agentColumn]);

				Reports_SD reportRow = new Reports_SD(l[typeColumn], l[categoryColumn], l[closeDateColumn], agent,
						l[registerDateColumn]);

				int checkRowLength = reportRow.toString().length();
				if (checkRowLength < 127) {
					flagStatus = 2;
				}

				/**
				 * flagStatus - select closed or not closed interactions:
				 * 
				 * In check schema - all rows add (0) or only close (1, condition: 'closeDate'
				 * must be empty)
				 */
				String tempStatus = reportRow.getCloseDate();

				switch (flagStatus) {
				case 0: {
					/* select only incident - recognize after prefix */
					if (!prefix.equalsIgnoreCase(reportRow.getType().substring(0, subStringCount))) {

						// System.out.println(reportRow.getType() + " break 0");
						break;
					}

					reportRow.setCompany_id(company_id);
					reportRow.setPeriod_id(period_id);
					reportRow.setUser_id(user_id);

					/* small date format */
					reportRow.setCloseDate(formatDateString(reportRow.getCloseDate()));
					reportRow.setRegisterDate(formatDateString(reportRow.getRegisterDate()));
					// System.out.println("lista 0: "+reportRow.toString());
					reports.add(reportRow);
					break;
				}
				case 1: {
					if (tempStatus.isEmpty() == false) {
						/* select only incident - recognize after prefix */
						if (!prefix.equalsIgnoreCase(reportRow.getType().substring(0, subStringCount))) {

							// System.out.println(reportRow.getType() + " break 1");
							break;
						}

						// System.out.println("lista 1: "+reportRow.toString());

						reportRow.setCompany_id(company_id);
						reportRow.setPeriod_id(period_id);
						reportRow.setUser_id(user_id);

						/* small date format */
						reportRow.setCloseDate(formatDateString(reportRow.getCloseDate()));
						reportRow.setRegisterDate(formatDateString(reportRow.getRegisterDate()));

						reports.add(reportRow);

					} else {
						break;
					}

					break;
				}
				case 2: {
					System.out.println("Break");
					/* empty row */
					break;
				}
				}
			} catch (Exception e) {
				//System.out.println(e);
			}
		}

		return reports;

	}

	/**
	 * Method check before upload data, if report for given month are not added
	 * earlier.
	 *
	 * @param company_id int
	 * @param month      as string in format mm
	 * @param year       as string in format yyyy
	 * @return boolean flag, not null
	 */
	@Override
	public boolean preventAddReportAgain(int company_id, String month, String year) {

		boolean flag = false;
		Reports_SD_UploadedDAO report = new Reports_SD_UploadedDAOImpl();
		List<Reports_SD_Uploaded> list = new ArrayList();

		list = report.checkUploadedReport(company_id, month, year);
		if (list.size() > 0) {
			flag = true;
		}

		return flag;
	}

	/**
	 * Method prepare data uploaded from *.xlsx file to save in db. Before save in
	 * db, method @see {@link preventAddReportAgain} check added periods for given
	 * company, and stop upload for the same period again.
	 *
	 * @param userName   - user which add report as String
	 * @param company_id - converted from String company id as int
	 * @param month      - month substract in controller from period
	 * @param year       - year substract in controller from period
	 * @param workbook   - uploaded excel object
	 * @throws IOException
	 * @throws SecurityException
	 */
	@Override
	public void uploadReportXLSX(String userName, int company_id, String month, String year, XSSFWorkbook workbook)
			throws SecurityException, IOException {

		Reports_SD_PeriodDAO p = new Reports_SD_PeriodDAOImpl();
		int period_id = p.getPeriodId(month, year);

		if (period_id == 0) {
			p.addPeriod(month, year);
			period_id = p.getPeriodId(month, year);
		}

		UsersDAO user = new UsersDAOImpl();
		int user_id = user.getUserByUsername(userName).getId();

		/* ************ */
		List<Reports_SD> reportsData = objectsFromXLSX(workbook, company_id, period_id, user_id);

		DateTimeFormatter dateLog = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDateTime now = LocalDateTime.now();

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		for (int i = 0; i < reportsData.size(); i++) {

			// SET UPLOAD DATE
			reportsData.get(i).setUpload_date(dateLog.format(now));

			session.saveOrUpdate(reportsData.get(i));
			Logs log = new Logs();
			log.addLogDataReport(SecurityContextHolder.getContext().getAuthentication().getName() + " added row to db:"
					+ reportsData.get(i) + " in " + dateLog + ".", 0);
		}

		session.getTransaction().commit();
		session.close();

	}

	/**
	 * Method convert objects from xlsx to object list.
	 * 
	 * @param workbook   - XSSFWorkbook (object with imported *.xlsx file)
	 * @param userName   - String
	 * @param period_id  - int
	 * @param company_id - int
	 * 
	 * @return objects list of Reports_SD
	 */
	private List<Reports_SD> objectsFromXLSX(XSSFWorkbook workbook, int company_id, int period_id, int user_id) {

		// int i = 0;
		ArrayList<Reports_SD> reports = new ArrayList<Reports_SD>();

		List<Integer> schemaList = new ArrayList<Integer>();
		Reports_SchemaDAO getSchema = new Reports_SchemaDAOImpl();
		Reports_Schema schema = getSchema.getSchema(company_id);

		int typeColumn = schema.getType();
		int categoryColumn = schema.getCategory();
		int closeDateColumn = schema.getCloseDate();
		int agentColumn = schema.getAgent();
		int registerDateColumn = schema.getDate();
		int flagStatus = schema.getIsClosed();
		String prefix = schema.getPrefix();
		int subStringCount = prefix.length();

		XSSFSheet worksheet = workbook.getSheetAt(0);

		for (int i = 1; i < worksheet.getLastRowNum(); i++) {

			try {
				XSSFRow row = worksheet.getRow(i);

				String agent = deletePolishSigns(row.getCell(agentColumn).toString());

				Reports_SD reportRow = new Reports_SD(row.getCell(typeColumn).toString(),
						row.getCell(categoryColumn).toString(), row.getCell(closeDateColumn).toString(), agent,
						row.getCell(registerDateColumn).toString());

				int checkRowLength = reportRow.toString().length();
				if (checkRowLength < 127) {
					flagStatus = 2;
				}

				/**
				 * flagStatus - select closed or not closed interactions:
				 * 
				 * In check schema - all rows add (0) or only close (1, condition: 'closeDate'
				 * must be empty)
				 */

				String tempStatus = reportRow.getCloseDate();

				switch (flagStatus) {
				case 0: {

					// System.out.println(" 0: " + reportRow.toString());
					/* select all incidents - recognize after prefix */
					if (!prefix.equalsIgnoreCase(reportRow.getType().substring(0, subStringCount))) {
						// System.out.println(reportRow.getType() + " break flag 0 ");
						break;
					}

					/* small date format */
					reportRow.setCloseDate(formatDateString(reportRow.getCloseDate()));
					reportRow.setRegisterDate(formatDateString(reportRow.getRegisterDate()));

					reportRow.setCompany_id(company_id);
					reportRow.setPeriod_id(period_id);
					reportRow.setUser_id(user_id);
					reports.add(reportRow);
					break;
				}
				case 1: {

					if (tempStatus.isEmpty() == false) {
						/* select only closed incident - recognize after prefix */
						// System.out.println(" 1L tempstatus empty = false");
						if (!prefix.equalsIgnoreCase(reportRow.getType().substring(0, subStringCount))) {
//							System.out.println(reportRow.getType() + " break flag 1");
							break;
						}

						reportRow.setCompany_id(company_id);
						reportRow.setPeriod_id(period_id);
						reportRow.setUser_id(user_id);

						/* small date format */
						reportRow.setCloseDate(formatDateString(reportRow.getCloseDate()));
						reportRow.setRegisterDate(formatDateString(reportRow.getRegisterDate()));

						reports.add(reportRow);

					} else {
						break;
					}

					break;
				}

				case 2: {
					// System.out.println("Break");
					/* empty row */
					break;
				}
				}

			} catch (Exception e) {
//				System.out.println("Finish at line " + i + " error: " + e);
				// break;
			}

		}

		return reports;
	}

	/**
	 * Method to format date - change only from format: dd?mm?yyyy?????? to:
	 * dd-mm-yyyy
	 * 
	 * '?' - character to change
	 *
	 * @param date as String
	 * @return formated date as String
	 */
	private String formatDateString(String date) {

		// System.out.println("date: "+date);
		if (date.length() < 6) {
			// System.out.println(" return empty ");
			return date;
		} else {
			date = date.replace("/", "-");
			date = date.replace(".", "-");
			date = date.replace(" ", "-");
			date = date.substring(0, 10);
		}

		// System.out.println("date after: "+date);
		return date;
	}

	/**
	 * Method load data from db with - all uploaded reports and return as object
	 * list.
	 *
	 * @return list of Reports_SD-objects, list can be null
	 */
	@Override
	public List<Reports_SD> getDataReports() {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<Reports_SD> list = new ArrayList<Reports_SD>();

		list = session.createQuery("from Reports_SD").getResultList();
		session.getTransaction().commit();

		session.close();

		return list;
	}

	/**
	 * Method replace polish chars to english chars in given string
	 *
	 * @param word with polish chars
	 * 
	 * @return the same String without polish chars
	 */
	@Override
	public String deletePolishSigns(String agentName) {

		StringBuilder SB = new StringBuilder();
		SB.append(agentName);

		int length = agentName.length();

		String result = "";

		for (int i = 0; i < length; i++) {

			// System.out.println(SB.charAt(i));

			switch (SB.charAt(i)) {
			case '¹':
				// System.out.println(SB.charAt(i));
				SB.setCharAt(i, 'a');
				// System.out.println(SB.charAt(i));
				break;
			case 'æ':
				SB.setCharAt(i, 'c');
				break;
			case 'ê':
				SB.setCharAt(i, 'e');
				break;
			case '³':
				SB.setCharAt(i, 'l');
				break;
			case 'ñ':
				SB.setCharAt(i, 'n');
				break;
			case 'ó':
				SB.setCharAt(i, 'o');
				break;
			case 'œ':
				SB.setCharAt(i, 's');
				break;
			case 'Ÿ':
				SB.setCharAt(i, 'z');
				break;
			case '¿':
				SB.setCharAt(i, 'z');
				break;
			case '¥':
				SB.setCharAt(i, 'A');
				break;
			case 'Æ':
				SB.setCharAt(i, 'C');
				break;
			case 'Ê':
				SB.setCharAt(i, 'E');
				break;
			case '£':
				SB.setCharAt(i, 'L');
				break;
			case 'Ñ':
				SB.setCharAt(i, 'N');
				break;
			case 'Ó':
				SB.setCharAt(i, 'O');
				break;
			case 'Œ':
				SB.setCharAt(i, 'S');
				break;
			case '':
				SB.setCharAt(i, 'Z');
				break;
			case '¯':
				SB.setCharAt(i, 'Z');
				break;
			}
		}
		result = SB.toString();
		return result;
	}

}
