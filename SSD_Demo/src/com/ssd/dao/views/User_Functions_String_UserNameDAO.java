package com.ssd.dao.views;

import java.util.List;

import com.ssd.entity.viewsDB.User_Functions_String_UserName;


/**
 *  Interface used to operation on view User_Functions_String_UserName.
 * 
 * Table generate data to easier recognize id and stringName of users, companies, functions.
 * 
 * Public interface this for class: @see documentation of {@link User_Functions_String_UserNameDAOImpl}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 19 sty 2020 
 *
 */
public interface User_Functions_String_UserNameDAO {

	/**
	 * Method return view 'user_functions_string_username' as object-list.
	 *
	 * @return list
	 */
	public List<User_Functions_String_UserName> getView();

	/**
	 * Method return object-list with companies available for user with function:
	 * 'Add_Report_Employee'
	 *
	 * @param name of current user
	 * 
	 * @return object-list
	 */
	public List<User_Functions_String_UserName> getCompanies(String name);

	/**
	 * Method check if user has 'Show_Added_Reports_Employee' function (one or more) and return object-list.
	 * In result-list controller can check:
	 * - available functions (can be null) 
	 * - available companies
	 * Controller for companies with this function generate list of uploaded earlier reports with: period, companyName and 'upload by + userName'. 
	 *
	 * @param name of current user
	 * 
	 * @return object-list, can be empty
	 */
	List<User_Functions_String_UserName> getFunctionsShow(String name);
	
	/**
	 * If user has no any function, object User_Functions_String_UserName must set fields companyName and functionName with String 'Empty'. 
	 *
	 * @return object User_Functions_String_UserName
	 */
	public User_Functions_String_UserName setObjectWithEmptyFunctions();

	/**
	 * Show all user functions.
	 * 
	 * @param name of user
	 * @return object list
	 */
	public List<User_Functions_String_UserName> getFunctions(String name);
}
