package com.ssd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class with structure of menu toolbar:
 * - top panel
 * - first level (subfolders and options)
 * - second level (only options)
 * 
 * 
 * Only field 'path' can be 'null', when 'type' is part of top pane (value 'TOP') or first-level subfolder (value 'SUB').
 * Other fields are with condition - 'not null'.
 * 
 * Table has got three types of datas:
 * - main bar with main options (displayed by role), column type = 'TOP'
 * - subfolders (second level of menu), column type = 'SUB'
 * - options (first level, behind subfolders or thirt level - in subfolders)
 * 
 * Validation of repeated fields are in controllers. Unique must be at the same time fields: role_name+menu_option_name+submenu+type.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 *
 */
@Entity
@Table(name="menu_options")
public class Menu_Options {
	
	/**
	 * Option-id, primary key, auto_increment.
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
		
	/**
	 * Foreign key - role name (as string)
	 */
	@Column(name="role_name")
	private String roleName;
	
	/**
	 * Displayed name of option/subfolder
	 */
	@Column(name="menu_option_name")
	private String menuOptionName;
	
	/**
	 * Option in menu must be connected wih subfolder in this field.
	 * Field must be identically with 'menuOptionName' only for 'TOP' menu positions.
	 */
	@Column(name="submenu")
	private String subMenu;
	
	/**
	 * Type of option.
	 * Used only three values:
	 * 'TOP'
	 * 'SUB'
	 * 'OPT' 
	 */
	@Column(name="type")
	private String type;
	
	/**
	 * Path of controller.
	 * This field can be 'null', when 'type' is part of top pane (value 'TOP') or first-level subfolder (value 'SUB') 
	 */
	@Column(name="path")
	private String path;
	
	/**
	 * Default Controller
	 */
	public Menu_Options() {}
	
	
	//public Menu_Options(String name) {this.menuOptionName=name;}

	/**
	 * Constructor used in all forms.
	 * Field 'path' is generated automatically after formatting and concatenating menu_option_name and role_name in controller:
	 * @see Documentation of {@link Menu_OptionsDAOIml}
	 * 
	 * @param roleName
	 * @param menuOptionName
	 * @param subMenu
	 */
	public Menu_Options(String roleName, String menuOptionName, String subMenu) {

		this.roleName = roleName;
		this.menuOptionName = menuOptionName;
		this.subMenu = subMenu;
	}

	/** Default methods get-set*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuOptionName() {
		return menuOptionName;
	}

	public void setMenuOptionName(String menuOptionName) {
		this.menuOptionName = menuOptionName;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}
	
	

	public void setPath(String path) {
		this.path = path;
	}

	
	/**
	 * Default method to show object information.
	 */
	@Override
	public String toString() {
		return "Menu_Options [id=" + id + ", roleName=" + roleName + ", menuOptionName=" + menuOptionName + ", subMenu="
				+ subMenu + ", type=" + type + ", path=" + path + "]";
	}
	
	
	

}
