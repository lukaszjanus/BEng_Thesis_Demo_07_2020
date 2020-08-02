package com.ssd.dao.views;

import java.util.List;

import com.ssd.entity.viewsDB.Reports_SD_Uploaded;
import com.ssd.entity.viewsDB.User_Functions_String_UserName;

/**
 * Interface used to operation on view Reports_SD_Uploaded.
 * 
 * 
 * Full class: @see documentation of {@link Reports_SD_UploadedDAOImpl}
 * ORM class with field descriptions: @see documentation of {@link Reports_SD_Uploaded}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 lut 2020 
 *
 */
public interface Reports_SD_UploadedDAO {

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
	public List<Reports_SD_Uploaded> checkUploadedReport(int company_id, String month, String year);
	
	
	/**
	 * Method show all uploaded by user reports by date and company.
	 * To use this method, user must have added function 'Show_added_reports'. 
	 *
	 * @param userName of checking user
	 * 
	 * @return list of objects Reports_SD_Uploaded; list can be empty
	 */
	public List<Reports_SD_Uploaded> showUploadedReports(List<User_Functions_String_UserName> functionList);

}