package com.ssd.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for testing if connection to db is available.
 * 
 * @author Lukasz Janus
 * @version 1.0
 * @date 5 sty 2020
 *
 */
@WebServlet("/test")
public class DBConnectionTest extends HttpServlet {

	/**
	 * Default servlet serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Servlet implementation class - test connection.
	 * Not use in whole application.
	 * 
	 * @param request  - servlet request object
	 * @param response - servlet response objects
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = "ssdUser";
		String password = "ssdUser";
		String url = "jdbc:mysql://127.0.0.1:3306/SSD_Reports?useSSL=false";
		String driver = "org.mariadb.jdbc.Driver";

		try {
			PrintWriter out = response.getWriter();
			out.println("Connecting to db: " + url);
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, password);
			out.println("Success!!");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * Java console connection test.
	 * Path of configuration file not works with servlet.
	 */	
	static String url = null;
	static String user = null;
	static String password = null;
		
	/**
	 * Java console class - test connection
	 * 
	 * @param args - default String argument for 'main' method.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		readDbCredentials();
		try {
			System.out.println("Connecting do DB...");
			Connection conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connection succesfull!");
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection Error!");
		}
	}

	/**
	 * Method read data from db.properties with credentials to connect with db: user, password, url. 
	 */
	private static void readDbCredentials() {
		FileReader fr;
		List<String> dbCredentials = new ArrayList<String>();
		try {
			fr = new FileReader("src\\db.properties");
			BufferedReader br = new BufferedReader(fr);
			String line = null;

			int temp = 0;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					temp++;

					if (line.startsWith("jdbc.url=")) {
						System.out.println("sukces:");
						String[] s = line.split("jdbc.url=");
						int s2 = s.length;
						for (int i = 0; i < s.length; i++) {
							url = s[i];
						}
					}
					if (line.startsWith("jdbc.user=")) {
						System.out.println("sukces:");
						String[] s = line.split("jdbc.user=");
						int s2 = s.length;
						for (int i = 0; i < s.length; i++) {
							user = s[i];
						}
					}
					if (line.startsWith("jdbc.password=")) {
						System.out.println("sukces:");
						String[] s = line.split("jdbc.password=");
						int s2 = s.length;
						for (int i = 0; i < s.length; i++) {
							password = s[i];
						}
					}
				}
			}
			br.close();
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
}
