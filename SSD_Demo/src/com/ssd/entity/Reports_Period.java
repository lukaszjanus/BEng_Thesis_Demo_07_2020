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
 * ORM-Class to work with datas of loaded reports to table: @see documentation
 * of {@link Reports_SD}: - months - years
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 30 sty 2020
 * 
 */
@Entity
@Table(name="reports_period")
public class Reports_Period {

	/**
	 * id of table reports_sd_month_year, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "month")
	private String month;

	@Column(name = "year")
	private String year;

	/**
	 * Constructors:
	 */
	public Reports_Period() {

	}

	public Reports_Period(String month, String year) {
		this.month = month;
		this.year = year;
	}

	/**
	 * Default Getters and Setters:
	 */
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

	@Override
	public String toString() {
		return "Reports_SD_Period [id=" + id + ", month=" + month + ", year=" + year + "]";
	}

	
}
