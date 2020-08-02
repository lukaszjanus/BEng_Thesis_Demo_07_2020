package com.ssd.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Company;
import com.ssd.entity.Reports_Schema;
import com.ssd.logs.Logs;

/**
 * 
 * Class with definitions method of schemas to correctly upload reports: - set
 * definition - show definition to edit - read definition
 * 
 * Editing db is save in log-file.
 * 
 * Public interface for this class - @see {@link Reports_SchemaDAO}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 14 lut 2020
 *
 */
public class Reports_SchemaDAOImpl implements Reports_SchemaDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method read customization to correct upload file.
	 *
	 * @param company_id - to recognize, which company is currently upload.
	 * 
	 * @return simple object of Reports_Company_Schema
	 */
	@Override
	public Reports_Schema getSchema(int company_id) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		// search for first name or last name - case insensitive
		Query<Reports_Schema> query = session.createQuery("from Reports_Schema where company_id=:company_id",
				Reports_Schema.class);
		query.setParameter("company_id", company_id);

		List<Reports_Schema> schema = query.getResultList();

		session.getTransaction().commit();
		session.close();

		return schema.get(0);
	}

	/**
	 * Method used to create automatically new report schema after add new company.
	 * Values are defaults - before upload report, user should customize all values.
	 *
	 * @param companyName - int id of Company
	 */
	public void addSchemaForNewCompany(String companyName) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		CompanyDAO c = new CompanyDAOImpl();
		Company company = c.getCompanyByName(companyName);

		Reports_Schema schema = new Reports_Schema(company.getId());
		// search for first name or last name - case insensitive
		session.save(schema);

		session.getTransaction().commit();
		session.close();
	}

	/**
	 * Method update existing uploading schema.
	 *
	 * @param schema  - object Reports_Schema with values from view
	 * @throws IOException 
	 * @throws SecurityException
	 * 
	 * @return flag of result operation
	 */
	@Override
	public int updateSchema(Reports_Schema schema)  {

		int company_id=schema.getCompany_id();
		Reports_SchemaDAO schemaDAO = new Reports_SchemaDAOImpl();
		Reports_Schema oldSchema = schemaDAO.getSchema(company_id);
		
		/* for add old schema values to log */
		Reports_Schema tempSchema = oldSchema; 
		
		schema=updateSchemaValues(oldSchema, schema);
		
		int flag=0;
		
		/**
		 * Add log with new and old values of updated schema.
		 */
		try {
			
			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			//System.out.println("Update in dao: "+schema.toString());
			session.update(schema);
			
			CompanyDAO c = new CompanyDAOImpl();
			Company company = c.getCompanyById(company_id);
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName() + " has update schema for company: "+ company.getCompanyName()+".\nOld schema values:\n"+ tempSchema.toString()+" \nNew schema values:\n"	+ schema.toString(), 0);
			
			flag=0;
			
		} catch (SecurityException e) {
			flag=1;
			e.printStackTrace();
		} catch (IOException e) {
			flag=1;
			e.printStackTrace();
		}
		
		session.getTransaction().commit();
		session.close();
		
		return flag;
	}

	/**
	 * Support method to set new values in updated schema.
	 *
	 * @param oldSchema - old object Reports_Schema
	 * @param schema    - new object Reports_Schema with new values from view
	 * 
	 * @return object - Reports_Schema with updated values
	 */
	private Reports_Schema updateSchemaValues(Reports_Schema oldS, Reports_Schema newS) {

		newS.setId(oldS.getId());
		int prefixLength = newS.getPrefix().length();
		newS.setSubStringCount(prefixLength);
		return newS;
	}
}
