package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Table with name of Companies.
 * All fields are with condition 'not null'.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
@Entity
@Table(name = "company")
public class Company {

	

	/**
	 * Company-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	
	 /**
	  *  Name of Company - unique field (index).
	  */
	@Column(name ="companyName")
	private String companyName;

	
	/**
	 * Information, that company is active.
	 * Values:
	 * '0' - company is enabled (default value in insert)
	 * '1' - company is disabled
	 * 
	 */
	@Column(name="enabled")
	private boolean enabled;

	

	/**
	 * Default non-parameter constructor.
	 */
	public Company() {}
	
	/**
	 * Constructor without 'enabled' field.
	 * 
	 * @param id
	 * @param companyName
	 */
	public Company(int id, String companyName) {

		this.id = id;
		this.companyName = companyName;
	}
	
	/**
	 * Constructor with all fields.
	 * 
	 * @param id
	 * @param companyName
	 * @param enabled
	 */
	public Company(int id, String companyName, boolean enabled) {
		this.id = id;
		this.companyName = companyName;
		this.enabled = enabled;
	}


	/** Default methods get-set*/

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	
	/**
	 * Default method to show object information.
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", isActive=" + enabled + "]";
	}

	

	
}
