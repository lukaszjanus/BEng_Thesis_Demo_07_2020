package com.ssd.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Roles;

/**
 * Class used to get access to roles.
 * 
 * Public interface for this class: @see {@link RolesDAOImpl}
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
public class RolesDAOImpl implements RolesDAO {
	
	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	 private Session session=null;
	 
	 
	 /**
	  * Hibernate object to do transaction  by static SessionFactory
	  */
	 private Transaction transaction;

	/**
	 * Get role by given id.
	 *
	 * @param id int of role
	 * 
	 * @return object-role
	 */
	@Override
	public Roles getRoleById(int id) {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();
		Roles role = session.get(Roles.class, id);
		return role;
	}

	/**
	 * Method read all roles from db as list.
	 *
	 * @return objects-Roles List
	 */
	@Override
	public List<Roles> getAllRoles() {
		
		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		List<Roles> list;

		try {
			//session.beginTransaction();
			list = session.createQuery("from Roles").getResultList();
			session.getTransaction().commit();

			//System.out.println(list.get(1).getRoleName());

			for (int i = 0; i < list.size(); i++) {
				String temp = list.get(i).getRoleName().substring(5);
				list.get(i).setRoleName(temp);
				//System.out.println(list.get(i));
			}

		} finally {
			session.close();
		}

		return list;
	}

	/**
	 * Method read from db (Roles) column 'roleName' and delete prefix 'ROLE_'
	 *
	 * @return list-String roleName
	 */
	@Override
	public List<String> getAllRoleNamesString() {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		List<String> list;

		try {
			Criteria criteria = session.createCriteria(Roles.class);
			criteria.setProjection(Projections.property("roleName")); // Projections.property is used to retrieve
																		// specific columns
			list = criteria.list();

			for (int i = 0; i < list.size(); i++) {
				String temp = list.get(i).substring(5);
				list.set(i, temp);
				// System.out.println(list.get(i));
			}
			// System.out.println("roles: "+list);//Iterate list to show name
			session.getTransaction().commit();

		} finally {
			session.close();
		}
		return list;
	}

	/**
	 * Method read from db (Roles) only column 'roleName'.
	 *
	 * @return list-String roleName
	 */
	@Override
	public List<Roles> getAllRoleNames() {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		List<Roles> list;

		try {
			Criteria criteria = session.createCriteria(Roles.class);
			criteria.setProjection(Projections.property("roleName")); // Projections.property is used to retrieve
																		// specific columns
			list = criteria.list();
			session.getTransaction().commit();

		} finally {
			session.close();
		}
		return list;
	}
}
