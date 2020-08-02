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
 * Class with list and information about functions.
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
@Entity
@Table(name = "functions")
public class Functions {

	/**
	 * Function-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Function name - unique field (index).
	 */
	@Column(name = "functionName")
	private String functionName;
	
	/**
	 * Short information about function.
	 */
	@Column(name = "description")
	private String description;

	/**
	 * Information, that function is active (enabled) or disabled.
	 */
	@Column(name="enabled")
	private boolean enabled;
	

	/**
	 * Default non-parameters Constructor.
	 */
	public Functions() {
	}
	

	/**
	 * Constructor with all fields - used to add new function.
	 *  	
	 * @param id
	 * @param functionName
	 * @param description
	 * @param enabled
	 */
	public Functions(int id, String functionName, String description, boolean enabled) {
		
		this.id = id;
		this.functionName = functionName;
		this.description = description;
		this.enabled = enabled;
	}

	/** Default getters, setters and toString*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * Default method to show object information.
	 */
	@Override
	public String toString() {
		return "Functions [id=" + id + ", functionName=" + functionName + ", description=" + description + ", enabled="
				+ enabled + "]";
	}
	
	
}
