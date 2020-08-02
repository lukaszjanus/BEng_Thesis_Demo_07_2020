package com.ssd.config.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * Class used to connect with db for more complex queries - not used.
 * 
 * Public interface for this class - @see {@link JDBCConnect}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020 
 *
 */
public class JDBCConnectImpl implements JDBCConnect {

	String url = "jdbc:mysql://127.0.0.1:3306/SSD_Reports?useSSL=false";
	String user = "ssdUser";
	String password = "ssdUser";
	String driver = "org.mariadb.jdbc.Driver";
	
	static Connection con = null;

	/**
	 * Method used to open connection to db.
	 * 
	 * @return Connection.
	 */
	@Override
	public Connection getConn() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);			
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("blad getCon() ");
		}
		return con;
	}
}
