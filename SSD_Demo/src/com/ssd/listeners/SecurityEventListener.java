package com.ssd.listeners;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.ssd.logs.Logs;


/**
 * Listener-Class used to registering user logins.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020 
 *
 */
@Component
public class SecurityEventListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	/**
	 * Handle an application event - interactive authentication was successful.
	 *
	 * @param appEvent - object with information responce information of event. 
	 */
	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent appEvent) {
		
		String user = appEvent.getAuthentication().getName().toString();
		try {
			if (user != null) {

				Logs log = new Logs("User " + user + " succesfully login.", 0);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}