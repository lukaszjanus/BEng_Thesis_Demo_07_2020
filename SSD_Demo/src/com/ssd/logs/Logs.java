package com.ssd.logs;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.ssd.statics.Statics;


/**
 * Class Tool for generate logs in file:
 * 
 * stat.getnameApp() + "_logs.txt"
 * @see Documentation of class {@link Statics}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 sty 2020 
 *
 */
public class Logs {

	/**
	 * Object used to create an anonymous Logger.
	 * @see Official Documentation of Logger Class.
	 */
	private static Logger logger = Logger.getAnonymousLogger();

	/**
	 * Handler of txt file of log.
	 */
	private static FileHandler handler;
	
	/**
	 * Text communicate of log - added always at the moment of creation new object Logs/ 
	 */
	private String communicate;
	
	/**
	 * Static value - used as part of file-name.
	 * @see Documentation of class {@link Statics}
	 */
	private Statics stat;

	/**
	 * String table - type of log communicate:
	 * index:
	 * 0 - INFO
	 * 1 - WARNING
	 */
	private String[] levels = { "INFO", "WARNING" };

	/**
	 * Method to get information about type of log.
	 *
	 * @param index - addet in constructor, shouldn't be null.
	 * @return name of log communicat type (as String)
	 */
	public String getLevel(int index) {
		return levels[index];
	}

	/**
	 * Constructor creating new logs - for standard application use logs.
	 *    
	 * @param communicate - string information from class, which should be added as log to file
	 * @param index - type of communicate for new log 
	 * @throws SecurityException
	 * @throws IOException
	 */
	public Logs(String communicate, int index) throws SecurityException, IOException {
		this.logger = logger;
		// Logger logger = Logger.getLogger(className);
		this.communicate = communicate;
		addLog(index);
	}

	/**
	 *  Default constructor
	 */
	public Logs() {	}

	/**
	 * Method add standard information about activity of users.
	 * 
	 * Standard action - check type of log-communicate and added correct type of log:
	 * 0 - add to log standard information, when operation has finished success.
	 * 1 - add to log warning, when information has been stopped.
	 * 
	 * @param index - type of log
	 * @throws IOException
	 * @throws SecurityException
	 */
	private void addLog(int index) throws IOException, SecurityException {

		handler = new FileHandler(stat.getnameApp() + "_logs.txt", true);

		handler.setFormatter(new SimpleFormatter());

		logger.addHandler(handler);

		switch (index) {
		case 0: {
			logger.info(communicate);
			break;
		}
		case 1: {
			logger.warning(communicate);
			break;
		}
		}
		handler.close();
	}
	
	
	/**
	 * Method add only added to db data information from files *.csv, *.xlsx
	 * 
	 * Standard action - check type of log-communicate and added correct type of log:
	 * 0 - add correctly data-row.
	 * 1 - add warning with information, that row has mistakes (with row)
	 * 
	 * @param index - type of log
	 * @throws IOException
	 * @throws SecurityException
	 */
	public void addLogDataReport(String communicate, int index) throws IOException, SecurityException {

		handler = new FileHandler("ReportsData_logs.txt", true);

		handler.setFormatter(new SimpleFormatter());

		logger.addHandler(handler);

		switch (index) {
		case 0: {
			logger.info(communicate);
			break;
		}
		case 1: {
			logger.warning(communicate);
			break;
		}
		}
		handler.close();
	}
}
