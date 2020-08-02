package com.ssd.dao;

import java.io.IOException;

import com.ssd.entity.Reports_Schema;

/**
 * 
 * Interface with definitions method of schemas to correctly upload reports:
 * - set definition
 * - show definition to edit
 * - read definition
 * 
 * Editing db is save in log-file.
 * 
 * Full code of methods for this class - @see {@link Reports_SchemaDAOImpl}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 14 lut 2020 
 *
 */
public interface Reports_SchemaDAO {

	/**
	 * Method read customization to correct upload file.
	 *
	 * @param company_id - to recognize, which company is currently upload.
	 *  
	 * @return simple object of Reports_Company_Schema
	 */
	public Reports_Schema getSchema(int company_id);

	/**
	 * Method used to create automatically new report schema after add new company.
	 * Values are defaults - before upload report, user should customize all values.
	 *
	 * @param companyName - int id of Company
	 */
	public void addSchemaForNewCompany(String companyName);

	/**
	 * Method update existing uploading schema. 
	 *
	 * @param schema  - object Reports_Schema with values from form
	 * 
	 * @throws IOException 
	 * @throws SecurityException
	 * 
	 * @return flag of result operation
	 */
	public int updateSchema(Reports_Schema schema) throws SecurityException, IOException;

}
