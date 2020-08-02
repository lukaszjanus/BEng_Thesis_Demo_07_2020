package com.ssd.entity.viewsDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * View of tables:
 * - reports_sd
 * - company
 * - reports_period
 * - users
 * 
 * Table used to easier show uploaded data as String in place of id.
 * Table show all reports_sd rows. 
 * 
 * SQL Query:
 * 
 * select r.id, r.type, r.closeDate, r.upload_date, r.category, r.agent, c.companyName, p.month, p.year, u.userName
 *	from reports_sd r
 *	JOIN company c ON (r.company_id=c.id)
 *	JOIN reports_period p ON (r.period_id=p.id)
 *	JOIN users u ON (r.user_id=u.id)
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 28 lut 2020 
 *
 */

@Entity
@Table(name="reports_preview")
public class Reports_Preview {
	
	/**
	 * id from table reports_sd
	 */
	@Id
	@Column(name="id")
	private int id;
	
	/**
	 * Type of ticket (f.e. 'incident')
	 */
	@Column(name = "type")
	String type;

	/**
	 * Status of ticket (f.e. 'closed')
	 */
	@Column(name = "closeDate")
	String closeDate;

	/**
	 * Category of ticket (f.e. 'printer', 'system')
	 */
	@Column(name = "category")
	String category;

	/**
	 * User works as Service Desk agent, who have done/close ticket.
	 */
	@Column(name = "agent")
	String agent;

	/**
	 * Reported Company.
	 */
	@Column(name = "companyName")
	String companyName;
	
	/**
	 * Reported Month.
	 */
	@Column(name = "month")
	String month;

	/**
	 * Reported Year.
	 */
	@Column(name = "year")
	String year;

	/**
	 * User loading report to reports_sd table.
	 */
	@Column(name = "userName")
	String userName;
	
	/**
	 * Date of adding report/ticket to reports_sd by user.
	 */
	@Column(name = "upload_date")
	String upload_date;
	
	/**
	 * Column in format 'DATE' in DB.
	 */
	@Column(name = "period")
	String period;

	/** Default Constructor */
	public Reports_Preview() {

	}

	/** Default getters, setters and toString */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "Reports_View [id=" + id + ", type=" + type + ", closeDate=" + closeDate + ", category=" + category
				+ ", agent=" + agent + ", companyName=" + companyName + ", month=" + month + ", year=" + year
				+ ", userName=" + userName + ", upload_date=" + upload_date + "]";
	}
}
