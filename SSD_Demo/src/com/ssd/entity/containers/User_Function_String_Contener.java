package com.ssd.entity.containers;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Class container - to extend using class User_Function as table swapping id-number to String-names.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020
 * 
 * @see Documentation of {@link User_Function}
 *
 */
public class User_Function_String_Contener {
	
	/**
	 * id, primary key of user_functions.
	 */
	@Id
	@Column(name="id")
	private int id;
	
	/**
	 * User-name (login)
	 */
	private String userName;
	/**
	 * Name of Company (project)
	 */
	private String companyName;
	/**
	 * Function name 
	 */
	private String functionName;
	
	/**
	 * Default constructor - without parameters.
	 */
	public User_Function_String_Contener() {}
	
	/**
	 * Constructor to take string data. No one can be null.
	 * 
	 * @param id - id from User_Functions
	 * @param userName - login of user.
	 * @param companyName - name of company.
	 * @param functionName - name of function.
	 */
	public User_Function_String_Contener(int id, String userName, String companyName, String functionName) {
		this.id=id;
		this.userName = userName;
		this.companyName = companyName;
		this.functionName = functionName;
	}

	
	/**
	 * Return int - id.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set id from User_Functions
	 *
	 * @param id original id of User_Functions-object
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return string - login.
	 *
	 * @return login
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set login - used in forms.
	 *
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Return string - company name.
	 *
	 * @return login
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Set company name - used in forms.
	 *
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * Return string - function name.s
	 *
	 * @return login
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * Set function name - used in forms.
	 *
	 * @param functionName
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	/**
	 * Default method 'to string' - only for tests.
	 * 
	 * @return string with all object values. 
	 */
	@Override
	public String toString() {
		return "User_Function_String_Contener: userName=" + userName + ", companyName=" + companyName
				+ ", functionName=" + functionName + "";
	}
}
