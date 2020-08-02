package com.ssd.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Class - Registers the DelegatingFilterProxy to use the springSecurityFilterChain before any other registered Filter. 
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020 
 *
 * @see documentation of Class AbstractSecurityWebApplicationInitializer in Spring Security Framework. 
 */
public class SecurityWebApplicationInitializer 
						extends AbstractSecurityWebApplicationInitializer {

}