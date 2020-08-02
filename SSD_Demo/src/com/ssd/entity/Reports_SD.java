/**
 * 
 */
package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class to read data from *.csv file with additional fields to control: - which
 * month of data - when added - who add report to db - company
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 29 sty 2020
 * 
 */
@Entity
@Table(name = "reports_sd")
public class Reports_SD {

	/**
	 * data-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
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
	 * Date of adding report/ticket to reports_sd by user.
	 */
	@Column(name = "upload_date")
	String upload_date;

	/**
	 * Reported Month.
	 */
	@Column(name = "period_id")
	int period_id;

	/**
	 * Reported Company - FK.
	 */
	@Column(name = "company_id")
	int company_id;

	/**
	 * User loading report to reports_sd table.
	 */
	@Column(name = "user_id")
	int user_id;
	
	/**
	 * Date of registration ticket - to check period.
	 */
	@Column(name="registerDate")
	String registerDate;

	
	/**
	 * Default Constructor.
	 */
	public Reports_SD() {}
	
	/**
	 * Constructor to get simple row from file.
	 * 
	 * @param type
	 * @param category
	 * @param status
	 * @param agent
	 * @param registerDate
	 */
	public Reports_SD(String type, String category, String closeDate, String agent, String registerDate) {
		this.type = type;
		this.closeDate = closeDate;
		this.category = category;
		this.agent = agent;
		this.registerDate = registerDate;
	}

	

	/**
	 * Default Getters, Setters and toString-method:
	 */

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

	public String getUpload_date() {
		return upload_date;
	}

	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public int getPeriod_id() {
		return period_id;
	}

	public void setPeriod_id(int month_id) {
		this.period_id = month_id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Reports_SD [id=" + id + ", type=" + type + ", closeDate=" + closeDate + ", category=" + category
				+ ", agent=" + agent + ", upload_date=" + upload_date + ", period_id=" + period_id + ", company_id="
				+ company_id + ", user_id=" + user_id + ", registerDate=" + registerDate + "]";
	}

	

}
