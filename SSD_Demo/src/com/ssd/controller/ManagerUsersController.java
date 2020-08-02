package com.ssd.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssd.dao.Menu_OptionsDAO;
import com.ssd.dao.Menu_OptionsDAOImpl;
import com.ssd.dao.User_RolesDAO;
import com.ssd.dao.User_RolesDAOImpl;
import com.ssd.dao.UsersDAO;
import com.ssd.dao.UsersDAOImpl;
import com.ssd.entity.User_Roles;
import com.ssd.entity.Users;

/**
 * Users controller for manage Users and Roles by Manager.
 *
 * Annotations:
 * GetMapping - used only to display site.
 * PostMapping - used to Post-method of forms.
 * ModelAttribute - added spring object (class Model) to Front-End
 * Controller - class used to request handling by Spring MVC (get and post)
 * RequestMapping - mapping of admin area, all methods have added prefix from this annotation
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 12 sty 2020
 *
 */
@Controller
public class ManagerUsersController {

	/**
	 * Method show view with all users list.
	 * 
	 * @GetMapping read get-action 'manage/ShowUsers'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return String path to view with all users
	 */
	@GetMapping("manage/ShowUsers")
	public String showToolShowUsers(Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		List<Users> usersList = usersDAO.getUsers();
		model.addAttribute("users", usersList);
		return "manage/showUsers";
	}

	/**
	 * Method show view with active users list.
	 * 
	 * @GetMapping read get-action 'manage/ActiveUsers'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return String path to view with active users
	 */
	@GetMapping("manage/ActiveUsers") // form show users
	public String showActiveUsers(Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		List<Users> usersList = usersDAO.getActiveUsers();
		model.addAttribute("users", usersList);
		return "manage/activeUsers"; // zwrocenie pliku
	}

	/**
	 * Method disable chosen user.
	 * 
	 * @PostMapping read post-action 'manage/disableUserForm'
	 * 
	 * @param id  of user to disable
	 * @param res - servlet response, here used to add cookie
	 * 
	 * @return String path to controller
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping(value = "manage/disableUserForm")
	public String disableUser(@RequestParam("id") Integer id, HttpServletResponse res)
			throws SecurityException, IOException {
		UsersDAO user = new UsersDAOImpl();
		Users userToDisable = user.getUser(id);
		userToDisable.setEnabled(false);
		user.saveUser(userToDisable, 2);

		Cookie cookie = new Cookie("disableUser", userToDisable.getUserName());
		cookie.setMaxAge(10);

		res.addCookie(cookie);
		return "redirect:/manage/ActiveUsers";
	}

	/**
	 * Method show only disabled users.
	 * 
	 * @GetMapping read get-action 'manage/UnactiveUsers' and 'manage/disabledUsers'
	 * 
	 * @param model - read Model-object
	 * 
	 * @return String path to view with disabled users
	 */
	@GetMapping(value = { "manage/UnactiveUsers", "manage/disabledUsers" })
	public String showUnActiveUsers(Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		List<Users> usersList = usersDAO.getDisabledUsers();
		model.addAttribute("users", usersList);
		return "manage/disabledUsers";
	}

	/**
	 * Method used to enable chosen user.
	 * 
	 * @PostMapping read post-action 'manage/enableUser'
	 * 
	 * @param id  of user to enable
	 * @param res - servlet response, here used to add cookie
	 * 
	 * @return string path to controller
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@PostMapping(value = "manage/enableUser")
	public String enableUser(@RequestParam("id") Integer id, HttpServletResponse res)
			throws SecurityException, IOException {

		UsersDAO user = new UsersDAOImpl();
		Users userToEnable = user.getUser(id);

		userToEnable.setEnabled(true);
		user.saveUser(userToEnable, 1);
		//user.changeUserEnableStatus(userToEnable);

		Cookie cookie = new Cookie("enableUser", userToEnable.getUserName());
		cookie.setMaxAge(10);
		res.addCookie(cookie);

		return "redirect:/manage/disabledUsers";

	}

	/**
	 * Form - display after get user from list of all users and active users: -
	 * update user - add roles - remove roles
	 * 
	 * @PostMapping read post-action 'manage/updateUserForm' (RequestMethod.Post is
	 *              notation from older Spring MVC)
	 * 
	 * @param req   - servlet request, to add userName to session
	 * @param user  - Object of user to load in form
	 * @param id    of user
	 * @param model - read Model-object
	 * 
	 * @return string path to view manage/updateUserForm.jsp -> edit form
	 */
	@RequestMapping(value = "manage/updateUserForm", method = RequestMethod.POST) // handler pobierany z jsp
	public String showUpdateUserForm(HttpServletRequest req, @ModelAttribute Users user, @RequestParam("id") Integer id,
			Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		user = usersDAO.getUser(id); // get one customer

		model.addAttribute("tempUser", user);

		HttpSession session = req.getSession();
		session.setAttribute("userNameR", user.getUserName());

		User_RolesDAO activeRoles = new User_RolesDAOImpl();

		List<String> list = new ArrayList<>();
		List<User_Roles> listRoles = activeRoles.getAllRoleNames(user.getUserName());

		for (int i = 0; i < listRoles.size(); i++) {
			String temp = listRoles.get(i).getRole().substring(5);
			list.add(temp);
		}

		model.addAttribute("roles_active", list);

		User_RolesDAO otherRoles = new User_RolesDAOImpl();
		List<String> listUnactiveRoles = new ArrayList<>();

		for (int i = 0; i < otherRoles.getNotActiveUserRole(user.getUserName()).size(); i++) {
			String temp = otherRoles.getNotActiveUserRole(user.getUserName()).get(i).getRoleName();
			listUnactiveRoles.add(temp);
		}
		model.addAttribute("roles_other", listUnactiveRoles);

		return "manage/updateUserForm";
	}

	/**
	 * Form - display after update user.
	 * 
	 * @GetMapping read get-action 'manage/updateUserForm'
	 * 
	 * @param req   - servlet request, to get cookies and add new, add userName to
	 *              session
	 * @param user  - Object of user to load in form
	 * @param model - read Model-object
	 * 
	 * @return manage/updateUserForm.jsp -> edit form
	 */
	@RequestMapping(value = "manage/updateUserForm", method = RequestMethod.GET)
	public String showUpdateUserFormGet(HttpServletRequest req, @ModelAttribute Users user, Model model) {

		UsersDAO usersDAO = new UsersDAOImpl();
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("cookieId")) {
					int id = Integer.valueOf(c.getValue());
					c.setMaxAge(0);
					user = usersDAO.getUser(id); // get one customer
					break;
				}
			}
		}

		if (user.getId() == 0) {
			return "acces-denied";
		}

		model.addAttribute("tempUser", user);

		HttpSession session = req.getSession();
		session.setAttribute("userNameR", user.getUserName());

		User_RolesDAO activeRoles = new User_RolesDAOImpl();

		List<String> list = new ArrayList<>();
		List<User_Roles> listRoles = activeRoles.getAllRoleNames(user.getUserName());

		System.out.println(" before petla");
		for (int i = 0; i < listRoles.size(); i++) {
			String temp = listRoles.get(i).getRole().substring(5);
			list.add(temp);
		}

		model.addAttribute("roles_active", list);

		User_RolesDAO otherRoles = new User_RolesDAOImpl();
		List<String> listUnactiveRoles = new ArrayList<>();
		for (int i = 0; i < otherRoles.getNotActiveUserRole(user.getUserName()).size(); i++) {
			String temp = otherRoles.getNotActiveUserRole(user.getUserName()).get(i).getRoleName();
			listUnactiveRoles.add(temp);
		}
		model.addAttribute("roles_other", listUnactiveRoles);

		return "manage/updateUserForm";
	}

	/**
	 * Save updated user.
	 * 
	 * Flags: - flag used to DAO-response: 0-update user succesfully,1-username
	 * duplicate, 2-email duplicate.
	 * 
	 * @PostMapping read post-action 'manage/saveUpdatedUser'
	 * 
	 * @param user - updated object to save
	 * @param req  - servlet request, here used to read button-label
	 * @param res  - servlet response, here used to add cookie
	 * 
	 * @return string path to controller
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@PostMapping("/manage/SaveUpdatedUser")
	public String saveUser(@ModelAttribute Users user, HttpServletRequest req, HttpServletResponse res)
			throws SecurityException, IOException {

		UsersDAO usersDAO = new UsersDAOImpl();
		/**
		 * '0' - information for DAO method, that it is only update of user
		 */
		int flag = usersDAO.saveUser(user, 0);

		switch (flag) {
		case 0: {
			String submitType = req.getParameter("submit");
			if (submitType.equals("Update User")) {
				Cookie cookie = new Cookie("updateUser", "update");
				cookie.setMaxAge(10);
				res.addCookie(cookie);

				String tempId = String.valueOf(user.getId());
				Cookie cookieId = new Cookie("cookieId", tempId);
				cookieId.setMaxAge(10);
				res.addCookie(cookieId);

				return "redirect:/manage/updateUserForm";
			}
			if (submitType.equals("Save and return to list")) {

				return "redirect:/manage/ActiveUsers";
			}
			break;
		}

		case 1: {
			Cookie cookie = new Cookie("updateUser", "username");
			cookie.setMaxAge(10);
			res.addCookie(cookie);

			String tempId = String.valueOf(user.getId());
			Cookie cookieId = new Cookie("cookieId", tempId);
			cookieId.setMaxAge(10);
			res.addCookie(cookieId);

			return "redirect:/manage/updateUserForm";
		}

		case 2: {
			Cookie cookie = new Cookie("updateUser", "email");
			cookie.setMaxAge(10);
			res.addCookie(cookie);

			String tempId = String.valueOf(user.getId());
			Cookie cookieId = new Cookie("cookieId", tempId);
			cookieId.setMaxAge(10);
			res.addCookie(cookieId);

			return "redirect:/manage/updateUserForm";
		}
		}
		return "redirect:/";
	}

	/**
	 * Method add role to user.
	 * 
	 * @GetMapping read get-action 'manage/addRoleToUser' (@RequestMapping is older
	 *             version of Spring MVC)
	 * 
	 * @param req  - read servlet request - get userName from session (as session
	 *             parameter) and get label of button
	 * @param res  - read response of servlet, here used to add cookie
	 * @param role - String role chosen in form, assigned by parameter 't1' (in form
	 *             it is attribute 'name' in 'select')
	 * 
	 * @return string path of controller
	 * 
	 * @throws IOException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "manage/addRoleToUser", method = RequestMethod.GET) // handler pobierany z jsp
	public String saveUserRole(HttpServletRequest req, @RequestParam("t1") String role, HttpServletResponse res)
			throws SecurityException, IOException {

		HttpSession session = req.getSession();
		String user = session.getAttribute("userNameR").toString();
		session.removeAttribute("userNameR");

		User_RolesDAO add_role = new User_RolesDAOImpl();
		add_role.addRoleToUser(user, role);

		String submitType = req.getParameter("submit");
		if (submitType.equals("Add role")) {

			Cookie cookie = new Cookie("cookieUser", "added");
			cookie.setMaxAge(10);
			res.addCookie(cookie);

			/* part needed to 'get' updateUserForm */
			UsersDAO users = new UsersDAOImpl();
			Users userTemp = users.getUserByUsername(user);

			String tempId = String.valueOf(userTemp.getId());
			Cookie cookieId = new Cookie("cookieId", tempId);
			cookieId.setMaxAge(10);
			res.addCookie(cookieId);

			return "redirect:/manage/updateUserForm";
		}
		if (submitType.equals("Add and return to list of users")) {
			return "redirect:/manage/ActiveUsers";
		}
		return "redirect:/";
	}

	/**
	 * Remove role from user.
	 * 
	 * @GetMapping read get-action 'adminTools/removeRoleToUser' (@RequestMapping is
	 *             older version of Spring MVC)
	 * 
	 * @param req  - read servlet request - get userName from session (as session
	 *             parameter) and get label of button
	 * @param res  - read response of servlet, here used to add cookie
	 * @param role - String role chosen in form, assigned by parameter 't1' (in form
	 *             it is attribute 'name' in 'select')
	 * 
	 * @return string path of controller
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@RequestMapping(value = "manage/removeRoleToUser", method = RequestMethod.GET)
	public String removeUserRole(HttpServletRequest req, @RequestParam("t1") String role, HttpServletResponse res)
			throws SecurityException, IOException {

		HttpSession session = req.getSession();
		String user = session.getAttribute("userNameR").toString();
		session.removeAttribute("userNameR");

		User_RolesDAO deleteRole = new User_RolesDAOImpl();
		deleteRole.deleteRoleOfChosenUser(user, "ROLE_" + role);

		String submitType = req.getParameter("submit");
		if (submitType.equals("Remove Role")) {

			Cookie cookie = new Cookie("cookieUser", "removed");
			cookie.setMaxAge(10);
			res.addCookie(cookie);

			/* part needed to 'get' updateUserForm */
			UsersDAO users = new UsersDAOImpl();
			Users userTemp = users.getUserByUsername(user);

			String tempId = String.valueOf(userTemp.getId());
			Cookie cookieId = new Cookie("cookieId", tempId);
			cookieId.setMaxAge(10);
			res.addCookie(cookieId);

			return "redirect:/manage/updateUserForm";
		}
		if (submitType.equals("Remove and return to list of users")) {
			return "redirect:/manage/ActiveUsers";
		}
		return "redirect:/";
	}

	/**
	 * Method to change password of chosen user.
	 * 
	 * @PostMapping read post-action 'manage/changePassword'
	 * 
	 * @param req      - servlet request, used to get from session userName.
	 * @param password - new password typed in form
	 * @param res      - servlet response, here to add cookie
	 * 
	 * @return String path to controller
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	@RequestMapping(value = "manage/changePassword", method = RequestMethod.POST)
	public String changePassword(HttpServletRequest req, @RequestParam("p1") String password, HttpServletResponse res)
			throws SecurityException, IOException {

		HttpSession session = req.getSession();
		String name = session.getAttribute("userNameR").toString();
		session.removeAttribute("userNameR");

		/* part needed to 'get' updateUserForm */
		UsersDAO userDao = new UsersDAOImpl();
		Users userTemp = userDao.getUserByUsername(name);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		password = ("{bcrypt}" + passwordEncoder.encode(password));
		userDao.updatePassword(name, password, 1);

		// cookie to inform about changed password
		Cookie cookie = new Cookie("updatePass", "passwordUpdated");
		cookie.setMaxAge(10);
		res.addCookie(cookie);

		String tempId = String.valueOf(userTemp.getId());
		Cookie cookieId = new Cookie("cookieId", tempId);
		cookieId.setMaxAge(10);
		res.addCookie(cookieId);

		return "redirect:/manage/updateUserForm";
	}

	/**
	 * Model attribute to POST method "showUpdateUserForm"
	 * 
	 * ModelAttribute - 'tempUser'
	 * 
	 * @return empty Users-object
	 */
	@ModelAttribute("tempUser")
	public Users setUser() {
		return new Users();
	}

	/**
	 * Model create method to translate 'false' to 'no' and 'yes' to true.
	 * 
	 * To test, probably not used.
	 * 
	 * ModelAttribute - 'disableUser'
	 *
	 * @return Map<String,String>
	 */
	@ModelAttribute("disableUser")
	public Map<String, String> getOptions() {

		Map<String, String> list = new HashMap<String, String>();
		list.put("YES", "true");
		list.put("NO", "false");

		return list;
	}

	/**
	 * Method created in DAO and generated structure of whole Menu: Top-bar,
	 * Sub-folders, Options. Object is added to Front-End.
	 * 
	 * @param model - default spring method, here menu is added to all jsps
	 *              (Front-End)
	 * 
	 * @return object Menu_OptionsDAO
	 */
	@ModelAttribute
	public Model addMenuAndLabels(Model model) {
		Menu_OptionsDAO menu = new Menu_OptionsDAOImpl();
		model.addAttribute(menu.addMenuAndLabels(model));
		return model;
	}
}
