package com.ssd.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Class - configuration of Spring Security.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020 
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Object add a reference to our security data source.
	 */
	@Autowired
	private DataSource loginSecurityDataSource; // podlaczamy sql'a,library: import javax.sql.DataSource;

	/**
	 * Method used to authentication users. Checking data by sql query to two tables:
	 * 1. users:
	 * - login
	 * - password
	 * - enabled
	 * 2. user_roles:
	 * - username (in table it is String user-name, not foreign key 'id')
	 * - user_roles
	 * 
	 * @param auth - Object used to create authentication query. 
	 */
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	//	System.out.println(" security "+auth.toString());
		auth.jdbcAuthentication().dataSource(loginSecurityDataSource)
				.usersByUsernameQuery("select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery("select username, role FROM user_roles where username=?");
	}

	/**
	 * Configuration of Spring Security:
	 *  - authenticate users
	 *  - acces by roles to zones
	 *  - sites: login, logout, acces-denied
	 *  - folder 'resources' (gui-scripts in JS and CSS)
	 *  
	 *  @param http - default argument for Spring Security
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/settings/**").hasRole("SETTINGS")
				.antMatchers("/manage/**").hasRole("MANAGE")
				.antMatchers("/employee/**").hasRole("EMPLOYEE")
				.antMatchers("/systems/**").hasRole("ADMIN")
				.antMatchers("/adminTools/**").hasRole("ADMIN")
				.and()
				.formLogin().
					loginPage("/login")
					.usernameParameter("userName").passwordParameter("password")
					.loginProcessingUrl("/authenticateTheUser")
					.permitAll()
				.and()
				.logout().
					logoutSuccessUrl("/login")
					.permitAll()
				.and()
				.exceptionHandling()
					.accessDeniedPage("/acces-denied");				
	}
}
