package com.ssd.controller.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ssd.dao.containers.ChartContainerDAO;
import com.ssd.dao.containers.ChartContainerDAOImpl;
import com.ssd.entity.containers.ChartContainer;

/**
 * Servlet prepare data for charts. Only method 'get', run as Ajax, works for Administrator and Manager roles.
 *
 * @author Lukasz Janus
 * @version 1.0
 * @date 1 lut 2020
 * 
 */
@WebServlet({"/adminTools/chart", "/manage/chart"})
public class chartController extends HttpServlet {

	/**
	 * Adds a default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Standard Servlet Get-Method with request and response for administrator and manger.
	 * Method prepare data for charts as gson-objects. 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String tempPeriod = req.getParameter("monthCount");
		String companyL = req.getParameter("companyL");
		String action = req.getParameter("action");

		ChartContainerDAO report = new ChartContainerDAOImpl();
		List<ChartContainer> list = new ArrayList<ChartContainer>();

		switch (action) {
		case "tab1": {
			
			String companyR = req.getParameter("company1r");
			companyL = companyL + ";" + companyR;
			list = report.prepareChartCompany(tempPeriod, companyL);
			break;
		}
		case "tab2": {

			String selectUser2l = req.getParameter("selectUser2l");
			String selectUser2r = req.getParameter("selectUser2r");

			String compareFlag2 = req.getParameter("compareFlag2");
			Boolean flag = Boolean.valueOf(compareFlag2);

			if (flag == true) {
				selectUser2l = selectUser2l + ";" + selectUser2r;
			}

			list = report.prepareChartUser(tempPeriod, companyL, selectUser2l);
			break;
		}
		case "tab3": {
			System.out.println(action);
			String selectUser3l = req.getParameter("selectUser3l");
			String selectCategory3l = req.getParameter("selectCategory3l");
			String selectCategory3r = req.getParameter("selectCategory3r");
			
			String compareFlag3 = req.getParameter("compareFlag3");
			Boolean flag = Boolean.valueOf(compareFlag3);

			if (flag == true) {
				
				String selectUser3r = req.getParameter("selectUser3r");
				selectUser3l = selectUser3l + ";" + selectUser3r;
			}

			list = report.prepareChartUserCategory(tempPeriod, companyL, selectUser3l, selectCategory3l, selectCategory3r);			
			break;
		}
		}

		Gson gson = new Gson();
		res.setContentType("application/json");

		PrintWriter out = res.getWriter();
		out.println(gson.toJson(list));
	}
}
