package com.ssd.entity.viewsDB;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.ssd.entity.Users;
import com.sun.xml.bind.v2.schemagen.xmlschema.Documentation;

/**
 * View of tables: 
 * - user_functions;
 * - users;
 * - company;
 * 
 * Table generate data to easier recognize id and stringName of users, companies, functions.
 * 
 * 
 * SQL query: select user_functions.id AS id, user_functions.id_user AS id_user,
 * users.username AS userName, user_functions.id_function,
 * functions.functionName, user_functions.id_company, company.companyName from
 * user_functions join users on(user_functions.id_user = users.id) join
 * functions on (user_functions.id_function=functions.id) join company on
 * (user_functions.id_company=company.id)usersreports_sd_period;
 * 
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 19 sty 2020
 *
 */
@Entity
@Table(name = "user_functions_string_username")
public class User_Functions_String_UserName {

	/**
	 * id, primary key of user_functions.
	 */
	@Id
	@Column(name = "id")
	private int id;

	/**
	 * Foreign key of user from user_functions.
	 * 
	 * @see Documentation of {@link Users}
	 */
	@Column(name = "id_user")
	private int id_user;

	/**
	 * User login from users.
	 */
	@Column(name = "userName")
	private String userName;

	/**
	 * Foreign key of function.
	 * 
	 * @see Documentation of {@link Functions}
	 */
	@Column(name = "id_function")
	private int id_function;

	/**
	 * Function name from functions.
	 */
	@Column(name = "functionName")
	private String functionName;

	/**
	 * Foreign key of company.
	 * 
	 * @see Documentation of {@link Company}
	 */
	@Column(name = "id_company")
	private int id_company;

	/**
	 * Company Name from company.
	 */
	@Column(name = "companyName")
	private String companyName;

	/**
	 * Default constructor
	 */
	public User_Functions_String_UserName() {
	}

	/*
	 * Standard Getters and Setters
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId_function() {
		return id_function;
	}

	public void setId_function(int id_function) {
		this.id_function = id_function;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public int getId_company() {
		return id_company;
	}

	public void setId_company(int id_company) {
		this.id_company = id_company;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "User_Functions_String_UserName [id=" + id + ", id_user=" + id_user + ", userName=" + userName
				+ ", id_function=" + id_function + ", functionName=" + functionName + ", id_company=" + id_company
				+ ", companyName=" + companyName + "]";
	}
}
