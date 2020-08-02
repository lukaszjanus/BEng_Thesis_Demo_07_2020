package com.ssd.dao;

import java.io.IOException;
import java.util.List;

import com.ssd.entity.Functions;


/**
 * Class used to managing functions.
 * 
 * Full code of methods - @see {@link FunctionsDAO}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
public interface FunctionsDAO {

	/**
	 * Method add new function (company name).
	 * 
	 * Type - int-flag to inform of result:
	 * - default '1' - add to db new company
	 * - 0 - duplicate
	 * - 5 - other errors
	 * 
	 * @param function 
	 * @param type - flag to check duplicates
	 * 
	 * @return flag to inform, that company has been added/not added to db 
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int saveNewFunction(Functions function, int type) throws SecurityException, IOException;

	/**
	 * Method check if any Company-Name is duplicated.
	 * Flag 'type' changed on '0' only, if duplicate will be found.
	 *
	 * @param functions - list of all Functions-object 
	 * @param fuctionName - name to check
	 * @param type - original flag
	 * 
	 * @return - flag (original or changed)
	 */
	public int checkDuplicates(List<Functions> functions, String fuctionName, int type);

	/**
	 * Objects-List of all functions.
	 *
	 * @return objects-list <Functions>
	 */
	public List<Functions> getFunctions();

	/**
	 * Objects-List of all active functions.
	 *
	 * @return objects-list <Functions>
	 */
	public List<Functions> getActiveFunctions();

	/**
	 * Objects-List of all disabled functions.
	 *
	 * @return objects-list <Functions>
	 */
	public List<Functions> getDisabledFunctions();

	/**
	 * Method look for object 'Function' by id.
	 * 
	 * @param function id
	 * 
	 * @return object Functions
	 */
	public Functions getFunctionsById(int id);

	/**
	 * Method look for object 'Functions' by given functionName (f.e. in form->input).
	 * 
	 * @param functionName as string 
	 * 
	 * @return object Functions
	 */
	public Functions getFunctionByName(String functionName);

	/**
	 * Method save new description of Function.
	 *
	 * @param function - original object Functions
	 * @param newDescription - flag with new description, or string-action to do: 'enabled' or 'disabled'
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void updateFunctionData(Functions function, String newDescription) throws SecurityException, IOException;

}
