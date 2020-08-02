package com.ssd.dao;

public interface Reports_SD_PeriodDAO {

	/**
	 * Method save new period of added report.
	 * 
	 * @param month - as String
	 * @param year  - as String
	 */
	public void addPeriod(String month, String year);

	/**
	 * Method return id of month-year period.
	 * Used f.e. before add new position to db.
	 *
	 * @param month String
	 * @param year String
	 * 
	 * @return int id
	 */
	public int getPeriodId(String month, String year);
}
