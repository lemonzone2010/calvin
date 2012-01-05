package com.xia.quartz.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class JobProcessServlet
 */
public class JobProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ApplicationContext ctx;

	// private SchedulerService schedulerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JobProcessServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		// schedulerService = (SchedulerService)
		// ctx.getBean("schedulerService");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String jobtype = request.getParameter("jobtype");
		String action = request.getParameter("action");
		System.out.println(11);
		/*
		 * if (jobtype.equals("0") && action.equals("add")) {
		 * this.addSimpleTrigger(request, response); } else if
		 * (jobtype.equals("1") && action.equals("add")) {
		 * this.addCronTriggerByExpression(request, response); } else if
		 * (jobtype.equals("2") && action.equals("add")) {
		 * this.addCronTriggerBy(request, response); } else if
		 * (jobtype.equals("100") && action.equals("query")) {
		 * this.getQrtzTriggers(request, response); } else if
		 * (jobtype.equals("200") && action.equals("pause")) {
		 * this.pauseTrigger(request, response); } else if
		 * (jobtype.equals("200") && action.equals("resume")) {
		 * this.resumeTrigger(request, response); } else if
		 * (jobtype.equals("200") && action.equals("remove")) {
		 * this.removeTrigdger(request, response); }
		 */
	}

	/**
	 * 根据名称和组别暂停Tigger
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void removeTrigdger(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// request.setCharacterEncoding("UTF-8");
		String triggerName = URLDecoder.decode(request.getParameter("triggerName"), "utf-8");
		String group = URLDecoder.decode(request.getParameter("group"), "utf-8");

		/*
		 * boolean rs = schedulerService.removeTrigdger(triggerName, group); if
		 * (rs) { response.getWriter().println(0); } else {
		 * response.getWriter().println(1); }
		 */
	}

}
