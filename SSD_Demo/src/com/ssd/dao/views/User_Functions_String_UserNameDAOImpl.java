package com.ssd.dao.views;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.viewsDB.User_Functions_String_UserName;
import com.ssd.statics.Statics;

/**
 * Class used to operation on view User_Functions_String_UserName.
 * 
 * Table generate data to easier recognize id and stringName of users, companies, functions.
 * 
 * Full code of methods in class: @see documentation of
 * {@link User_Functions_String_UserNameDAO}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 19 sty 2020
 *
 */
public class User_Functions_String_UserNameDAOImpl implements User_Functions_String_UserNameDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method return view 'user_functions_string_username' as object-list.
	 *
	 * @return list
	 */
	@Override
	public List<User_Functions_String_UserName> getView() {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<User_Functions_String_UserName> list;
		try {
			list = session.createQuery("from User_Functions_String_UserName").getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Method check if user has 'Add_Report_Employee' function (one or more) and return object-list.
	 * In result-list controller can check, which companies are available for upload report by user.
	 *
	 * @param name of current user
	 * 
	 * @return object-list, can be empty
	 */
	@Override
	public List<User_Functions_String_UserName> getCompanies(String name) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		String functionName=Statics.getAdd_Report_Employee();
		
		List<User_Functions_String_UserName> list = new ArrayList<User_Functions_String_UserName>();

		try {

			list = session.createQuery("from User_Functions_String_UserName u where u.userName='" + name + "' and u.functionName='"+functionName+"'").getResultList();

			transaction.commit();
		} finally {
			session.close();

		}
		return list;
	}

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
	@Override
	public List<User_Functions_String_UserName> getFunctionsShow(String name) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		String functionName=Statics.getShow_Added_Reports_Employee();
		
		List<User_Functions_String_UserName> list = new ArrayList<User_Functions_String_UserName>();

		try {

			list = session.createQuery("from User_Functions_String_UserName u where u.userName='" + name + "' and u.functionName='"+functionName+"'").getResultList();

			transaction.commit();
		} finally {
			session.close();

		}
		return list;
	}

	/**
	 * To check - if not used - to delete.
	 * 
	 * If user has no any function, object User_Functions_String_UserName must set fields companyName and functionName with String 'Empty'. 
	 *
	 * @return object User_Functions_String_UserName
	 */
	@Override
	public User_Functions_String_UserName setObjectWithEmptyFunctions() {
		
		User_Functions_String_UserName user = new User_Functions_String_UserName();
		user.setCompanyName("Empty");
		user.setFunctionName("You have not added any function.");
		return user;
	}

	/**
	 * Show all user functions.
	 * 
	 * @param name of user
	 * @return object list
	 */
	public List<User_Functions_String_UserName> getFunctions(String name){
		
		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		List<User_Functions_String_UserName> list = new ArrayList<User_Functions_String_UserName>();

		try {

			list = session.createQuery("from User_Functions_String_UserName u where u.userName='" + name + "'").getResultList();

			transaction.commit();
		} finally {
			session.close();
		}
		return list;
	}
}
