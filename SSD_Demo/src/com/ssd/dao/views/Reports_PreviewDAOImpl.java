package com.ssd.dao.views;

import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ssd.config.jdbc.ConnectDB;
import com.ssd.entity.containers.StandardReportContainer;
import com.ssd.entity.viewsDB.Reports_Preview;

/**
 * Class used to get data view from db with uploaded reports.
 * 
 * Interface: @see documentation of {@link Reports_PreviewDAO} ORM class with
 * field descriptions: @see documentation of {@link Reports_Preview}
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 28 lut 2020
 * 
 */
public class Reports_PreviewDAOImpl implements Reports_PreviewDAO {

	/**
	 * Hibernate object to connect with DB by static SessionFactory
	 */
	private Session session = null;

	/**
	 * Hibernate object to do transaction by static SessionFactory
	 */
	private Transaction transaction;

	/**
	 * Method load data from db - all uploaded reports and return as object list.
	 *
	 * @return list of Reports_Preview-objects, list can be null
	 */
	@Override
	public List<Reports_Preview> getDataReports() {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<Reports_Preview> list = new ArrayList<Reports_Preview>();

		list = session.createQuery("from Reports_Preview").getResultList();
		session.getTransaction().commit();

		session.close();

		return list;
	}

	/**
	 * Method get available list of agents from column agent in uploaded reports.
	 * List can be empty if any report hasn't been uploaded yet for given
	 * month-range or any company selected. If ticket hasn't assigned any agent, to
	 * list is added value 'no assigned'.
	 *
	 * @param month           - range of months as int
	 * @param selectedCompany - to select agents only from chosen company
	 * 
	 * @return String list
	 */
	@Override
	public List<String> getAgents(int month, String selectedCompany) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<String> list = session
				.createSQLQuery(
						"SELECT DISTINCT agent FROM reports_preview" + " WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"
								+ month + " AND companyName in (\"" + selectedCompany + "\")" + " ORDER BY agent")
				.list();

		session.getTransaction().commit();
		session.close();

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			if (list.get(i).length() < 2) {
				list.set(i, "No agent assigned");
			}
		}

		if (listSize == 0) {
			list.add("No agents for selected period");
		}
		return list;
	}

	/**
	 * Method get available Companies from column company in uploaded reports. List
	 * can be empty if any report hasn't been uploaded yet.
	 * 
	 * @param monthCount - range of months as int
	 * 
	 * @return String list
	 * 
	 */
	@Override
	public List<String> getCompanies(int month) {
		List<String> list = new ArrayList<String>();

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		list = this.session.createSQLQuery(
				"SELECT DISTINCT companyName from Reports_Preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<" + month
						+ " order by companyName")
				.list();

		session.getTransaction().commit();
		session.close();
		return list;
	}

	/**
	 * to check - probably replaced and not used
	 * 
	 * Method get available Periods from column period in uploaded reports. List can
	 * be empty if any report hasn't been uploaded yet.
	 * 
	 * @return String list
	 * 
	 */
	@Override
	public List<String> getUploadedPeriods() {
		List<String> list = new ArrayList<String>();

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		list = this.session.createSQLQuery("SELECT DISTINCT period from Reports_Preview").list();

		session.getTransaction().commit();
		session.close();
		return list;
	}

	/**
	 * Method get date of uploaded report from column uploaded_date in uploaded
	 * reports. List can be empty if any report hasn't been uploaded yet.
	 * 
	 * @return String list
	 * 
	 */
	@Override
	public List<String> getUploadedDate() {
		List<Date> listDate = new ArrayList<Date>();

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		listDate = this.session.createSQLQuery("SELECT DISTINCT upload_date from Reports_Preview").list();

		session.getTransaction().commit();
		session.close();

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < listDate.size(); i++) {
			list.add(listDate.get(i).toString());
		}
		return list;
	}

	/**
	 * Method get Category/Description report from column category in uploaded
	 * reports. List can be empty if any report hasn't been uploaded yet.
	 * 
	 * @param monthCount       - range of months as int
	 * @param selectedCompanyL - categories available only for selected company
	 * 
	 * @return String list
	 * 
	 */
	public List<String> getCategory(int monthCount, String selectedCompany) {
		List<String> list = new ArrayList<String>();

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		list = this.session.createSQLQuery(
				"SELECT DISTINCT category from Reports_Preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<" + monthCount
						+ " AND companyName='" + selectedCompany + "' ORDER BY category")
				.list();

		session.getTransaction().commit();
		session.close();
		return list;
	}

	/**
	 * Method return count of uploaded tickets from given counts of months
	 *
	 * @param monthCount - range of months as int
	 * @param companies  - list of company
	 * 
	 * @return count as string
	 */
	@Override
	public String getCount(int monthCount, String companies) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();
		
		if (companies.equals("All")) {

			List count = session
					.createSQLQuery("select count(*) from Reports_Preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"
							+ monthCount + " " + "AND TIMESTAMPDIFF(MONTH, period,NOW() )>=" + (monthCount - 18))
					.list();
			String result = count.get(0).toString();

			session.getTransaction().commit();
			session.close();

			return result;

		} else {

			String tempCompanies = "'" + companies.replace(";", "','") + "'";

			List count = session.createSQLQuery("select count(*) from Reports_Preview WHERE " + "companyName in ("
					+ tempCompanies + ") AND" + " TIMESTAMPDIFF(MONTH, period,NOW() )<" + monthCount
					+ " AND TIMESTAMPDIFF(MONTH, period,NOW() )>=" + (monthCount - 18)).list();
			String result = count.get(0).toString();

			session.getTransaction().commit();
			session.close();
			return result;
		}
	}

	/**
	 * Get Count of tickets - columns group by CompanyName: Count, CompanyName
	 *
	 * @param monthCount      int
	 * @param selectedCompany as String with delimiter ';'
	 * 
	 * @return list Object<?> (row can be parsed to String)
	 */
	@Override
	public List<?> getCountPeriodCompany(int monthCount, String companyName) {

		String query = "select id, companyName, COUNT(*) from reports_preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"
				+ monthCount + " GROUP BY companyName";
		if (!companyName.equals("All") && !companyName.equals("")) {
			String tempCompanies = "\"" + companyName.replace(";", "\",\"") + "\"";
			query = "select  id, companyName,COUNT(*) from reports_preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"
					+ monthCount + " and companyName in(" + tempCompanies + ") GROUP BY companyName";
		}

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<?> list = session.createSQLQuery(query).list();

		session.getTransaction().commit();
		session.close();

		/* get row and set column with id [0] */
		for (int i = 0; i < list.size(); i++) {
			Object[] row = (Object[]) list.get(i);
			row[0] = i + 1;
		}
		return list;
	}

	/**
	 * Get Count of tickets - columns group by periods and companyName
	 *
	 * Displayed Columns: No, period, CompanyName, Count.
	 *
	 * @param monthCount      as int
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 * 
	 * @return list Object<StandardReportContainer> (row can be parsed to String)
	 */
	@Override
	public List<StandardReportContainer> getCompanyCountByPeriodsLists(int monthCount, String companyName) {

		String query = "select distinct period, + '" + companyName + "', COUNT(case when companyName='" + companyName
				+ "' then 0 END ) AS 'Count'" + " from reports_preview WHERE TIMESTAMPDIFF(MONTH, period,NOW() )<"
				+ (monthCount) + " GROUP BY period, companyName ORDER BY period desc, COUNT asc";

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<?> result = session.createSQLQuery(query).list();

		session.getTransaction().commit();
		session.close();

		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();

		/* transform to StandardReportContainer */
		for (int i = 0; i < result.size(); i++) {
			Object[] row = (Object[]) result.get(i);
			list.add(new StandardReportContainer(row[0].toString(), row[1].toString(), row[2].toString()));
			// System.out.println(row[0].toString());
		}

		/* remove duplicate, add missing periods */

		for (int i = 0; i < list.size() - 1; i++) {

			String tempCompany = list.get(i).getCompanyName();
			String tempMonth = list.get(i).getMonth_0();

			for (int j = i + 1; j < list.size(); j++) {

				if (list.get(j).getCompanyName().equals(tempCompany) && list.get(j).getMonth_0().equals(tempMonth)) {
					list.remove(i);
				}
			}
		}

		/* add id for display */
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setId(String.valueOf(i + 1));

		}
		return list;
	}

	public static final long getMonthsDifference(Date date1, Date date2) {
		YearMonth m1 = YearMonth.from(date1.toInstant().atZone(ZoneOffset.UTC));
		YearMonth m2 = YearMonth.from(date2.toInstant().atZone(ZoneOffset.UTC));

		return m1.until(m2, ChronoUnit.MONTHS) + 1;
	}

	/**
	 * Get StandardReportContainer list with companies: active and unactive, but
	 * with uploaded reports in db
	 * 
	 * Container StandardReportContainer has initialized in this method only id and
	 * companyName.
	 * 
	 * Period - last 18 months: - last three months - quarter before three months -
	 * year before three months and quarter
	 *
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 * 
	 * @return list StandardReportContainer, list can be empty
	 */
	@Override
	public List<StandardReportContainer> getCompanyFromReports(String selectedCompany) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List<String> list = new ArrayList<String>();

		if (selectedCompany.contentEquals("All")) {
			list = session.createSQLQuery("select  r.companyName FROM reports_preview r\r\n"
					+ "WHERE   TIMESTAMPDIFF(MONTH, r.period,NOW() ) < 18 UNION\r\n" + "\r\n"
					+ "SELECT c.companyName  FROM company c\r\n" + "WHERE enabled = 1\r\n" + "\r\n"
					+ "ORDER BY companyName").list();
		} else {

			String tempCompanies = "\"" + selectedCompany.replace(";", "\",\"") + "\"";

			list = session.createSQLQuery("select  r.companyName FROM reports_preview r\r\n"
					+ "WHERE   TIMESTAMPDIFF(MONTH, r.period,NOW() ) < 18 and companyName in (" + tempCompanies
					+ ") UNION\r\n" + "\r\n" + "SELECT c.companyName  FROM company c\r\n"
					+ "WHERE enabled = 1  and companyName in (" + tempCompanies + ")" + "\r\n" + "ORDER BY companyName")
					.list();

		}

		session.getTransaction().commit();
		session.close();

		List<StandardReportContainer> result = new ArrayList<StandardReportContainer>();

		for (int i = 0; i < list.size(); i++) {

			StandardReportContainer temp = new StandardReportContainer();
			String id = String.valueOf(i + 1);
			temp.setId(id);
			temp.setCompanyName(list.get(i));

			result.add(temp);
		}
		return result;
	}

	/**
	 * Get counts for standard periods (last 18 months): - last three months (start
	 * from current month) - quarter before three months - year before three months
	 * and quarter
	 *
	 *
	 * @param object          list from method getCompanyFromReports
	 * @param startMonth      as int, current month is '0'
	 * @param selectedCompany - companies Names as one String with delimiter ';'
	 * 
	 * @return full list StandardReportContainer, list can be empty
	 */
	@Override
	public List<StandardReportContainer> getStandardCounts(List<StandardReportContainer> list, int startMonth) {

		/* first month: */
		for (int i = 0; i < list.size(); i++) {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			String month = String.valueOf(startMonth);

			String tempCompany = list.get(i).getCompanyName();
			List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
					+ tempCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) =" + month).list();

			list.get(i).setMonth_0(count.get(0).toString());

			session.getTransaction().commit();
			session.close();

		}

		/* second month: */

		for (int i = 0; i < list.size(); i++) {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			String tempCompany = list.get(i).getCompanyName();

			String month = String.valueOf(startMonth + 1);

			List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
					+ tempCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) =" + month).list();

			list.get(i).setMonth_1(count.get(0).toString());

			session.getTransaction().commit();
			session.close();

		}

		/* thirt month: */

		for (int i = 0; i < list.size(); i++) {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			String tempCompany = list.get(i).getCompanyName();

			String month = String.valueOf(startMonth + 2);

			List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
					+ tempCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) =" + month).list();

			list.get(i).setMonth_2(count.get(0).toString());

			session.getTransaction().commit();
			session.close();

		}

		/* quarter: */

		for (int i = 0; i < list.size(); i++) {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			String tempCompany = list.get(i).getCompanyName();

			int monthStart = startMonth + 2;

			int monthFinish = startMonth + 6;
			List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
					+ tempCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) >" + monthStart
					+ " and TIMESTAMPDIFF(MONTH, period,NOW() ) <" + monthFinish).list();

			list.get(i).setQuarter(count.get(0).toString());

			session.getTransaction().commit();
			session.close();

		}

		/* year: */

		for (int i = 0; i < list.size(); i++) {

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();

			String tempCompany = list.get(i).getCompanyName();


			int monthStart = startMonth + 5;
			int monthFinish = startMonth + 18;

			List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
					+ tempCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) > " + monthStart
					+ " and TIMESTAMPDIFF(MONTH, period,NOW() ) < " + monthFinish).list();

			list.get(i).setYear(count.get(0).toString());

			session.getTransaction().commit();
			session.close();
		}
		return list;
	}

	/**
	 * Method check quantity of agent in selected company and selected range month.
	 *
	 * @param monthCount
	 * @param selectedCompany
	 * @param agentName
	 * 
	 * @return count of tickets, can be null.
	 */
	@Override
	public String getAgentsTicketCount(int monthCount, String selectedCompany, String agentName) {

		session = ConnectDB.getSessionFactory().getCurrentSession();
		transaction = session.beginTransaction();

		List count = session.createSQLQuery("select  COUNT(*) from reports_preview WHERE companyName = '"
				+ selectedCompany + "' and TIMESTAMPDIFF(MONTH, period,NOW() ) < " + monthCount + " and agent in ('"
				+ agentName + "')").list();

		String result = count.get(0).toString();

		session.getTransaction().commit();
		session.close();
		return result;
	}

	/**
	 * Method check quantity of agent in selected company and selected range month
	 * and create object-list StandardReportContainer for every month.
	 *
	 * @param monthCount      as int
	 * @param selectedCompany as String
	 * @param agentName       as String
	 *
	 * @return object-list of StandardReportContainer, list can be empty
	 */
	@Override
	public List<StandardReportContainer> getAgentsTicketPeriodCount(int monthCount, String selectedCompany,
			String agentName) {

		List<StandardReportContainer> list = new ArrayList<StandardReportContainer>();

		/* get periods with reports for selected company */

		list = getPeriods(list, monthCount, agentName);

		for (int i = 0; i < list.size(); i++) {

			String query = "SELECT COUNT(*) from reports_preview WHERE" + " period='" + list.get(i).getMonth_0()
					+ "' AND companyName = '" + selectedCompany + "' " + "and agent in ('" + agentName + "')";

			session = ConnectDB.getSessionFactory().getCurrentSession();
			transaction = session.beginTransaction();
			try {
				List result = session.createSQLQuery(query).list();
				list.get(i).setCount(result.get(0).toString());

			} catch (Exception e) {
				// System.out.println("empty "+e);
			}
			session.getTransaction().commit();
			session.close();
		}
		return list;
	}

	/**
	 * Method prepare List of StandardReportContainer's objects with periods and
	 * agentName with selected months-range.
	 *
	 * @param empty      list of objects StandardReportContainer - prepared in
	 *                   class, where method is called
	 * @param monthCount as int
	 * @param agentName  as String
	 * 
	 * @return list of StandardReportContainer's objects. can be empty if no reports
	 */
	private List<StandardReportContainer> getPeriods(List<StandardReportContainer> list, int monthCount,
			String agentName) {

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
			tempObject.setAgent(agentName);
			list.add(tempObject);
		}
		return list;
	}
}
