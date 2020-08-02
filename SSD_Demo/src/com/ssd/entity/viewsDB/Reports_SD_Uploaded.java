package com.ssd.entity.viewsDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * View of tables:
 * - reports_period;
 * - reports_sd;
 * - company;
 * - users;
 * 
 * Table generated unique data used to check uploaded reports by three main subjects: period (as two columns: month and year), companyName and userName.
 * 
 * SQL query: SELECT DISTINCT p.month, p.year, c.companyName, u.username FROM
 * reports_period p right outer JOIN reports_sd r ON (p.id=r.period_id) left
 * outer JOIN company c ON (r.company_id=c.id) left OUTER JOIN users u ON
 * (r.user_id = u.id);
 * 
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 lut 2020
 *
 */

@Entity
@Table(name= "reports_sd_uploaded")
public class Reports_SD_Uploaded {

	/**
	 * id, primary key of user_functions.
	 */
	@Id
	@Column(name = "id")
	private int id;
	
	/**
	 * Period - month.
	 */
	@Column(name = "month")
	String month;
	
	/**
	 * Period - year.
	 */
	@Column(name = "year")
	String year;
	
	/**
	 * Company Name from company.
	 */
	@Column(name = "companyName")
	String companyName;
	
	@Column(name = "company_id")
	private int company_id;
	
	/**
	 * User login from users.
	 */
	@Column(name = "userName")
	String userName;
	
	/**
	 * Column in format 'DATE' in DB.
	 */
	@Column(name = "period")
	String period;
	
	/* Default constructor: */
	public Reports_SD_Uploaded() {}

	@Override
	public String toString() {
		return "Reports_SD_Uploaded [id=" + id + ", month=" + month + ", year=" + year + ", companyName=" + companyName
				+ ", company_id=" + company_id + ", userName=" + userName + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
}
