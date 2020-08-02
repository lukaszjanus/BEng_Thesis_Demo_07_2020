package com.ssd.dao;

import java.io.IOException;
import java.util.List;

import com.ssd.entity.Company;


/**
 * Public interface for class CompanyDAOImpl.
 * 
 * Class used to managing method of projects (companies).
 * All operations on DB are also added to Log-file.
 * 
 * Full code in class CompanyDAOImpl @see {@link CompanyDAOImpl}
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
public interface CompanyDAO {

	/**
	 * Method to get list of all Companies.
	 * 
	 * @return object-list Companies
	 */
	public List<Company> getCompanies();
	
	/**
	 * Method to get list of all active Companies.
	 * 
	 * @return object-list Companies
	 */
	public List<Company> getActiveCompanies();

	/**
	 * Method to get list of all disabled Companies.
	 * 
	 * @return object-list Companies
	 */
	public List<Company> getDisabledCompanies();
	
	/**
	 * Method look for object 'Company' by given Company Name (f.e. in form->input).
	 * 
	 * @param companyName as string
	 * @return object Companies
	 */
	public Company getCompanyByName(String companyName);

	/**
	 * Method look for object 'Company' by id.
	 * 
	 * @param company id
	 * 
	 * @return object Companies
	 */
	public Company getCompanyById(int id);
	
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
	 * @return informational number to controller (for generate cookie and
	 *         informational alert)
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int updadeCompany(Company company, String companyName, int type) throws SecurityException, IOException;

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
	public int saveNewCompany(Company company, int type) throws SecurityException, IOException;

	/**
	 * Method to check duplicates.
	 * 
	 * @param companies - original object
	 * @param company   - new name of company (from form->controller)
	 * @param type      - type of operation - changed if company-name is available
	 * 
	 * @return type '0' if duplicate, original value if not duplicate
	 */
	public int checkDuplicates(List<Company> companies, String companyName, int type);
	
}
