package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Default table with list of roles.
 * Now added only four roles:
 * ROLE_ADMIN
 * ROLE_MANAGER
 * ROLE_EMPLOYEE
 * ROLE_SETTINGS - default role for all users, used for active user withouth added any other role and any function. 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
@Entity
@Table(name = "roles")
public class Roles {
	
	/**
	 * Company-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	/**
	 * Name of role.
	 * Default format for Spring Security:
	 * 'ROLE_stringRoleName'
	 */
	@Column(name="role_name")
	private String roleName;
	
	public Roles() {}
	
	public Roles(String role) {
		this.roleName=role;
	}

	/** Default methods get-set*/
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	/**
	 * Default method to show object information.
	 */
	@Override
	public String toString() {
		return "Roles [id=" + id + ", roleName=" + roleName + "]";
	}
	
	
	

}
