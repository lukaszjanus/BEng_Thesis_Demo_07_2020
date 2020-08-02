package com.ssd.dao;

import java.util.List;

import com.ssd.entity.Roles;

/**
 * Class used to get access to roles.
 * 
 * Public interface for class RolesDAO:- @see {@link RolesDAO}
 * 
 * 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
public interface RolesDAO {

	/**
	 * Get role by given id.
	 *
	 * @param id int of role
	 * 
	 * @return object-role
	 */
	public Roles getRoleById(int id);

	/**
	 * Method read all roles from db as list.
	 *
	 * @return objects-Roles List
	 */
	public List<Roles> getAllRoles();

	/**
	 * Method read from db (Roles) column 'roleName' and delete prefix 'ROLE_'
	 *
	 * @return list-String roleName
	 */
	public List<String> getAllRoleNamesString();

	/**
	 * Method read from db (Roles) only column 'roleName'.
	 *
	 * @return list-String roleName
	 */
	public List<Roles> getAllRoleNames();

}