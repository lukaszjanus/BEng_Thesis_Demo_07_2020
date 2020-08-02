package com.ssd.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Company;
import com.ssd.entity.Functions;
import com.ssd.entity.User_Functions;
import com.ssd.entity.Users;
import com.ssd.entity.containers.User_Function_String_Contener;
import com.ssd.logs.Logs;

/**
 * Class used to operation on assigning to user functions and companies Public
 * interface this for class: @see documentation of {@link User_FunctionsDAOImpl}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
public class User_FunctionsDAOImpl implements User_FunctionsDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method to get list of all users and theirs functions.
	 *
	 * @return object-list of User_Functions
	 */
	@Override
	public List<User_Functions> getUserFunctions() {

		List<User_Functions> list;
		try {
			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			list = session.createQuery("from User_Functions").getResultList();
			transaction.commit();
		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Tool-Method to change id to string of Method-Functions. Method is needed to
	 * display values from table User_Functions.
	 *
	 * @return List of users as string name
	 */
	@Override
	public List<User_Function_String_Contener> getUserFunctionsString() {

		List<User_Function_String_Contener> list = new ArrayList<User_Function_String_Contener>();

		List<User_Functions> objectList = getUserFunctions();

		for (int i = 0; i < objectList.size(); i++) {

			UsersDAO u = new UsersDAOImpl();
			Users user = u.getUser(objectList.get(i).getId_user());

			FunctionsDAO f = new FunctionsDAOImpl();
			Functions func = f.getFunctionsById(objectList.get(i).getId_function());

			CompanyDAO c = new CompanyDAOImpl();
			Company comp = c.getCompanyById(objectList.get(i).getId_company());

			User_Function_String_Contener temp = new User_Function_String_Contener(objectList.get(i).getId(),
					user.getUserName(), comp.getCompanyName(), func.getFunctionName());

			list.add(temp);
		}
		return list;
	}

	/**
	 * Method get object User_Function_String_Contener and convert it to ORM
	 * User_Functions. If in form has been selected more, than one user, method also
	 * convert String userName to List of ORM objects User_Function.
	 * 
	 * @param userFunction object; field userName can be with one or more usernames,
	 *                     delimiter: ","
	 * 
	 * @return converted to objects-list User_Functions
	 */
	@Override
	public List<User_Functions> convertStringObjectToListId(User_Function_String_Contener userFunction) {

		String tempUserNames = userFunction.getUserName();

		String company = userFunction.getCompanyName();
		CompanyDAO comp = new CompanyDAOImpl();
		Company c = comp.getCompanyByName(company);
		int companyID = c.getId();

		String function = userFunction.getFunctionName();
		FunctionsDAO func = new FunctionsDAOImpl();
		Functions f = func.getFunctionByName(function);
		int functionID = f.getId();

		List<User_Functions> list = new ArrayList<User_Functions>();

		String[] ss = tempUserNames.split(",");
		for (int i = 0; i < ss.length; i++) {
			/**
			 * Get user id by userName
			 */
			UsersDAO user = new UsersDAOImpl();
			Users tempUser = user.getUserByUsername(ss[i]);
			int id = tempUser.getId();

			/**
			 * Create new User_Function by users -> add userId, companyId, functionId
			 */
			User_Functions u = new User_Functions(id, companyID, functionID);
			list.add(u);

		}
		return list;
	}

	/**
	 * Method add new relation: user-function-company. Flag: 1. Succesfully added
	 * all relations. 2. Duplicate when add one relation. 3. Duplicate (one or more)
	 * when add list of users.
	 * 
	 * 
	 * 
	 * @param list of objects User_Functions
	 * 
	 * @return flag with information about operation, used to generate cookie.
	 */
	@Override
	public int addRelationUser_Function(List<User_Functions> list) {

		int flag = 1;

		/* check duplicate */

		flag = checkDuplicateRelation(list);

		switch (flag) {
		case 0: {
			/* not used - moved to other method */
			// Logs log = new
			// Logs(SecurityContextHolder.getContext().getAuthentication().getName() + " try
			// add '"
			// + "test" + "' as company name, but this is already in use.", 1);

			return 0;
		}
		case 1: {
			for (int i = 0; i < list.size(); i++) {

				session = ConnectDB.getSessionFactory().openSession();
				transaction = session.beginTransaction();

				session.save(list.get(i));

				session.getTransaction().commit();
				try {
					Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
							+ " succesfully added function to user: " + list.get(i).toString() + ".", 0);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					session.close();
				}
			}

			return 1;
		}

		case 2: {

			try {
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try to add duplicated function to user: " + list.get(0).toString()
						+ ", but this relation has been added earlier.", 0);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 2;
		}

		case 3: {

			try {
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try to add function group of users. Relation for one or more users have been added earlier (duplicate).",
						0);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 3;
		}

		default: {
			// other error
			return 5;
		}
		}
	}

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
	@Override
	public int checkDuplicateRelation(List<User_Functions> list) {

		int size = list.size();

		if (size == 1) {

			int userID = list.get(0).getId_user();
			int functionID = list.get(0).getId_function();
			int companyID = list.get(0).getId_company();

			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// System.out.println(userID + " " + functionID + " " + companyID);

			Query<User_Functions> query = session.createQuery(
					"from User_Functions where id_user=:userID and id_function=:functionID and id_company=:companyID",
					User_Functions.class);
			query.setParameter("userID", userID);
			query.setParameter("functionID", functionID);
			query.setParameter("companyID", companyID);

			List<User_Functions> scoreList = query.getResultList();
			transaction.commit();

			session.close();

			if (scoreList.size() > 0) {
				/* duplicate found, change flag */
				return 2;
			}
		}
		if (size > 1) {

			for (int i = 0; i < size; i++) {

				int userID = list.get(i).getId_user();
				int functionID = list.get(i).getId_function();
				int companyID = list.get(i).getId_company();

				session = ConnectDB.getSessionFactory().openSession();
				transaction = session.beginTransaction();

				Query<User_Functions> query = session.createQuery(
						"from User_Functions where id_user=:userID and id_function=:functionID and id_company=:companyID",
						User_Functions.class);
				query.setParameter("userID", userID);
				query.setParameter("functionID", functionID);
				query.setParameter("companyID", companyID);

				List<User_Functions> scoreList = query.getResultList();
				transaction.commit();

				session.close();

				if (scoreList.size() > 1) {
					/* found duplicate in list */
					System.out.println(" break ");
					return 3;
				}
				System.out.println("petla: " + i);
			}
		}

		/* default value of flag 'add relation' - no duplicates */
		return 1;
	}

	/**
	 * not finished, not used - to delete
	 *
	 * @param object
	 * @return
	 */
	private boolean checkDuplicates(User_Functions object) {

		boolean flag = true;
		List<User_Functions> list = getUserFunctions();
		for (int i = 0; i < list.size(); i++) {
			//System.out.println(" petla checkDuplicates " + flag);
			int a = object.hashCode();
			int b = list.get(i).hashCode();

			if (a == b) {
				flag = false;
				//System.out.println(" zmiana flagi" + flag);
				break;
			}
		}
		return flag;
	}

	/**
	 * Method remove relation from User_Functions db table by given 'id'.
	 *
	 * @param userName as string form's parameter
	 * @param list     - String data to add information to log
	 * 
	 * @return flag (success or not) -> to generate cookie
	 */
	@Override
	public boolean removeRelation(int id, List<String> list) {

		boolean flag = true;

		try {

			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			Query query = session.createQuery("delete from User_Functions where id=:functionID");
			query.setParameter("functionID", id);

			query.executeUpdate();

			session.getTransaction().commit();
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully remove from user: '" + list.get(0) + "' function: '" + list.get(2)
					+ "' used with company: '" + list.get(1) + "'.", 0);

		} catch (SecurityException | IOException e) {
			flag = false;
			// e.printStackTrace();
		} finally {
			// factory.close();
			session.close();
		}

		return flag;
	}

	/**
	 * Method delete all active functions of given user-id. Value userName is used
	 * to information added to log.
	 * 
	 * @param id       int user-id
	 * @param userName string-userName
	 */
	@Override
	public void removeAllFunctionsFromUser(int id, String userName) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createQuery("delete from User_Functions where id_user=:userID");
		query.setParameter("userID", id);
		query.executeUpdate();
		transaction.commit();
		session.close();

		try {
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully remove all functions from user: '" + userName + "' (in case of 'disable user').",
					0);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method delete all relation with given CompanyID. companyName used only to add
	 * information to log.
	 *
	 * @param id          int company-Id
	 * @param companyName string company-Name
	 */
	public void removeAllCompanyRelation(int companyId, String companyName) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createQuery("delete from User_Functions where id_company=:companyId");
		query.setParameter("companyId", companyId);
		query.executeUpdate();
		transaction.commit();
		session.close();

		try {
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully remove all functions of company: '" + companyName
					+ "' (in case of 'disable company').", 0);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method delete all relation with given functionID. functionName used only to
	 * add information to log.
	 *
	 * @param id
	 * @param functionName
	 */
	@Override
	public void removeAllFunctionsOfDisabledFunction(int functionId, String functionName) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		Query query = session.createQuery("delete from User_Functions where id_function=:functionId");
		query.setParameter("functionId", functionId);
		query.executeUpdate();
		transaction.commit();
		session.close();

		try {
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully remove all functions of company: '" + functionName
					+ "' (in case of 'disable function').", 0);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}
}
