package com.ssd.dao.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.containers.ChartContainer;

/**
 * Class prepare data to generate charts used ChartContainer.
 * 
 * Public interface - @see documentation of {@ChartContainerDAOImpl}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 10 kwi 2020 
 *
 */
public class ChartContainerDAOImpl implements ChartContainerDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method prepare data for chart by selected company (max two) and range of
	 * months.
	 *
	 * @param monthCount as String
	 * @param headers    for data as String - used only to search data
	 * 
	 * @return object list of ChartContainer; can be null if any report has not
	 *         found.
	 */
	@Override
	public List<ChartContainer> prepareChartCompany(String tempPeriod, String headers) {

		List<String> items = Arrays.asList(headers.split(";"));
		List<ChartContainer> list = new ArrayList<ChartContainer>();

		/* prepare empty list with all periods added as x-axis */
		prepare_Periods_on_X_axis(tempPeriod, list);

		/* get quantity for every company */
		for (int i = 0; i < items.size(); i++) {
			String companyTemp = items.get(i);
			for (int j = 0; j < list.size(); j++) {

				session = ConnectDB.getSessionFactory().getCurrentSession();
				transaction = session.beginTransaction();
				try {
					List tempQuantities = session.createSQLQuery("SELECT count(*) from reports_preview WHERE "
							+ "period='" + list.get(j).getColumn1() + "' and companyName = '" + companyTemp + "'")
							.list();

					Long temp = Long.valueOf(tempQuantities.get(0).toString());

					switch (i) {
					case 0: {
						list.get(j).setColumn2(temp);
						break;
					}
					case 1: {
						list.get(j).setColumn3(temp);
						break;
					}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				session.getTransaction().commit();
				session.close();
			}
		}

		/* data short format */
		for (int i = 0; i < list.size(); i++) {
			String dateShort = list.get(i).getColumn1();
			list.get(i).setColumn1(dateShort.substring(0, dateShort.length() - 3));
		}
		return list;
	}

	/**
	 * Method prepare data for chart by selected company (max two) and range of
	 * months. 'users' can be one company or list of companies with delimiter ';'
	 *
	 * @param monthCount as String
	 * @param company    as String
	 * @param users      for data as String - used only to search data
	 * 
	 * @return object list of ChartContainer; can be null if any report has not
	 *         found.
	 */
	@Override
	public List<ChartContainer> prepareChartUser(String tempPeriod, String company, String users) {

		List<String> items = Arrays.asList(users.split(";"));
		List<ChartContainer> list = new ArrayList<ChartContainer>();

		/* prepare empty list with all periods added as x-axis */
		prepare_Periods_on_X_axis(tempPeriod, list);

		/* get quantity for every company */

		for (int i = 0; i < items.size(); i++) {
			String userTemp = items.get(i);
			for (int j = 0; j < list.size(); j++) {

				session = ConnectDB.getSessionFactory().getCurrentSession();
				transaction = session.beginTransaction();
				try {
					List tempQuantities = session.createSQLQuery(
							"SELECT count(*) from reports_preview WHERE " + "period='" + list.get(j).getColumn1()
									+ "' and agent = '" + userTemp + "'" + " and companyName='" + company + "'")
							.list();

					Long temp = Long.valueOf(tempQuantities.get(0).toString());

					switch (i) {
					case 0: {
						list.get(j).setColumn2(temp);
						break;
					}
					case 1: {
						list.get(j).setColumn3(temp);
						break;
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.getTransaction().commit();
				session.close();
			}
		}

		/* data short format */
		for (int i = 0; i < list.size(); i++) {
			String dateShort = list.get(i).getColumn1();
			list.get(i).setColumn1(dateShort.substring(0, dateShort.length() - 3));
		}
		return list;
	}

	/**
	 * Method prepare data for chart by selected company (max two) and range of
	 * months. 'users' can be one company or list of companies with delimiter ';'
	 *
	 * @param tempPeriod       - monthCount as String
	 * @param company          as String
	 * @param agents           for data as String
	 * 
	 * @param selectCategory3l - selected category for first user;
	 * @param selectCategory3r - selected category for second user, can be null
	 * 
	 * @return object list of ChartContainer; can be null if any report has not
	 *         found.
	 */
	@Override
	public List<ChartContainer> prepareChartUserCategory(String tempPeriod, String company, String agents,
			String selectCategory3l, String selectCategory3r) {

		List<String> items = Arrays.asList(agents.split(";"));
		List<ChartContainer> list = new ArrayList<ChartContainer>();

		/* prepare empty list with all periods added as x-axis */
		prepare_Periods_on_X_axis(tempPeriod, list);

		for (int i = 0; i < items.size(); i++) {
			String userTemp = items.get(i);
			for (int j = 0; j < list.size(); j++) {

				session = ConnectDB.getSessionFactory().getCurrentSession();
				transaction = session.beginTransaction();
				try {
					switch (i) {
					case 0: {
						List tempQuantities = session.createSQLQuery("SELECT count(*) from reports_preview WHERE "
								+ "period='" + list.get(j).getColumn1() + "' and agent = '" + userTemp + "'"
								+ " and companyName='" + company + "'" + " and category='" + selectCategory3l + "'")
								.list();

						Long temp = Long.valueOf(tempQuantities.get(0).toString());
						list.get(j).setColumn2(temp);
						break;
					}
					case 1: {

						List tempQuantities = session.createSQLQuery("SELECT count(*) from reports_preview WHERE "
								+ "period='" + list.get(j).getColumn1() + "' and agent = '" + userTemp + "'"
								+ " and companyName='" + company + "'" + " and category='" + selectCategory3r + "'")
								.list();

						Long temp = Long.valueOf(tempQuantities.get(0).toString());
						list.get(j).setColumn3(temp);
						break;
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				session.getTransaction().commit();
				session.close();
			}
		}

		/* data short format */
		for (int i = 0; i < list.size(); i++) {
			String dateShort = list.get(i).getColumn1();
			list.get(i).setColumn1(dateShort.substring(0, dateShort.length() - 3));
			System.out.println(list.get(i).toString());
		}
		return list;
	}

	/**
	 * Private method available in all methods of ChartContainerDAOImpl. Method
	 * prepare in loop empty list row by row for selected range of months. In adding
	 * operation as x-axis are saved periods selected from DB for selected range of
	 * months.
	 *
	 * @param tempPeriod
	 * @param list
	 */
	private void prepare_Periods_on_X_axis(String tempPeriod, List<ChartContainer> list) {
		/* convert range of months from String to int */
		int mountCount = Integer.valueOf(tempPeriod);
		for (int i = 0; i < mountCount; i++) {
			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			try {
				List tempPeriodsList = session.createSQLQuery("SELECT distinct period from reports_preview WHERE "
						+ "TIMESTAMPDIFF(MONTH, period,NOW() )=" + i).list();
				list.add(new ChartContainer(tempPeriodsList.get(0).toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			session.getTransaction().commit();
			session.close();
		}
	}
}