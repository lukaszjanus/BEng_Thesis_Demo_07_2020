package com.ssd.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Functions;
import com.ssd.logs.Logs;

/**
 * Class used to managing functions.
 * 
 * Public interface - @see {@link FunctionsDAO}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
public class FunctionsDAOImpl implements FunctionsDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	 private Session session=null;
	 
	 
	 /**
	  * Hibernate object to do transaction  by static SessionFactory
	  */
	 private Transaction transaction;
	
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
	public int saveNewFunction(Functions function, int type) throws SecurityException, IOException {
		
		function.setEnabled(true);

		type = checkDuplicates(getFunctions(), function.getFunctionName(), type);

		switch (type) {
		case 0: {

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName() + " try add '"
					+ function.getFunctionName() + "' as function name, but this is already in use.", 1);

			return 0;
		}
		case 1: {

			session = ConnectDB.getSessionFactory().getCurrentSession(); 
			transaction = session.beginTransaction();

			function.setEnabled(true);
			session.save(function);
			session.getTransaction().commit();
			
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully added new function: " + function.getFunctionName() + ".", 0);

			return 1;
		}

		default: {
			// other error
			return 5;
		}
		}
	}

	
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
	@Override
	public int checkDuplicates(List<Functions> functions, String fuctionName, int type) {
		for (int i = 0; i < functions.size(); i++) {
			if (functions.get(i).getFunctionName().contentEquals(fuctionName)) {
				type = 0;
				break;
			}
		}
		return type;
	}

	/**
	 * Objects-List of all functions.
	 *
	 * @return objects-list <Functions>
	 */
	@Override
	public List<Functions> getFunctions() {

		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		List<Functions> functionList;
		try {
			functionList = session.createQuery("from Functions order by functionName").getResultList();
			transaction.commit();
		} finally {
			session.close();
			
		}
		return functionList;
	}
	
	/**
	 * Objects-List of all active functions.
	 *
	 * @return objects-list <Functions>
	 */
	@Override
	public List<Functions> getActiveFunctions() {
		
		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		List<Functions> functionsList;
		try {
			functionsList = session.createQuery("from Functions where enabled=1 order by functionName").getResultList();
			transaction.commit();
		} finally {
			session.close();
			
		}
		return functionsList;
	}
	
	/**
	 * Objects-List of all disabled functions.
	 *
	 * @return objects-list <Functions>
	 */
	@Override
	public List<Functions> getDisabledFunctions() {
		
		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		List<Functions> functionsList;
		try {
			//session.beginTransaction();
			functionsList = session.createQuery("from Functions where enabled=0 order by functionName").getResultList();
			transaction.commit();
		} finally {
			
			session.close();
		}

		return functionsList;
	}
	
	
	/**
	 * Method look for object 'Function' by id.
	 * 
	 * @param function id
	 * 
	 * @return object Functions
	 */
	@Override
	public Functions getFunctionsById(int id) {

		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		Functions function = new Functions();

		try {
			function = session.get(Functions.class, id);
			transaction.commit();
			
		} finally {
			
			session.close();
		}
		return function;
	}
	
	/**
	 * Method look for object 'Functions' by given functionName (f.e. in form->input).
	 * 
	 * @param functionName as string 
	 * 
	 * @return object Functions
	 */
	@Override
	public Functions getFunctionByName(String functionName) {

		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		Functions function = new Functions();

		List<Functions> functionList = new ArrayList<Functions>();
		try {
			functionList = session.createQuery("from Functions s where s.functionName='" + functionName + "'").getResultList();
			transaction.commit();
		} finally {
			session.close();
		}
		function = functionList.get(0);
		return function;
	}


	/**
	 * Method save new description of Function.
	 *
	 * @param function - original object Functions
	 * @param newDescription - flag with new description, or string-action to do: 'enabled' or 'disabled'
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public void updateFunctionData(Functions function, String newDescription) throws SecurityException, IOException {

		if(newDescription.equals("enable")) {
			/**
			 * Enable function
			 */
			session = ConnectDB.getSessionFactory().openSession(); 
			transaction = session.beginTransaction();

			//session.beginTransaction();
			function.setEnabled(true);
			session.update(function);
			transaction.commit();
			
			session.close();

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " has enabled function: '"+ function.getFunctionName()+ "'.", 0);
			
		}else if(newDescription.equals("disable")) {
			/**
			 * Disable function : delete all relations with this function
			 */
			
			User_FunctionsDAO functions = new User_FunctionsDAOImpl();
			functions.removeAllFunctionsOfDisabledFunction(function.getId(), function.getFunctionName());
			/**
			 * Disable function
			 */
			session = ConnectDB.getSessionFactory().openSession(); 
			transaction = session.beginTransaction();

			//session.beginTransaction();
			function.setEnabled(false);
			session.update(function);
			transaction.commit();
			session.close();
			
			
			
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " has disabled function: '"+ function.getFunctionName()+ "'.", 0);
			
		}else {
			/**
			 * change description
			 */
			String oldDescription = function.getDescription();

			session = ConnectDB.getSessionFactory().openSession();  
			transaction = session.beginTransaction();

			//session.beginTransaction();
			function.setDescription(newDescription);
			session.update(function);
			transaction.commit();
			session.close();
			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " succesfully updated description, old description: '"+oldDescription+"', new description: '" + function.getDescription()+ "'.", 0);
		}
		
	}
}
