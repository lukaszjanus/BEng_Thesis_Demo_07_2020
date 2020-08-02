package com.ssd.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Roles;
import com.ssd.entity.User_Roles;
import com.ssd.logs.Logs;

/**
 * Class used to manage relation:
 * 'user'-'role'
 * 
 * Public interface for this class: @see documentation of {@link User_RolesDAO}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 */
public class User_RolesDAOImpl implements User_RolesDAO {

	/** JDBC complex queries **/

	static Connection con;
	static PreparedStatement ps;
	
	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	 private Session session=null;
	 
	 
	 /**
	  * Hibernate object to do transaction  by static SessionFactory
	  */
	 private Transaction transaction;
	 
	/**
	 * Method show active roles of chosen user.
	 * Operation Substring(5)- Spring Security form db takes f.e. ROLE_ROLENAME, I cut part
	 * 'ROLE_'.
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	@Override
	public List<User_Roles> getUserRoles(String userName) {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();
		List<User_Roles> list;
		try {
			//session.beginTransaction();
			list = session.createQuery("from User_Roles u where u.userName='" + userName + "'").getResultList();
			session.getTransaction().commit();
			for (int i = 0; i < list.size(); i++) {
				String temp = list.get(i).getRole().substring(5);
				list.get(i).setRole(temp);
			}

		} finally {
			
		}
		return list;
	}

	/**
	 * Method show active roles of chosen user - without Substring(5) of column ROLE_ROLENAME
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	@Override
	public List<User_Roles> getAllRoleNames(String userName) {

		session = ConnectDB.getSessionFactory().openSession(); //zamiast get current 
		transaction = session.beginTransaction();
		//session.beginTransaction();
		
		List<User_Roles> list = new ArrayList<User_Roles>();
		try {
			list = session.createQuery("from User_Roles u where u.userName='" + userName + "'").getResultList();
			transaction.commit();
			
		} finally {
			session.close();
		}
		return list;
	}

	
	/**
	 * Delete role of chosen user - admin and manager form.
	 * This method take userName and roleName from form.
	 *
	 * @param userName choosen in form
	 * @param roleName choosen in form
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public void deleteRoleOfChosenUser(String userName, String roleName) throws SecurityException, IOException {
		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();
				
		//session.beginTransaction();
		Query query = session.createQuery("delete from User_Roles where userName=:user and role=:roleName");
		query.setParameter("user", userName);
		query.setParameter("roleName", roleName);
		query.executeUpdate();
		transaction.commit();
		session.close();
		Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
				+ " remove role '" +roleName+ "' from user: " + userName, 0);
	}
	
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
	@Override
	public void addRoleToUser(String userName, String roleName) throws SecurityException, IOException {
		
		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();
		User_Roles user_role = new User_Roles(userName,"ROLE_"+roleName);
		session.save(user_role);
		transaction.commit();
		session.close();
		Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
				+ " add role '" +roleName+ "' to user: " + userName, 0);
	}
	
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
	@Override
	public void addRoleAfterUserNameUpdate(String userName, String roleName) throws SecurityException, IOException {
		
		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();
		
		User_Roles user_role = new User_Roles(userName,roleName);
		session.save(user_role);
		session.getTransaction().commit();
	}
	
	/**
	 * List of roles, which can be add to user.
	 * This list not contains active uses's roles. 
	 *
	 * @param userName as String
	 * 
	 * @return List of objects User-Roles, list can be empty
	 */
	@Override
	public List<Roles> getNotActiveUserRole(String userName) {

		List<Roles> list = new ArrayList<Roles>();

		/* native sql-query used by Spring Hibernate */
		
		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();
	 
		list = this.session.createSQLQuery("select distinct *from Roles where role_name not in (SELECT DISTINCT role FROM user_roles WHERE username='"+userName+"')").addEntity(Roles.class).list();
		
		transaction.commit();
		session.close();
		
		/* remove prefix 'ROLE_' */
		
		for(int i=0;i<list.size();i++) {
			
			String a = list.get(i).getRoleName();
			a = a.substring(5);
			list.get(i).setRoleName(a);
		}
		return list;
	}
}
