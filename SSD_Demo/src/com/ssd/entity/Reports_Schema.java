package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Table used to save sequence of columns for reports-file of uploaded company.
 * Column 'prefix' and 'isClosed' are for customize uploaded data:
 * 
 * isClosed inform, if unclose ticket (without finish date) should be uploaded too.
 * 'prefix' is for customize type of ticket - it could be different for every companies, f.e.:
 * INC2992057 -> incidents, here starts from: 'I', 'IN', or 'INC'
 * If prefix will be empty, all data will be uploaded from report (except condition isClosed).
 *   
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 13 lut 2020 
 *
 */
@Entity
@Table(name="reports_schema")
public class Reports_Schema {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	/**
	 * Reported Company - FK.
	 */
	@Column(name = "company_id")
	int company_id;
	
	/**
	 * Type of ticket (f.e. incident).
	 */
	@Column(name="type")
	int type;
	
	/**
	 * Status of ticket - if empty, ticket is not closed; if not empty - ticket is closed.
	 */
	@Column(name="closeDate")
	int closeDate;
	
	/**
	 * Category of problem (f.e. printer, system).
	 */
	@Column(name="category")
	int category;
	
	/**
	 * Service Desk agent name.
	 */
	@Column(name="agent")
	int agent;
	
	/**
	 * Date of registering ticket - to check if data is from wrong month.
	 */
	@Column(name="date")
	int date;
		
	/**
	 * Type of ticket - prefix to recognize incident (f.e. INC*)
	 */
	@Column(name="prefix")
	String prefix;
	
	/**
	 * Information about count of charakters to check as prefix
	 */
	@Column(name="subStringCount")
	int subStringCount;	
	
	/**
	 * Information about close (1) or all tickets (0) to read.
	 */
	@Column(name="isClosed")
	int isClosed;
	
	@Column(name="splitter")
	public char splitter;
	
	/** Default constructor: */
	public Reports_Schema() {}

	/** 
	 * Constructor with started field - id.	
	 * 
	 * This constructor create 'new empty schema' for new created Company.
	 * All columns fields (besides id as PK in DB, company_id and fields below) have
	 * default values '0'. 
	 * 
	 * */
	public Reports_Schema(int company_id) {
		this.company_id=company_id;
		this.prefix="IN";
		this.subStringCount=2;
		this.splitter=';';
	}
	
	/* Default getters, setters and 'to String' */
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCloseDate() {
		return closeDate;
	}


	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}


	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getAgent() {
		return agent;
	}

	public void setAgent(int agent) {
		this.agent = agent;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public int getSubStringCount() {
		return subStringCount;
	}


	public void setSubStringCount(int subStringCount) {
		this.subStringCount = subStringCount;
	}


	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}
	
	public char getSplitter() {
		return splitter;
	}


	public void setSplitter(char splitter) {
		this.splitter = splitter;
	}


	@Override
	public String toString() {
		return "[id=" + id + ", company_id=" + company_id + ", type=" + type + ", closeDate="
				+ closeDate + ", category=" + category + ", agent=" + agent + ", date=" + date + ", prefix=\"" + prefix
				+ "\", subStringCount=" + subStringCount + ", isClosed=" + isClosed + ", splitter=\"" + splitter + "\"]";
	}

	
}
