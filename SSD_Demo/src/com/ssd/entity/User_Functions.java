package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Relation table with list of user-functions-company.
 * Values only as 'id'.
 * For this class was additionally created container class used for display information in string.
 * @see Documentation of {@link User_Function_String_Contener}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
@Entity
@Table(name = "user_functions")
public class User_Functions {

	/**
	 * Id of relation user-function-company, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * Foreign key of company.
	 * @see Documentation of {@link Company}
	 */
	@Column(name = "id_company")
	private int id_company;

	/**
	 * Foreing key of function.
	 * @see Documentation of {@link Functions} 
	 */
	@Column(name = "id_function")
	private int id_function;

	/**
	 * Foreign key of user.
	 * @see Documentation of {@link Users}
	 */
	@Column(name = "id_user")
	private int id_user;



	/**
	 * Default constructor.
	 */
	public User_Functions() {}
	
	
	/**
	 * Constructor without id.
	 * 
	 * @param id_company
	 * @param id_function
	 * @param id_user
	 */
	public User_Functions(int id_user, int id_company, int id_function ) {
		this.id_company = id_company;
		this.id_function = id_function;
		this.id_user = id_user;
	}
	
	/** Default methods get-set*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_company() {
		return id_company;
	}

	public void setId_company(int id_company) {
		this.id_company = id_company;
	}

	public int getId_function() {
		return id_function;
	}

	public void setId_function(int id_function) {
		this.id_function = id_function;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}


	/**
	 * Default method to show object information.
	 */
	@Override
	public String toString() {
		return "User_Functions [id=" + id + ", id_company=" + id_company + ", id_function=" + id_function + ", id_user="
				+ id_user + "]";
	}

}
