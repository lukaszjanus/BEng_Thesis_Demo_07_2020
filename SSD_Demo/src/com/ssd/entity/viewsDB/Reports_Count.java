package com.ssd.entity.viewsDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * View-Table used to count reports/tickets:
 * - reports_sd_uploaded
 * - company
 * - reports_preview
 * 
 * SELECT func_inc_var_session(0) AS id, period, companyName, category, agent,
 * COUNT(id) AS 'count' from reports_preview WHERE
 * 
 * period IN (SELECT DISTINCT period FROM reports_sd_uploaded) and
 * 
 * companyName IN (SELECT companyName FROM company) and
 * 
 * category IN (SELECT DISTINCT category from reports_preview order by category) and
 * 
 * agent IN (SELECT DISTINCT agent from reports_preview order by agent)
 * 
 * GROUP BY period,companyName,category, agent
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 1 mar 2020
 *
 */

@Entity
@Table(name="reports_count")
public class Reports_Count {
	
	/**
	 * id from table reports_sd
	 */
	@Id
	@Column(name="id")
	private int id;
	
	/**
	 * Column in format 'DATE' in DB.
	 */
	@Column(name = "period")
	String period;
	
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
	 * Count column - sql count() 
	 */
	@Column(name="count")
	int count;

	/**
	 * Default constructor.
	 */
	public Reports_Count() {}
	
	/**
	 * Default getters, setters and toString:
	 */
	
	public String getPeriod() {
		period = period.substring(0,7);
		return period;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPeriod(String period) {
		
		this.period = period.substring(0,7);
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Reports_Count [id=" + id + ", period=" + period + ", category=" + category + ", agent=" + agent
				+ ", companyName=" + companyName + ", count=" + count + "]";
	};
}