package com.ssd.dao.views;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.viewsDB.Reports_SD_Uploaded;
import com.ssd.entity.viewsDB.User_Functions_String_UserName;

/**
 * Class used to operation on view Reports_SD_Uploaded.
 * 
 * 
 * Public interface this for class: @see documentation of {@link Reports_SD_UploadedDAO}
 * ORM class with field descriptions: @see documentation of {@link Reports_SD_Uploaded}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 lut 2020 
 *
 */
public class Reports_SD_UploadedDAOImpl implements Reports_SD_UploadedDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;
	
	/**
	 * Method get list of view-objects Reports_SD_Uploaded by given values.
	 * Generally expected count of objects can be '0' or '1',
	 * because intention of this method is check, if report for given period and company
	 * has been uploaded or not yet uploaded.
	 *
	 * @param company_id int
	 * @param month int
	 * @param year int
	 * 
	 * @return list of objects Reports_SD_Uploaded; list can be empty
	 */
	@Override
	public List<Reports_SD_Uploaded> checkUploadedReport(int company_id, String month, String year) {
		
		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
		List<Reports_SD_Uploaded> list = new ArrayList<Reports_SD_Uploaded>();
		try {
			
			list = session.createQuery("from Reports_SD_Uploaded where company_id="+company_id+" and month='"+month+"' and year='"+year+"'").getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return list;
	}
	
	
	/**
	 * Method show all uploaded by user reports by date and company from last three months.
	 * To use this method, user must have added function 'Show_added_reports'. 
	 *
	 * @param userName of checking user
	 * 
	 * @return list of objects Reports_SD_Uploaded; list can be empty
	 */
	public List<Reports_SD_Uploaded> showUploadedReports(List<User_Functions_String_UserName> functionList){
		
		
		List<Reports_SD_Uploaded> list = new ArrayList<Reports_SD_Uploaded>();
		
		/* pobieram liste firm na podstwie funkcji */
		List<String> companies = new ArrayList<String>();
		List<String> companiesID = new ArrayList<String>();
		
		
		for(int i=0;i<functionList.size();i++) {
			companies.add("'"+functionList.get(i).getCompanyName()+"'");
		}
		
		String tempCompaniesString = companies.toString();
		tempCompaniesString=tempCompaniesString.substring(1,tempCompaniesString.length()-1);
				
		try {
			
			session = ConnectDB.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			List<Reports_SD_Uploaded> temp = new ArrayList<Reports_SD_Uploaded>();
			
			temp=this.session.createSQLQuery("select func_inc_var_session(0) as id, month, year, companyName, company_id, username, period count from Reports_SD_Uploaded where companyName in ("+tempCompaniesString+") and TIMESTAMPDIFF(MONTH, period,NOW() )<3").addEntity(Reports_SD_Uploaded.class).list();
			
			for(int i=0;i<temp.size();i++) {
				System.out.println(temp.get(i).toString());
			}
			
			session.getTransaction().commit();
			session.close();
			list.addAll(temp);
			
		} catch(Exception e) {
			
		}
		return list;
	}
}
