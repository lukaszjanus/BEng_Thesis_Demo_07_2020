package com.ssd.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.ui.Model;

import com.ssd.entity.Menu_Options;

/**
 * Interface with definitions of method to: - generate dynamic menu - manage
 * menu-options
 * 
 * Full code of methods - @see {@link Menu_OptionsDAOImpl}
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
public interface Menu_OptionsDAO {

	/**
	 * Method generate objects-List with all Options.
	 *
	 * @return objects-list
	 */
	public List<Menu_Options> showMenu();

	/**
	 * Method to get menuItem by primary key - 'id'.
	 * 
	 * @param id
	 * 
	 * @return object of class Menu_Options
	 */
	public Menu_Options getMenuItemById(int id);

	/**
	 * Method generate objects-List only used only as Top Bar of Menu.
	 *
	 * @return objects-list
	 */
	public List<Menu_Options> showMenuTop();

	/**
	 * Method generate objects-List only used only as submenus of Menu.
	 *
	 * @return objects-list
	 */
	public List<Menu_Options> showMenOpt();

	/**
	 * Method generate objects-List only used only as options of Menu.
	 *
	 * @return objects-list
	 */
	public List<Menu_Options> showMenuSub();

	/**
	 * Add menu to model: - three list for: top-bar, sub-menus, options in menu -
	 * label for non-db static option 'logout' and label for name of application
	 * 
	 * @param model - read Spring Model-object
	 * 
	 * @return model handled full menu
	 */
	public Model addMenuAndLabels(Model model);

	/**
	 * Method add to db new menu object: option Operations: - format path to option
	 * - add accessed role - check duplicates - add log
	 * 
	 * @param menu - object from form
	 * 
	 * @return flag to inform method if new option is added
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public boolean saveNewOption(Menu_Options menu) throws SecurityException, IOException;

	/**
	 * Method update option displayed name and: - check duplicates - add log
	 * 
	 *
	 * @param updatedOption - oryginal object before update
	 * @param newName       - string new name
	 * 
	 * @return flag to inform if option has been updated
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public boolean updateOption(Menu_Options updatedOption, String newName) throws SecurityException, IOException;

	/**
	 * Method add new sub-folder to menu and: - check duplicates - add log
	 *
	 * @param newSubfolder - new object to save
	 * 
	 * @return flag to inform if sub-folder has been updated
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public Boolean saveNewSubfolder(Menu_Options newSubfolder) throws SecurityException, IOException;

	/**
	 * Method show all menu positions by role, used to manage menu elements.
	 * 
	 * @param role as string
	 * 
	 * @return lis of all menu-positions
	 */
	public List<Menu_Options> showRecordsByRole(String role);

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
	public boolean delete(int id) throws SecurityException, IOException;

}
