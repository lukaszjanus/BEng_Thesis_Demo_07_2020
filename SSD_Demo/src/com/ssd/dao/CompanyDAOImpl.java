package com.ssd.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Company;
import com.ssd.logs.Logs;

/**
 * Class used to managing method of projects (companies). All operations on DB
 * are also added to Log-file.
 * 
 * Public interface for this class is in CompanyDAOImpl @see {@link CompanyDAO}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 14 sty 2020
 *
 */
public class CompanyDAOImpl implements CompanyDAO {
	
	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	 private Session session=null;
	 
	 
	 /**
	  * Hibernate object to do transaction  by static SessionFactory
	  */
	 private Transaction transaction;
	 

	/**
	 * Method to get list of all Companies.
	 * 
	 * @return object-list Companies
	 */
	@Override
	public List<Company> getCompanies() {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<Company> companyList;
		try {
			//session.beginTransaction();
			companyList = session.createQuery("from Company order by companyName").getResultList();
			session.getTransaction().commit();
		} finally {
			session.close();
			// 
		}
		return companyList;
	}

	/**
	 * Method to get list of all active Companies.
	 * 
	 * @return object-list Companies
	 */
	@Override
	public List<Company> getActiveCompanies() {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		List<Company> companyList;
		try {
			//session.beginTransaction();
			companyList = session.createQuery("from Company where enabled=1 order by companyName").getResultList();
			transaction.commit();
		} finally {
			
			session.close();
		}

		return companyList;
	}

	/**
	 * Method to get list of all disabled Companies.
	 * 
	 * @return object-list Companies
	 */
	@Override
	public List<Company> getDisabledCompanies() {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		List<Company> companyList;
		try {
			//session.beginTransaction();
			companyList = session.createQuery("from Company where enabled=0 order by companyName").getResultList();
			session.getTransaction().commit();
		} finally {
			
			// 
		}

		return companyList;
	}

	/**
	 * Method look for object 'Company' by given Company Name (f.e. in form->input).
	 * 
	 * @param companyName as string
	 * 
	 * @return object Companies
	 */
	@Override
	public Company getCompanyByName(String companyName) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		Company company = new Company();

		List<Company> companyList = new ArrayList<Company>();
		try {
			//session.beginTransaction();
			companyList = session.createQuery("from Company s where s.companyName='" + companyName + "'")
					.getResultList();

			session.getTransaction().commit();
		} finally {
			
			 
		}
		company = companyList.get(0);
		return company;
	}

	/**
	 * Method look for object 'Company' by id.
	 * 
	 * @param company id
	 * 
	 * @return object Companies
	 */
	@Override
	public Company getCompanyById(int id) {

		session = ConnectDB.getSessionFactory().openSession();
		transaction = session.beginTransaction();

		Company company = new Company();

		try {
			//session.beginTransaction();
			company = session.get(Company.class, id);
			transaction.commit();

		} finally {
			session.close();
			 
		}
		return company;
	}

	/**
	 * Method update information in DB about Company. Type - information from
	 * controller:
	 * 
	 * '0' - information for DAO, that name is duplicated. '1' - added new Company
	 * (in method CompanyDAOImpl) '2' - update companyName. '3' - disable company.
	 * '4' - enable company. '5' - other error (in case as 'default').
	 * 
	 * Method also check duplicates before save new name of company.
	 * 
	 * 
	 * @param company - object from controller
	 * @param companyName - string name of company from form
	 * @param type    of operation
	 * 
	 * 
	 * @return informational number to controller (for generate cookie and
	 *         informational alert)
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public int updadeCompany(Company company, String companyName, int type) throws SecurityException, IOException {

		if (type == 2) {
			type = checkDuplicates(getCompanies(), companyName, type);
		}

		switch (type) {
		case 0: {

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " try change name of '" + company.getCompanyName() + "' for new: '" + companyName
					+ "', but this name is already in use.", 1);

			return 0;
		}
		case 2: {

			String tempOldName = company.getCompanyName(); // for test

			/* get full information about object ('id' and 'enabled') */

			company.setCompanyName(companyName);
//			System.out.println("after change: "+company.toString() ); //roboczo

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			//session.beginTransaction();
			session.update(company);
			session.getTransaction().commit();
			 
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully changed companyName, old name: '" + tempOldName + "', new name: '" + companyName
					+ "'.", 0);

			return 2;

		}
		case 3: {
			// disable
			company.setEnabled(false);

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			session.update(company);
			session.getTransaction().commit();
			 
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully disabled company '" + company.getCompanyName() + "'.", 0);
			
			/** remove active relation user-functions for this company */
			User_FunctionsDAO function = new User_FunctionsDAOImpl();
			function.removeAllCompanyRelation(company.getId(), company.getCompanyName());
			
			return 3;
		}
		case 4: {
			// enable
			company.setEnabled(true);

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			
			//session.beginTransaction();
			session.update(company);
			session.getTransaction().commit();
			 
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully enabled company '" + company.getCompanyName() + "'.", 0);
			return 4;
		}
		default: {
			// other error
			return 5;
		}
		}
	}

	/**
	 * Method save in Data Base new Company (Project) and add information to logs.
	 * 
	 * Type - information from controller: '0' - information for DAO, that name is
	 * duplicated. '1' - information for DAO, that new name of Company has been
	 * added.
	 * 
	 * 
	 * As return also could be information with '5' means 'other error'.
	 * 
	 * Method also check duplicates before save new name of company.
	 * 
	 * update project (f.e. disable)
	 * 
	 * @param company - new company name from controller
	 * @param type    - type of operation in this method
	 * 
	 * @return number to controller - information, what type of cookie should be
	 *         generated
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public int saveNewCompany(Company company, int type) throws SecurityException, IOException {

		type = checkDuplicates(getCompanies(), company.getCompanyName(), type);

		switch (type) {
		case 0: {

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName() + " try add '"
					+ company.getCompanyName() + "' as new company, but this name is already in use.", 1);

			return 0;
		}
		case 1: {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			//session.beginTransaction();
			company.setEnabled(true);
			session.save(company);
			session.getTransaction().commit();

			Reports_SchemaDAO newSchema = new Reports_SchemaDAOImpl();
			newSchema.addSchemaForNewCompany(company.getCompanyName());
			
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully added new company: " + company.getCompanyName() + ".", 0);
			
			session.close();
			
			return 1;
		}

		default: {
			// other error
			return 5;
		}
		}
	}

	/**
	 * Method to check duplicates.
	 * 
	 * @param companies - original object
	 * @param company   - new name of company (from form->controller)
	 * @param type      - type of operation - changed if company-name is available
	 * 
	 * @return type '0' if duplicate, original value if not duplicate
	 */
	@Override
	public int checkDuplicates(List<Company> companies, String companyName, int type) {

		for (int i = 0; i < companies.size(); i++) {
			if (companies.get(i).getCompanyName().contentEquals(companyName)) {
				type = 0;
				break;
			}
		}
		return type;
	}
}
