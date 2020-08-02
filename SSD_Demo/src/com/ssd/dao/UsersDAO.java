package com.ssd.dao;

import java.io.IOException;
import java.util.List;
import com.ssd.entity.Users;

/**
 * Class to manage users.
 * 
 * Public interface for class UsersDAOImpl.
 * 
 * Full code - @see documentation of {@link UsersDAOImpl}
 * 
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
public interface UsersDAO {

	/**
	 * Method generate list of all users.
	 *
	 * @return usersList - object list of class Users.
	 */
	public List<Users> getUsers();

	/**
	 * Method return user (object) by given id.
	 *
	 * @param theId - id of user
	 * 
	 * @return user as object
	 */
	public Users getUser(int theId);

	/**
	 * Method update user in db from forms: - 'update user' - 'disable user' -
	 * 'enable user' Parametr 'type' this is instruction about type of operation. 0
	 * - save user; 1 - enable user; 2 - disable user;
	 * 
	 * @param user - object from form
	 * @param type - flag
	 * 
	 * @return flag int
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int saveUser(Users user, int type) throws SecurityException, IOException;

	/**
	 * Method add new user to DB.
	 * 
	 * 
	 * @param user with data from form
	 * 
	 * @return flag (0-added, 1-duplicated userName, 2-duplicated e-mail)
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int saveNewUser(Users user) throws SecurityException, IOException;

	/**
	 * Method select check user by userName.
	 *
	 * @param name - string userName
	 * 
	 * @return - object Users
	 */
	public Users getUserByUsername(String name);

	/**
	 * Method to update password of choosen user by Admin or Manager.
	 * Update user password:
	 * 0 - update user own password
	 * 1 - update by
	 * admin/manager for user chosen from list
	 *
	 * @param name - sting userName of user
	 * @param password - new password (hashed)
	 * @param i - flag to check, who change password
	 *  
	 * @throws SecurityException
	 * @throws IOException
	 */
	void updatePassword(String name, String password, int i) throws SecurityException, IOException;

	/**
	 * Method show only active users as object-list.
	 *
	 * @return Users-object list. 
	 */
	public List<Users> getActiveUsers();

	/**
	 * Method show only disabled users as object-list.
	 *
	 * @return Users-object list. 
	 */
	public List<Users> getDisabledUsers();

	/**
	 * Method used to disable or enable user (change flag 'enabled' in users trable)
	 *
	 * @param user to modify
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void changeUserEnableStatus(Users user) throws SecurityException, IOException;
}
