package com.ssd.statics;

/**
 * Class - container with static values, labels for frontend, menu levels, functions.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020 
 *
 */
public class Statics {

	/* menu level options - to filtrate list with type of option*/
	/**
	 * Main menu 'top-bar'.
	 */
	private static String menuTop = "TOP";
	/**
	 * Main menu 'sub-menu'.
	 */
	private static String subMenu = "SUB";
	/**
	 * Main menu link - 'Option'.
	 */
	private static String menuOption = "OPT";
	
	/* Gui options */

	/**
	 * Label of application name. 
	 */
	private static String nameApp = "DEMO_SSD";
	
	/**
	 * Label for Sign-out button/link.
	 */
	private static String signOutLabel = "Exit";

	/**
	 * Label for 'Home' button/link.
	 */
	private static String home = "Home";

	/**
	 * Label for 'Help' button/link.
	 */
	private static String help = "Help";
	
	
	/* Methods */
	
	/**
	 * Hard coded function name of availability to added function
	 */
	private static String Add_Report_Employee="Add_Report_Employee";
	
	/**
	 * Hard coded function name of availability to show uploaded reports
	 */
	private static String Show_Added_Reports_Employee="Show_Added_Reports_Employee";
	
	/* Standard getters and setters */
	
	/**
	 * Get function name.
	 *
	 * @return string function name.
	 */
	public static String getAdd_Report_Employee() {
		return Add_Report_Employee;
	}

	/**
	 * Get function name.
	 *
	 * @return string function name.
	 */
	public static String getShow_Added_Reports_Employee() {
		return Show_Added_Reports_Employee;
	}

	/**
	 * Get value of label for 'help'.
	 *
	 * @return label
	 */
	public static String getHelp() {
		return help;
	}

	/**
	 * Get value of label for button 'home'.
	 *
	 * @return label
	 */
	public static String getHome() {
		return home;
	}

	/**
	 * Get value of string for button sign-out .
	 *
	 * @return label
	 */
	public static String getSignOutLabel() {
		return signOutLabel;
	}

	/**
	 * Get value of string for Name of application.
	 *
	 * @return label
	 */
	public static String getnameApp() {
		return nameApp;
	}

	/**
	 * Get value of string  for type menu - 'Top Option' (top-bar). 
	 *
	 * @return label
	 */
	public static String getMenuTop() {
		return menuTop;
	}

	/**
	 * Get value of string for type menu - 'Subfolder'. 
	 *
	 * @return label
	 */
	public static String getSubMenu() {
		return subMenu;
	}

	/**
	 * Get value of string for type menu - 'Option'.  
	 *
	 * @return label
	 */
	public static String getMenuOption() {
		return menuOption;
	}
}
