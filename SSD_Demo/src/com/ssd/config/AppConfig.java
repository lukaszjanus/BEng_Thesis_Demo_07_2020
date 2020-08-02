package com.ssd.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.ssd.logs.Logs;
import com.sun.xml.bind.v2.schemagen.xmlschema.Documentation;

/**
 * 
 *  Main configuration class of Spring, used to generate bean definitions and service requests for those beans. 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 3 sty 2020 
 *
 */
@Configuration
@EnableAspectJAutoProxy
@EnableWebMvc
@ComponentScan(basePackages = "com.ssd")
@PropertySource("classpath:db.properties")
public class AppConfig implements WebMvcConfigurer {

	/**
	 * Spring method - serve static resources and add location of folder 'resources'.
	 * Folder 'resources' contains folders used to corrected display of application:
	 * - 'js' with code in javascript 
	 * - 'styles' with Cascading Style Sheets
	 * 
	 * @param registry -  configure ResourceHttpRequestHandlers for serving static resources from the classpath 
	 *   
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new PathResourceResolver());
	}
	
	/**
	 * Object set up a variable to hold the db properties - read and hold data from poperties file.
	 * Object used in method public 'DataSource securityDataSource()', read information about JDBC connection properties.
	 * 
	 * @see Documentation of {@link Logs}
	 */
	@Autowired
	private Environment env; 

	
	/**
	 *  Object set up a logger for diagnostics - to delete after tests (replaced).
	 */
//	private Logger logger = Logger.getLogger(getClass().getName());

	
	/**
	 * Method define a bean for ViewResolver.
	 * Prefix - path of gui.
	 * Suffix -extension of dynamic html - here '*.jsp' 
	 *
	 * @return view resolver
	 */
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	/**
	 * Method define a bean for our security datasource, create and set connection pool properties.
	 * Data taken from file 'db.properties'.
	 * In this file you can find and change data:
	 * - jdbc connection properties (driver, url, user, password)
	 * - connection pool properties (initialPoolSize, minPoolSize, maxPoolSize, maxIddleTime)
	 * 
	 * Library: javax.sql.DataSource
	 *
	 * @return DataSource for connection with db.
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Bean
	public DataSource securityDataSource() throws SecurityException, IOException {

		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

		/**
		 * Code set the jdbc driver class - read all db data frop propetries 
		 */
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}

		/**
		 * Add information to log-file, which data-base is connected on first-time running of application. 
		 */
		Logs log = new Logs("jdbc.url - connected to: " + env.getProperty("jdbc.user"),0);

		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));

		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.maxIdleTime"));

		return securityDataSource;
	}	

	/**
	 * Helper method - read environment property and convert to int.
	 * Method used in 'public DataSource securityDataSource()' 
	 *
	 * @param propName - name of property
	 * @return - values of properties taken from file 'db.properties'.
	 * 
	 * @see Documentation of {@link AppConfig#securityDataSource()}
	 */
	private int getIntProperty(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}
	
	/**
	 * Method give possibility to upload files.
	 *
	 * @return object MultipartResolver
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonMultipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();  
	    return multipartResolver;
	}
}
