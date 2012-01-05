package com.xia.quartz.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.xia.quartz.dao.Page;
import com.xia.quartz.model.JobEntity;
import com.xia.quartz.service.JobEntityService;
import com.xia.quartz.service.QuartzService;
import com.xia.quartz.util.ApplicationContextHolder;

/**
 * Servlet implementation class JobProcessServlet
 */
public class JobProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final static String RESULT = "result";
	private static QuartzService quartzService;
	private static JobEntityService jobEntityService;
	private static ObjectMapper mapper = new ObjectMapper();
	private Validator validator;

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
		mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		quartzService = ApplicationContextHolder.getBean("quartzService");
		jobEntityService = ApplicationContextHolder.getBean("jobEntityService");
		validator = ApplicationContextHolder.getBean("validator");
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String jobtype = request.getParameter("id");
		String action = request.getParameter("actionType");
		if (StringUtils.equals(action, "query")) {
			query(request, response);
		} else if (StringUtils.equals(action, "add")) {
			add(request, response);
		} else if (StringUtils.equals(action, "delete")) {
			delete(request, response);
		}else if (StringUtils.equals(action, "modify")) {
			modify(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		try {
			String[] ids = request.getParameterValues("items[]");
			for (String string : ids) {
				jobEntityService.deleteById(Long.valueOf(string));
			}
			mapper.writeValue(response.getOutputStream(), getSuccessResult());
		} catch (Exception e) {
			mapper.writeValue(response.getOutputStream(), getFailResult(e.getMessage()));
		}
		response.getOutputStream().close();
	}

	private void modify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		JobEntity newJob = mapper.readValue(request.getInputStream(), JobEntity.class);
		try {
			newJob.validate(validator);
			JobEntity update=jobEntityService.getById(newJob.getId());
			update.setJobName(newJob.getJobName());
			update.setJobClass(newJob.getJobClass());
			update.setJobCronExpress(newJob.getJobCronExpress());
			update.setJobDesc(newJob.getJobDesc());
			update.setJobClassIsBeanName(newJob.isJobClassIsBeanName());
			jobEntityService.saveJobEntity(update);
			mapper.writeValue(response.getOutputStream(), getSuccessResult());
		} catch (Exception e) {
			mapper.writeValue(response.getOutputStream(), getFailResult(e.getMessage()));
		}
		response.getOutputStream().close();
	}
	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		JobEntity newJob = mapper.readValue(request.getInputStream(), JobEntity.class);
		try {
			newJob.validate(validator);
			jobEntityService.saveJobEntity(newJob);
			mapper.writeValue(response.getOutputStream(), getSuccessResult());
		} catch (Exception e) {
			mapper.writeValue(response.getOutputStream(), getFailResult(e.getMessage()));
		}
		response.getOutputStream().close();
	}

	private void query(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Page<JobEntity> p = new Page<JobEntity>();
		p.setRequestMap(request.getParameterMap());
		p = jobEntityService.getAllJobEntitysAsPage(p);

		response.setContentType("application/json");
		mapper.writeValue(response.getOutputStream(), p);
		response.getOutputStream().close();
	}

	protected Map<String, Result> getSuccessResult(Object extend) {
		Result successResult = Result.getSuccessResult();
		successResult.setExtend(extend);
		return Collections.singletonMap(RESULT, successResult);
	}

	protected Map<String, Result> getSuccessResult() {
		return Collections.singletonMap(RESULT, Result.getSuccessResult());
	}

	protected Map<String, Result> getFailResult(String failMsg) {
		return Collections.singletonMap(RESULT, Result.getFailResult(failMsg));
	}
}
