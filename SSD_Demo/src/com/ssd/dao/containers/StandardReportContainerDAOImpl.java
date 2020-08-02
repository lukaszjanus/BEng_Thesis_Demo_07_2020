package com.ssd.dao.containers;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.containers.StandardReportContainer;

/**
 *
 * Class used to create object list from StandardReportContainer-class.
 *
 * Public interface for this class - @see documentation of {@link StandardReportContainerDAO}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 4 kwi 2020
 * 
 */
public class StandardReportContainerDAOImpl implements StandardReportContainerDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method generate table of data-report with filters: - range of months,
	 * company, agent, category.
	 * 
	 * Method return list of object with columns: -id, period, category, count
	 *
	 * @param monthCount     as integer
	 * @param company        string company name
	 * @param selectUser     string agent name
	 * @param selectCategory string category-name
	 * 
	 * @return list of object StandardReportContainer
	 */
	@Override
	public List<StandardReportContainer> getReportAgetnCategory3(int monthCount, String company, String selectUser,
			String selectCategory) {

		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();
		list = getPeriods(list, monthCount, selectCategory, selectUser);

		for (int i = 0; i < list.size(); i++) {
			String query = "SELECT COUNT(*) FROM reports_preview WHERE" + " period='" + list.get(i).getMonth_0()
					+ "' AND agent='" + selectUser + "' AND category in ('" + selectCategory
					+ "') GROUP BY period ORDER BY period desc, Category";

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			try {
				List result = session.createSQLQuery(query).list();
				list.get(i).setCount(result.get(0).toString());

			} catch (Exception e) {
				list.get(i).setCount("0");
			}

			session.getTransaction().commit();
			session.close();
		}
		return list;
	}

	/**
	 * Method prepare List of StandardReportContainer's objects with id, and period
	 * for selected months-range and company.
	 *
	 * @param empty      list of objects StandardReportContainer - prepared in
	 *                   class, where method is called
	 * @param monthCount as int
	 * @param selectUser as String
	 * @param agentName  as String
	 * 
	 * @return list of StandardReportContainer's objects. can be empty if no reports
	 */
	private List<StandardReportContainer> getPeriods(List<StandardReportContainer> list, int monthCount,
			String selectCategory, String selectUser) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		String query = "SELECT DISTINCT period from reports_preview WHERE" + " TIMESTAMPDIFF(MONTH, period,NOW() )<"
				+ (monthCount);

		List periodList = session.createSQLQuery(query).list();

		session.getTransaction().commit();
		session.close();

		for (int i = 0; i < periodList.size(); i++) {
			StandardReportContainer tempObject = new StandardReportContainer();
			tempObject.setMonth_0(periodList.get(i).toString());
			tempObject.setId(String.valueOf(i + 1));
			tempObject.setCategory(selectCategory);
			tempObject.setAgent(selectUser);
			list.add(tempObject);
		}
		return list;
	}

	/**
	 * Method return quantity of tickets with filters: - range of months, company,
	 * agent, category Category can be array with delimiter ',' as multiple-select.
	 * If no report has found by this criteria, method return '0'.
	 *
	 * @param monthCount     as integer
	 * @param company        string company name
	 * @param selectUser     string agent name
	 * @param selectCategory string category-name
	 * 
	 * @return string quantity of tickets by selected criteria.
	 */
	@Override
	public String getAgentsTicketCategoryCount(int monthCount, String selectedCompany, String selectUser,
			String selectCategory) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		String query = "SELECT count(*) from reports_preview WHERE" + " TIMESTAMPDIFF(MONTH, period,NOW() )<"
				+ (monthCount) + " AND companyName='" + selectedCompany + "'" + " AND agent='" + selectUser + "'"
				+ " AND category IN (\"" + selectCategory + "\")" + " ORDER BY period desc";

		List list = session.createSQLQuery(query).list();

		session.getTransaction().commit();
		session.close();

		String result = list.get(0).toString();

		return result;
	}
}
