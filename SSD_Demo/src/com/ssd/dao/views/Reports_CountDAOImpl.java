package com.ssd.dao.views;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.viewsDB.Reports_Count;

/**
 * Class used to get data view from db with counts of reports.
 * 
 * Interface of this class: @see documentation of {@link Reports_CountDAO} ORM
 * class with field descriptions: @see documentation of {@link Reports_Count}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 1 mar 2020
 *
 */
public class Reports_CountDAOImpl implements Reports_CountDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method get data from temporary View of uploaded data with column 'count'.
	 *@param monthCount - list of months to display
	 *
	 * @return Reports_Count-object list
	 */
	@Override
	public List<Reports_Count> getFullTable(int monthCount) {
		
		List<Reports_Count> list = new ArrayList<Reports_Count>();

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		list = session.createSQLQuery("select Distinct *from Reports_Count WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"+monthCount).addEntity(Reports_Count.class).list();

		session.getTransaction().commit();
		session.close();

		return list;
	}
	
	/**
	 * Method prepare list for XLSX file with informations about general quantity of tickets from given range of months.
	 * Show full counts and companyNames group by and order by companyName
	 * 
	 * @param monthCount int
	 * 
	 * @return list-Object
	 */
	@Override
	public List<?> showCount(int monthCount) {
		System.out.println("reportsCount DAO - showCount ");
		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		List<?> list = session.createSQLQuery("select id, companyName, count(*) from Reports_Count WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"+monthCount+" GROUP BY companyName order by companyName").list();
		session.getTransaction().commit();
		session.close();
		
		//add new id number
		for (int i = 0; i < list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			row[0]=i+1;		 
		}
		return list;
	}
}