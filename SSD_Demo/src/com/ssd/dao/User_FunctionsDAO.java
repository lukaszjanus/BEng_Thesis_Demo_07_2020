package com.ssd.dao;

import java.util.List;

import com.ssd.entity.User_Functions;
import com.ssd.entity.containers.User_Function_String_Contener;


/**
 * Public interface for class: User_FunctionsDAOImpl
 * 
 * Class used to operation on assigning to user functions and companies.
 * 
 * Full code - @see documentation of {@link User_Functions}
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
public interface User_FunctionsDAO {

	/**
	 * Method to get list of all users and theirs functions.
	 *
	 * @return object-list of User_Functions
	 */
	public List<User_Functions> getUserFunctions();

	/**
	 * Tool-Method to change id to string of Method-Functions.
	 * Method is needed to display values from table  User_Functions.
	 *
	 * @return List of users as string name 
	 */
	public List<User_Function_String_Contener> getUserFunctionsString();

	/**
	 * Method get object User_Function_String_Contener and convert it to ORM User_Functions.
	 * If in form has been selected more, than one user, method also convert String userName to List of ORM objects User_Function.
	 * 
	 * @param userFunction object; field userName can be with one or more usernames, delimiter: ", "
	 * 
	 * @return converted to objects-list User_Functions
	 */
	public List<User_Functions> convertStringObjectToListId(User_Function_String_Contener userFunction);

	/**
	 * Method add new relation: user-function-company.
	 * 
	 *
	 * @param list of objects User_Functions
	 * 
	 * @return flag with information about operation, used to generate cookie.
	 */
	public int addRelationUser_Function(List<User_Functions> list);


	/**
	 * Method remove relation from User_Functions db table by given 'id'. 
	 *
	 * @param userName as string form's parameter
	 * @param list - String data to add information to log
	 * 
	 * @return flag (success or not) -> to generate cookie
	 */
	public boolean removeRelation(int id, List<String> list);

	/**
	 * Method delete all active functions of given user-id.
	 * Value userName is used to information added to log.
	 * 
	 * @param id
	 * @param userName 
	 */
	public void removeAllFunctionsFromUser(int id, String userName);

	/**
	 * Method delete all relation with given CompanyID.
	 * companyName used only to add information to log.
	 *
	 * @param id int company-Id
	 * @param companyName string company-Name
	 */
	public void removeAllCompanyRelation(int companyId, String companyName);

	/**
	 * Method delete all relation with given functionID.
	 * functionName used only to add information to log.
	 *
	 * @param id
	 * @param functionName
	 */
	public void removeAllFunctionsOfDisabledFunction(int functionId, String functionName);

	/**
	 * Method check duplicated relation. If no duplicates, return flag=1 (no change
	 * flag). If list.size is one, and duplicate has found, return flag=2. If
	 * list.size is greater than one and duplicates have found, method return
	 * flag=2.
	 *
	 * @param list of objects User_Functions
	 * 
	 * @return int flag
	 */
	public int checkDuplicateRelation(List<User_Functions> list);

}
