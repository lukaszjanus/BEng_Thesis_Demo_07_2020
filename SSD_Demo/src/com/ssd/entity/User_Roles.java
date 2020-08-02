package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class with relation of userName and Roles. All fields with condition 'not
 * null'.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
@Entity
@Table(name = "user_roles")
public class User_Roles {

	/**
	 * Id of relation user-roles, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * UserName - foreign key.
	 * @see Documentation of {@link Users}
	 */
	@Column(name = "username")
	private String userName;

	/**
	 * Role - foreign key.
	 * @see Documentation of {@link Roles}
	 */
	@Column(name = "role")
	private String role;

	/**
	 * Default constructor.
	 */
	public User_Roles() {
	}

	/**
	 * Constructor only with fields 'role'.
	 * 
	 * @param role
	 */
	public User_Roles(String role) {
		this.role = role;
	}

	/**
	 * Full constructor with userName and name of role.
	 * 
	 * @param userName
	 * @param role
	 */
	public User_Roles(String userName, String role) {

		this.userName = userName;
		this.role = role;
	}

	/**
	 * Get login.
	 *
	 * @return string login
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set login as string.
	 *
	 * @param userName given in forms
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get role name.
	 *
	 * @return string role name. 
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Set role name as string.
	 *
	 * @param role from forms.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Get id of user_roles (primary key).
	 *
	 * @return int id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set id - from forms.
	 *
	 * @param id as int
	 */
	public void setId(int id) {
		this.id = id;
	}

}
