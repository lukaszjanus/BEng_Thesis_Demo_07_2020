package com.ssd.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.Menu_Options;
import com.ssd.entity.User_Roles;
import com.ssd.logs.Logs;
import com.ssd.statics.Statics;


/**
 * Class with definitions of method to:
 * - generate dynamic menu
 * - manage menu-options
 * 
 * Editing db is save in log-file.
 * 
 * Public interface for this class - @see {@link Menu_OptionsDAO}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
public class Menu_OptionsDAOImpl implements Menu_OptionsDAO {
	
	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	 private Session session=null;
	 
	 
	 /**
	  * Hibernate object to do transaction  by static SessionFactory
	  */
	 private Transaction transaction;

	
	/**
	 * Object with static data of application. Here used to check type of options: TOP(top-bar), SUB(sub-menu), OPT(option)
	 */
	public Statics stat;
	
	/**
	 * Method generate objects-List with all Options.
	 *
	 * @return objects-list
	 */
	@Override
	public List<Menu_Options> showMenu() {

		session = ConnectDB.getSessionFactory().openSession(); 
		transaction = session.beginTransaction();

		List<Menu_Options> list;

		try {
			//session.beginTransaction();
			list = session.createQuery("from Menu_Options").getResultList();
			transaction.commit();

		} finally {
			session.close();
		}

		Collections.sort(list, new SortObjectList());

		return list;
	}

	/**
	 * Method to get menuItem by primary key - 'id'.
	 * 
	 * @param id
	 * 
	 * @return object of class Menu_Options
	 */
	@Override
	public Menu_Options getMenuItemById(int id) {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		Menu_Options item;

		try {
			//session.beginTransaction();

			item = session.get(Menu_Options.class, id);

			session.getTransaction().commit();

		} finally {
			
			//factory.close();
		}

		return item;
	}

	/**
	 * Method generate objects-List only used only as Top Bar of Menu.
	 *
	 * @return objects-list
	 */
	@Override
	public List<Menu_Options> showMenuTop() {

		List<Menu_Options> menuList = new ArrayList<Menu_Options>();
		menuList = showMenu();

		// temporary array
		List<Menu_Options> topMenu = new ArrayList<Menu_Options>();

		User_RolesDAO userRoles = new User_RolesDAOImpl();
		List<User_Roles> list = userRoles
				.getAllRoleNames(SecurityContextHolder.getContext().getAuthentication().getName());

		// active rools
		List<String> list_roles = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {

			list_roles.add(i, list.get(i).getRole());

			// menu - top
			for (int j = 0; j < menuList.size(); j++) {
				if (menuList.get(j).getRoleName().equals(list_roles.get(i))
						&& menuList.get(j).getType().equals(Statics.getMenuTop())) {
					Menu_Options temp = menuList.get(j);
					topMenu.add(temp);
				}
			}
		}
		
		return topMenu;
	}

	/**
	 * Method generate objects-List only used only as submenus of Menu.
	 *
	 * @return objects-list
	 */
	@Override
	public List<Menu_Options> showMenOpt() {

		List<Menu_Options> menuList = new ArrayList();
		menuList = showMenu();

		// temporary array
		List<Menu_Options> optMenu = new ArrayList();

		User_RolesDAO userRoles = new User_RolesDAOImpl();
		List<User_Roles> list = userRoles
				.getAllRoleNames(SecurityContextHolder.getContext().getAuthentication().getName());

		// active rools
		List<String> list_roles = new ArrayList();

		for (int i = 0; i < list.size(); i++) {

			list_roles.add(i, list.get(i).getRole());

			// menu - options
			for (int j = 0; j < menuList.size(); j++) {
				if (menuList.get(j).getRoleName().equals(list_roles.get(i))
						&& menuList.get(j).getType().equals(Statics.getMenuOption())) {
					Menu_Options temp = menuList.get(j);
					optMenu.add(temp);
				}
			}
		}
		return optMenu;
	}

	/**
	 * Method generate objects-List only used only as options of Menu.
	 *
	 * @return objects-list
	 */
	@Override
	public List<Menu_Options> showMenuSub() {

		List<Menu_Options> menuList = new ArrayList();
		menuList = showMenu();

		// temporary array
		List<Menu_Options> subMenu = new ArrayList();

		User_RolesDAO userRoles = new User_RolesDAOImpl();
		List<User_Roles> list = userRoles
				.getAllRoleNames(SecurityContextHolder.getContext().getAuthentication().getName());

		// active rools - string
		List<String> list_roles = new ArrayList();

		for (int i = 0; i < list.size(); i++) {

			list_roles.add(i, list.get(i).getRole());

			// menu - submenus
			for (int j = 0; j < menuList.size(); j++) {
				if (menuList.get(j).getRoleName().equals(list_roles.get(i))
						&& menuList.get(j).getType().equals(Statics.getSubMenu())) {
					Menu_Options temp = menuList.get(j);
					subMenu.add(temp);
				}
			}

		}

		return subMenu;
	}

	/**
	 * Add menu to model: - three list for: top-bar, sub-menus, options in menu -
	 * label for non-db static option 'logout' and label for name of application
	 * 
	 * @param model - read Spring Model-object
	 * 
	 * @return model handled full menu
	 */
	@Override
	public Model addMenuAndLabels(Model model) {

		List<Menu_Options> topMenu = showMenuTop();
		List<Menu_Options> subMenu = showMenuSub();
		List<Menu_Options> optMenu = showMenOpt();
		model.addAttribute("topBar", topMenu);
		model.addAttribute("subBar", subMenu);
		model.addAttribute("Opt", optMenu);

		model.addAttribute("nameApp", stat.getnameApp());
		model.addAttribute("signOutLabel", stat.getSignOutLabel());
		model.addAttribute("helpLabel", stat.getHelp());

		return model;
	}

	/**
	 * Method add to db new menu object: option
	 * Method also format path to option and add accessed role.
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@Override
	public boolean saveNewOption(Menu_Options newOption) throws SecurityException, IOException {

		/* options in submenu */
		if (newOption.getType().equals(stat.getMenuOption())) {

			adminNameRoleCorrect(newOption);

			String tempPath = newOption.getMenuOptionName().trim();
			tempPath = tempPath.replace(" ", "");

			switch (newOption.getRoleName()) {
			case "ROLE_ADMIN": {
				newOption.setPath("adminTools/" + tempPath);
				break;
			}
			case "ROLE_MANAGER": {
				newOption.setPath("manage/" + tempPath);
				break;
			}
			case "ROLE_SETTINGS": {
				newOption.setPath("settings/" + tempPath);
				break;
			}
			case "ROLE_EMPLOYEE": {
				newOption.setPath("employee/" + tempPath);
				break;
			}
			}

		}

		/* check duplicates - duplicate option name */

		List<Menu_Options> checkList = showRecordsByRole(newOption.getRoleName());
		Boolean flag = true;

		for (int i = 0; i < checkList.size(); i++) {

			String tempOption = checkList.get(i).getMenuOptionName();
			String tempSubmenu = checkList.get(i).getSubMenu();

			if (tempOption.equals(newOption.getMenuOptionName()) && tempSubmenu.equals(newOption.getSubMenu())) {
				flag = false;

				/* add log when new option with choosen submenu already exist (duplicate) */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try added duplicate option name '" + newOption.getMenuOptionName() + " to submenu "
						+ newOption.getSubMenu(), 1);
				break;
			}
		}

		if (flag == true) {

			session = ConnectDB.getSessionFactory().getCurrentSession(); 
			transaction = session.beginTransaction();
			try {
				//session.beginTransaction();

				session.save(newOption);

				session.getTransaction().commit();

				/* new subfolder added - add log */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " added new option to menu: " + newOption.toString(), 0);

				// System.out.println("Finishing 'save new Option' .");

			} finally {
				
				
			}

		}

		return flag;
	}

	/**
	 * Method update option displayed name and:
	 * - check duplicates
	 * - add log
	 * 
	 *
	 * @param updatedOption - oryginal object before update
	 * @param newName - string new name
	 * 
	 * @return flag to inform if option has been updated
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public boolean updateOption(Menu_Options updatedOption, String newName) throws SecurityException, IOException {

		// temp object before update name
		Menu_Options tempMenuOption = updatedOption;
		tempMenuOption.setMenuOptionName(newName);

		/* check duplicates - duplicate option name */

		List<Menu_Options> checkList = showRecordsByRole(updatedOption.getRoleName());
		Boolean flag = true;
		for (int i = 0; i < checkList.size(); i++) {

			String tempOption = checkList.get(i).getMenuOptionName();
			String tempSubmenu = checkList.get(i).getSubMenu();

			if (tempOption.equals(newName) && tempSubmenu.equals(updatedOption.getSubMenu())) {
				flag = false;

				/* add log when new option with choosen submenu already exist (duplicate) */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try update option name " + updatedOption.getMenuOptionName() + " in submenu "
						+ updatedOption.getSubMenu()+", but option in this localization already exists.", 1);
				break;
			}
		}

		if (flag == true) {

			session = ConnectDB.getSessionFactory().getCurrentSession(); 
			transaction = session.beginTransaction();
			try {
				//session.beginTransaction();

				session.update(tempMenuOption);

				session.getTransaction().commit();

				/* new subfolder added - add log */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " updated option menu, old name: " + updatedOption.toString()+", new menuOptionName: "+newName, 0);

				// System.out.println("Finishing 'save new Option' .");

			} finally {
				
			}

		}

		return flag;

	}

	
	/**
	 * Method add new sub-folder to menu and:
	 * - add log
	 *
	 * @param newSubfolder - new object to save
	 * 
	 * @return flag to inform if sub-folder has been updated
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Override
	public Boolean saveNewSubfolder(Menu_Options newSubfolder) throws SecurityException, IOException {

		newSubfolder.setSubMenu(newSubfolder.getRoleName());

		adminNameRoleCorrect(newSubfolder);

		// check duplicates
		List<Menu_Options> checkList = showRecordsByRole(newSubfolder.getRoleName());
		Boolean flag = true;
		for (int i = 0; i < checkList.size(); i++) {

			String check = checkList.get(i).getMenuOptionName();
			if (check.equals(newSubfolder.getMenuOptionName())) {
				// System.out.println(" Duplikat " + check);
				flag = false;

				/* add log when subfolder name is duplicated */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " try added duplicate subfolder to menu " + newSubfolder.getRoleName() + " named: "
						+ newSubfolder.getMenuOptionName(), 1);
				break;
			}
		}

		if (flag == true) {
			// System.out.println("wprowadzone: "+newSubfolder.toString());

			session = ConnectDB.getSessionFactory().getCurrentSession(); 
			transaction = session.beginTransaction();
			try {
				//session.beginTransaction();

				session.save(newSubfolder);

				session.getTransaction().commit();

				// System.out.println("Finishing 'save new Subfolder' .");
				/* new subfolder added - add log */
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " added new subfolder: " + newSubfolder.toString(), 0);

			} finally {
				
				//factory.close();
			}

		}

		return flag;
	}

	/**
	 * Method show all menu positions by role, used to manage menu elements.
	 * 
	 * @param role as string
	 * 
	 * @return lis of all menu-positions
	 */
	@Override
	public List<Menu_Options> showRecordsByRole(String role) {

		List<Menu_Options> temp = showMenu();
		List<Menu_Options> list = new ArrayList<Menu_Options>();

		for (int i = 0; i < temp.size(); i++) {
			String check = temp.get(i).getRoleName();
			if (check.equals(role)) {
				list.add(temp.get(i));
			}
		}

		return list;
	}

	/**
	 * Method delete item from menu by given id and:
	 * - check if subfolder is empty
	 * - add log
	 * 
	 * @param id of element menu to delete
	 * 
	 * @param flag to inform method if option is deleted
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@Override
	public boolean delete(int id) throws SecurityException, IOException {

		session = ConnectDB.getSessionFactory().getCurrentSession(); 
		transaction = session.beginTransaction();

		//session.beginTransaction();
		Menu_Options item = session.get(Menu_Options.class, id);

		transaction.commit();
		session.close();
		
		
		boolean flag = true;
		if (item.getType().equals(Statics.getSubMenu())) {
			flag = checkRelationships(item);

		}

		if (flag == true) {
			try {

				session = ConnectDB.getSessionFactory().openSession(); 
				transaction = session.beginTransaction();
				
				session.delete(item);
				Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
						+ " succesfully deleted option '" + item.getMenuOptionName() + "' from submenu "
						+ item.getSubMenu(), 0);
				transaction.commit();

			} finally {
				session.close();
			}
		} else {

			Logs log = new Logs(SecurityContextHolder.getContext().getAuthentication().getName()
					+ " try deleted from menu subfolder: '" + item.toString()
					+ "', but in this subfolder still are active options.", 1);
		}

		return flag;
	}

	/**
	 * Method-tool to check if in subfolder are any object.
	 * 
	 * Flag:
	 * true - empty
	 * false - options is in this subfolder
	 *
	 * @param item - object of Menu-Options to check
	 * @return flag
	 */
	private boolean checkRelationships(Menu_Options item) {

		List<Menu_Options> list = showMenOpt();
		boolean flag = true;
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getSubMenu().equals(item.getMenuOptionName())) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * Method change string-name of role from 'Administrator' to 'Admin' in new-added role.
	 *
	 * @param newOption - object to check and correct role-name
	 */
	private void adminNameRoleCorrect(Menu_Options newOption) {

		if (newOption.getRoleName().equals("Administrator")) {
			newOption.setRoleName("Admin");
		}
		String temp = "ROLE_" + newOption.getRoleName().toUpperCase();
		newOption.setRoleName(temp);
	}
	
	/**
	 * Class-tool to sort List<Menu_Options>
	 * 
	 * @ModelAttribute("submenuName") public List<Menu_Options> getSubmenus()
	 * 
	 * @author janus
	 *
	 */
	class SortObjectList implements Comparator<Menu_Options> {
		@Override
		public int compare(Menu_Options a, Menu_Options b) {

			return a.getRoleName().compareTo(b.getRoleName());
		}
	}
}
