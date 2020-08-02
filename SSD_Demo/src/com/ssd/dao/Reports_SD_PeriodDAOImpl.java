package com.ssd.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Reports_Period;

public class Reports_SD_PeriodDAOImpl implements Reports_SD_PeriodDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method save new period of added report.
	 * 
	 * @param month - as String
	 * @param year  - as String
	 */
	@Override
	public void addPeriod(String month, String year) {
		
		
		Reports_Period period = new Reports_Period(month, year);
		
		
		session = ConnectDB.getSessionFactory().openSession();
		
		transaction = session.beginTransaction();

		session.save(period);
		session.getTransaction().commit();
		session.close();
	}

	
	/**
	 * Method return id of month-year period.
	 * Used f.e. before add new position to db.
	 *
	 * @param month String
	 * @param year String
	 * 
	 * @return int id
	 */
	@Override
	public int getPeriodId(String month, String year) {
		
		List<Reports_Period> result=new ArrayList<Reports_Period>();
		
		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

				
		Query<Reports_Period> query = session.createQuery(
				"from Reports_Period where month=:monthS and year=:yearS", Reports_Period.class);
	
		query.setMaxResults(1);
		query.setParameter("monthS", month);
		query.setParameter("yearS", year);
		result=query.getResultList();
		
		int id=0;
		if(result.size()>0) {
			id=result.get(0).getId();
		}
		
		session.getTransaction().commit();
		session.close();
		
		return id;
	}

}
