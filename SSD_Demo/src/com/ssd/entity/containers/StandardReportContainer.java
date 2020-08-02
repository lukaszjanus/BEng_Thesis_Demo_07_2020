package com.ssd.entity.containers;

import java.util.ArrayList;
import java.util.List;

/**
 *	Class-container for generate reports as string.
 *
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 15 mar 2020 
 * 
 */
public class StandardReportContainer {
	
	/**
	 * Id of object (used in list)
	 */
	public String id;
	
	/**
	 * Field integer - used to number of month
	 */
	int month_id;
	
	/**
	 * Field for companyName 
	 */
	public String companyName;
	
	/**
	 * Field for standard report period name - current month
	 */
	public String month_0;
	
	/**
	 * Field for standard report period name  - month before
	 */
	public String month_1;
	
	/**
	 * Field for standard report period name  - two months before
	 */
	public String month_2;
	
	/**
	 * Field for standard report period name  - quarter
	 */
	public String quarter;
	
	/**
	 * Field for standard report period name  - year
	 */
	public String year;
	
	/**
	 * String field for result f.e. count of tickets.
	 */
	public String count;
	
	/**
	 * Integer field for result f.e. count of tickets.
	 */
	int	countInt;
	
	/**
	 * test list for counts by periods in one object - not used
	 */
	public List<Integer> data = new ArrayList<Integer>();
	
	/**
	 * test list for counts by periods in one object - not used
	 */
	public List<Integer> mounths = new ArrayList<Integer>();

	/**
	 * Field for agentName.
	 */
	public String agent;
	
	/**
	 * Field for categoryName.
	 */
	public String category;

	/* * Constructors: * */
	
	public StandardReportContainer() {}

	public StandardReportContainer(String id, String month_0, String companyName, String count) {		
		this.id = id;
		this.companyName = companyName;
		this.month_0 = month_0;
		this.count = count;
	}

	public StandardReportContainer(String id, String companyName, String month_0, String month_1, String month_2,
			String quarter, String year) {
		this.id = id;
		this.companyName = companyName;
		this.month_0 = month_0;
		this.month_1 = month_1;
		this.month_2 = month_2;
		this.quarter = quarter;
		this.year = year;
	}

	public StandardReportContainer( String month_0, String companyName, String count) {
		this.companyName = companyName;
		this.month_0 = month_0;
		this.count = count;
	}

	/* * Standard methods: Getters, Setters and 'toString'.* */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMonth_0() {
		return month_0;
	}

	public void setMonth_0(String month_0) {
		this.month_0 = month_0;
	}

	public String getMonth_1() {
		return month_1;
	}

	public void setMonth_1(String month_1) {
		this.month_1 = month_1;
	}

	public String getMonth_2() {
		return month_2;
	}

	public void setMonth_2(String month_2) {
		this.month_2 = month_2;
	}


	public String getQuarter() {
		return quarter;
	}


	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	public int getCountInt() {
		return countInt;
	}

	public void setCountInt(int countInt) {
		this.countInt = countInt;
	}

	public int getMonth_id() {
		return month_id;
	}

	public void setMonth_id(int month_id) {
		this.month_id = month_id;
	}

	@Override
	public String toString() {
		return id+" " + month_0
				+ ", count=" + count + ", category=" + category;
	}
}
