package com.ssd.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class with information of Users - mapping table 'users' from DB (ORM Object).
 * All fields are with condition 'not null'.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
@Entity
@Table(name = "users")
public class Users {

	/**
	 * User-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * User login - unique field (index). Default format - 'firstName.lastName', but
	 * it is normal string - you can add/change in other format.
	 */
	@Column(name = "username")
	private String userName;

	/**
	 * First name of user.
	 */
	@Column(name = "first_name")
	private String firstName;

	/**
	 * Last name of user.
	 */
	@Column(name = "last_name")
	private String lastName;

	/**
	 * Password - string, max length - 68. Default password is hashed by bcrypt
	 * algorytm form Spring Security. From db manually you can add password in other
	 * algorithms used by Spring Security.
	 */
	@Column(name = "password")
	private String password;

	/**
	 * Field for check, if user is enable or disable. In db field is in format
	 * TINYINT and has length of 4. Values: 0 - user disabled 1 - user enabled
	 */
	@Column(name = "enabled")
	private boolean enabled;

	/**
	 * Time of creation user - not null. Field automatically write by db with
	 * date-time of user creation. Format in db: YYYY-MM-DD HH:MM:SS
	 */
	@Column(name = "created")
	private Timestamp created;

	/**
	 * Field with e-mail information - unique field (index). In db it is normal
	 * string; validation is on gui-level.
	 */
	@Column(name = "email")
	private String email;

	/**
	 * Default constructor.
	 */
	public Users() {
	}

	/**
	 * Constructor for new user - all fields without TimeStamp, wchih is add
	 * automatically with insert.
	 * 
	 * @param id
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param enabled
	 * @param created
	 * @param email
	 */
	public Users(int id, String userName, String firstName, String lastName, String password, boolean enabled,
			Timestamp created, String email) {
		// super();
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.enabled = enabled;
		this.created = created;
		this.email = email;
	}

	/**
	 * Get user id.
	 *
	 * @return id (int).
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set user id.
	 *
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get user login.
	 *
	 * @return userName (string).
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set user login.
	 *
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Get first name.
	 *
	 * @return firstName (string).
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set first name.
	 *
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get last name.
	 *
	 * @return lastName (string).
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set last name.
	 *
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get hashed password.
	 *
	 * @return password (string).
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set hashed password.
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get flag 'enabled'.
	 *
	 * @return enabled (boolean).
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Set flat 'enabled'.
	 *
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Get information about create time of user.
	 *
	 * @return created (Timestamp).
	 */
	public Timestamp getCreated() {
		return created;
	}

	/**
	 * Set time of created.
	 *
	 * @param created
	 */
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	/**
	 * Get user email.
	 *
	 * @return email (string).
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set e-mail.
	 *
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Default method to show object information as string.
	 */
	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", enabled=" + enabled + ", created=" + created + ", email=" + email + "]";
	}

}