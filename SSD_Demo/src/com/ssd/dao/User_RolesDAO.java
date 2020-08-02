package com.ssd.dao;


import java.io.IOException;
import java.util.List;

import com.ssd.entity.Roles;
import com.ssd.entity.User_Roles;

/**
 * Public interface - class used to manage relation:
 * 'user'-'role'
 * 
 * Full code of this interface - @see documentation of {@link User_RolesDAO}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
public interface User_RolesDAO {

	/**
	 * Method show active roles of chosen user.
	 * Operation Substring(5)- Spring Security form db takes f.e. ROLE_ROLENAME, I cut part
	 * 'ROLE_'.
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	public List<User_Roles> getUserRoles(String userName);

	/**
	 * Method show active roles of chosen user - without Substring(5) of column ROLE_ROLENAME
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	public List<User_Roles> getAllRoleNames(String userName);

	/**
	 * List of roles, which can be add to user.
	 * This list not contains active uses's roles. 
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	public List<Roles> getNotActiveUserRole(String userName);

	/**
	 * Delete role of chosen user - admin and manager form.
	 * This method take userName and roleName from form.
	 *
	 * @param userName chosen in form
	 * @param roleName chosen in form
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void deleteRoleOfChosenUser(String userName, String roleName) throws SecurityException, IOException;
	
	/**
	 * Add role to user - admin and manager form.
	 * This method take userName and roleName chosen in form.
	 * Method can add only one chose role - in form there is no possibility to multiple mark and add roles.
	 *
	 * @param userName chosen in form
	 * @param roleName chosen in form
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void addRoleToUser(String userName, String roleName) throws SecurityException, IOException;

	/**
	 * Add role to user without concatenation (without add "ROLE_" to roleName from parameter)
	 * This method is used when userName is updated (sting userName is a foreign key in table User_Roles.
	 * Method can add only one chosen role, used in loop without information in log,
	 * because roles earlier have been copied to temporary list, next all have been deleted.
	 * After this two operations in main table application can change userName and with this method add again the same roles. 
	 *
	 * @param userName chosen in form
	 * @param roleName taken from active user-roles
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void addRoleAfterUserNameUpdate(String userName, String roleName) throws SecurityException, IOException;
}
