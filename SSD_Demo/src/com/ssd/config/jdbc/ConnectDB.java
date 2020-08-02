package com.ssd.config.jdbc;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Class to create session factory as static object - one factory for one DataBase.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 23 sty 2020 
 *
 */
public class ConnectDB {

	/**
	 * Static sessionFactory used to creation of Session instances
	 */
	 private static SessionFactory sessionFactory;

	 private static SessionFactory build() {
	        try {

	            return new Configuration().configure().buildSessionFactory();

	        } catch (Throwable ex) {

	            System.err.println("Initial SessionFactory creation failed: " + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    }
	
	    /**
	     * A static method for other method to get SessionFactory object
	     * initialized in this support class.
	     */    
	    public static SessionFactory getSessionFactory() {

	        if (sessionFactory == null) {
	            sessionFactory = build();
	        }
	        return sessionFactory;
	    }  
}