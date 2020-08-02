package com.ssd.config.jdbc;

import java.sql.Connection;

import com.ssd.config.jdbc.JDBCConnectImpl;

/**
 * Public interface for class: @see {@link JDBCConnectImpl}
 * 
 * Class used to connect with db for more complex queries - not used.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020 
 * 
 * @see {@link JDBCConnectImpl}
 *
 */
public interface JDBCConnect {

	/**
	 * Interface to run Connection for JDBC Configuration.
	 *
	 * @return Connection
	 */
	public Connection getConn();
}
