package com.ssd.config;

import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.sun.xml.bind.v2.schemagen.xmlschema.Documentation;

/**
 * Servlet Container Initialization - class used to register a DispatcherServlet
 * and use Java-based Spring configuration.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020
 *
 * @see Documentation of Class
 *      AbstractAnnotationConfigDispatcherServletInitializer in Spring Framework
 */
public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Configuration of base context application.
	 * 
	 * @return configuration of application (Spring Framework)
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class[] { AppConfig.class };
	}

	/**
	 * Configuration of servlet application context.
	 * 
	 * @return configuration of application (Spring Framework)
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {

		return new Class[] { AppConfig.class };
	}

	/**
	 * Configuration of servlet's mapping.
	 * 
	 * @return string mapping of app
	 */
	@Override
	protected String[] getServletMappings() {

		return new String[] { "/" };
	}

	/**
	 * Servlet initializer and register method,
	 * add possibility to global handling '404 error - page not found'.
	 */
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		boolean done = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true"); //
		if (!done)
			throw new RuntimeException();
	}
}