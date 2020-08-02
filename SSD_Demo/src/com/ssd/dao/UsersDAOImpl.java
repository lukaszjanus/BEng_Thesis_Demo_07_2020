package com.ssd.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.User_Roles;
import com.ssd.entity.Users;
import com.ssd.logs.Logs;

/**
 * Class to manage users.
 * 
 * Public interface for this class - @see documentation of {@link UsersDAOI}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
@Repository
public class UsersDAOImpl implements UsersDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method generate list of all users.
	 *
	 * @return usersList - object list of class Users.
	 */
	@Override
	public List<Users> getUsers() {

		List<Users> usersList = null;

		try {

			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			// usersList = session.createQuery("from Users").getResultList(); //different
			// version, the same result
			usersList = session.createCriteria(Users.class).list();
			transaction.commit();

		} catch (Exception e) {
			System.out.println("error");
		} finally {
			session.close();
		}
		return usersList;
	}

	/**
	 * Method return user (object) by given id.
	 *
	 * @param theId - id of user
	 * 
	 * @return user as object
	 */
	@Override
	public Users getUser(int theId) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		// session.beginTransaction();
		Users user = session.get(Users.class, theId);
		transaction.commit();
		session.close();

		return user;
	}

	/**
	 * Method update user in db from forms: - 'update user' - 'disable user' -
	 * 'enable user' Parametr 'type' this is instruction about type of operation. 0
	 * - save user; 1 - enable user; 2 - disable user;
	 * 
	 * When user is disable, from db automatically roles and functions have been
	 * deleted.
	 * 
	 * @param user - object from form
	 * @param type - flag
	 * 
	 * @return flag int
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public int saveUser(Users user, int type) throws SecurityException, IOException {
		// System.out.println("user nowy: " + user.getId());

		User_RolesDAO actualRolesGet = new User_RolesDAOImpl();
		List<Users> checkList = getUsers();

		/**
		 * Prepare list to check duplicates - remove current user from this list
		 */
		for (int i = 0; i < checkList.size(); i++) {
			if (checkList.get(i).getId() == user.getId()) {
				checkList.remove(i);
				break;
			}
		}

		int flag = 0;
		/**
		 * Temporary user with old login.
		 */
		UsersDAO getUser = new UsersDAOImpl();

		Users userBeforeUpdate = new Users();
		userBeforeUpdate = getUser.getUser(user.getId());

		/**
		 * if user is disabled from 'edit' form - actually this option removed from
		 * 'edit' form
		 */
		for (int i = 0; i < checkList.size(); i++) {
			String checkUserName = checkList.get(i).getUserName();
			if (checkUserName.equals(user.getUserName())) {
				flag = 1;

				/**
				 * duplicate username
				 */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try update User but userName " + user.getUserName() + " is duplicated", 1);
				break;
			}
			/**
			 * duplicate email
			 */
			String checkEmail = checkList.get(i).getEmail();
			if (checkEmail.equals(user.getEmail())) {
				flag = 2;
				System.out.println("FLAG 2 ------------------------------------");
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try updated User but email " + user.getEmail() + " is duplicated", 1);
				break;
			}
		}

		if (type == 1) {
			/** 
			 * add default roles when user is enable
			 */

			User_RolesDAOImpl rolesToAdd = new User_RolesDAOImpl();
			rolesToAdd.addRoleToUser(user.getUserName(), "Settings");

		}

		if (type == 2) {
			/**
			 * delete roles when user has been disabled
			 */
			User_RolesDAO activeRoles = new User_RolesDAOImpl();
			
			List<String> list = new ArrayList<>();
			for (int i = 0; i < activeRoles.getAllRoleNames(user.getUserName()).size(); i++) {
				String temp = activeRoles.getAllRoleNames(user.getUserName()).get(i).getRole().substring(5);
				list.add(temp);
			}

			for (int i = 0; i < list.size(); i++) {
				activeRoles.deleteRoleOfChosenUser(user.getUserName(), "ROLE_" + list.get(i));
			} 

			/**
			 * delete functions of this user
			 */
			User_FunctionsDAO functionsToDelete = new User_FunctionsDAOImpl();
			functionsToDelete.removeAllFunctionsFromUser(user.getId(), user.getUserName());

		}

		boolean loginUpdateFlag = user.getUserName() != userBeforeUpdate.getUserName();
		// System.out.println(loginUpdateFlag);
		List<User_Roles> list = new ArrayList<User_Roles>();

		if (loginUpdateFlag == true) {
			list = actualRolesGet.getAllRoleNames(userBeforeUpdate.getUserName());
			deleteAllUserRoles(userBeforeUpdate.getUserName());
		}

		if (flag == 0) {
			System.out.println("before session");
			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			try {

				/**
				 * If update user and actual password is taken from db - password is again
				 * hashed: - from form 'user' has got hashed password - hashed password was
				 * hashed again In form it has created new controller - only for change
				 * password. Here system taken hashed password before change and set it again
				 * without second hashed.
				 */
				user.setPassword(userBeforeUpdate.getPassword());
				user.setCreated(userBeforeUpdate.getCreated());
	
				session.update(user);
				session.getTransaction().commit();

				/** add roles again from list - in case of userName update */
				if (loginUpdateFlag == true && type!=1) {
					System.out.println("if START");
					for (int i = 0; i < list.size(); i++) {
						actualRolesGet.addRoleAfterUserNameUpdate(user.getUserName(), list.get(i).getRole());
					}
				}

				switch (type) {
				case 0: {

					/** save updated user */
					Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
							+ " update account of user: " + user.toString(), 0);

					break;
				}
				case 1: {
					/** enable user */
					Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
							+ " enable account of " + user.toString(), 0);

					break;
				}
				case 2: {
					/** disable user */
					Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
							+ " disable account of " + user.toString(), 0);

					break;
				}
				default: {

					Logs log = new Logs(
							SecurityContextHolder.getContext().getAuthentication().getName() + " try update "
									+ user.toString() + ", but operation has not finished correctly - other error",
							1);
					break;
				}
				}
			} finally {

				// factory.close();
			}

		}
		return flag;
	}

	/**
	 * not used
	 * 
	 * Method used to disable or enable user (change flag 'enabled' in users trable)
	 *
	 * @param user to modify
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public void changeUserEnableStatus(Users user) throws SecurityException, IOException {

		// session = ConnectDB.getSessionFactory().getCurrentSession();
		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		/* with 'transaction' java show error 'transaction is active' */

		session.update(user);
		transaction.commit();
		session.close();
		// used to save enable and disable
		Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName() + " enable account of "
				+ user.toString(), 0);
	}

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
	@Override
	public int saveNewUser(Users user) throws SecurityException, IOException {

		List<Users> checkList = getUsers();
		int flag = 0;

		flag = duplicatesPrevent(user, checkList, flag);

		if (flag == 0) {
			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			try {
				//System.out.println("Select users from DB");

				session.save(user);
				session.getTransaction().commit();

				// add log with successfuly added user
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " has been added new user: " + user.toString(), 0);

				User_RolesDAO addRoles = new User_RolesDAOImpl();
				addRoles.addRoleToUser(user.getUserName(), "SETTINGS");
				addRoles.addRoleToUser(user.getUserName(), "EMPLOYEE");

			} finally {

				session.close();
			}
		}
		return flag;
	}

	/**
	 * Tool-Method: - check if new/updated user has the same user.name or email.
	 * 
	 * 
	 * @param user      - oryginal object
	 * @param checkList - actual Users-list
	 * @param flag      - type of operation
	 * 
	 * @return int flag (0-added, 1-duplicated userName, 2-duplicated e-mail)
	 * 
	 * @throws IOException
	 */
	private int duplicatesPrevent(Users user, List<Users> checkList, int flag) throws IOException {
		for (int i = 0; i < checkList.size(); i++) {

			String checkUserName = checkList.get(i).getUserName();
			if (checkUserName.equals(user.getUserName())) {
				flag = 1;

				// duplicate username
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try added new User but userName " + user.getUserName() + " is duplicated", 1);
				break;
			}
			String checkEmail = checkList.get(i).getEmail();
			if (checkEmail.equals(user.getEmail())) {
				flag = 2;
				System.out.println("FLAG 2 ------------------------------------");
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try added new User but email " + user.getEmail() + " is duplicated", 1);
				break;
			}
		}
		return flag;
	}

	/**
	 * Method select check user by userName.
	 *
	 * @param name - string userName
	 * 
	 * @return - object Users
	 */
	@Override
	public Users getUserByUsername(String userName) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		Query<Users> query = session.createQuery("from Users where userName =:name", Users.class);
		query.setParameter("name", userName);

		List<Users> users = query.getResultList();
		transaction.commit();

		Users user = users.get(0);

		session.close();

		return user;
	}

	/**
	 * Method to update password of choosen user by Admin or Manager. Update user
	 * password: 0 - update user own password 1 - update by admin/manager for user
	 * chosen from list
	 *
	 * @param name     - sting userName of user
	 * @param password - new password (hashed)
	 * @param i        - flag to check, who change password
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public void updatePassword(String name, String password, int i) throws SecurityException, IOException {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
		Query query = null;

		try {
			query = session.createQuery("UPDATE Users SET password =:pass WHERE userName =:Name");
			query.setParameter("pass", password);
			query.setParameter("Name", name);
			query.executeUpdate();
			transaction.commit();

			switch (i) {
			case 0: {
				Logs log = new Logs(
						SecurityContextHolder.getContext().getAuthentication().getName() + " has changed own password",
						0);
				break;
			}
			case 1: {
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " has changed password for " + name, 0);
				break;
			}

			}
			// session.getTransaction().commit();
		} finally {

			// factory.close();
		}
	}

	/**
	 * Method show only active users as object-list.
	 *
	 * @return Users-object list.
	 */
	@Override
	public List<Users> getActiveUsers() {

		List<Users> usersList = getUsers();

		for (int i = usersList.size() - 1; i >= 0; i--) {

			if (usersList.get(i).isEnabled() == false) {
				usersList.remove(i);
			}
		}
		return usersList;
	}

	/**
	 * Method show only disabled users as object-list.
	 *
	 * @return Users-object list.
	 */
	@Override
	public List<Users> getDisabledUsers() {

		List<Users> usersList = null;

		try {
			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			// usersList = session.createQuery("from Users").getResultList();
			usersList = session.createCriteria(Users.class).list();
			transaction.commit();

		} catch (Exception e) {
			System.out.println("disabled wyjatek ");
		} finally {
			session.close();
		}

		for (int i = usersList.size() - 1; i >= 0; i--) {

			if (usersList.get(i).isEnabled() == true) {
				usersList.remove(i);
			}
		}
		return usersList;
	}

	/**
	 * Method look for all active roles of user (by Strin userName) and deleted by
	 * query.
	 * 
	 * @param userName
	 * 
	 */
	private void deleteAllUserRoles(String userName) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		// session.beginTransaction();
		Query query = session.createQuery("delete from User_Roles where userName=:user");
		query.setParameter("user", userName);
		query.executeUpdate();
		session.getTransaction().commit();
	}
}
