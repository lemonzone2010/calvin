package com.xia.search.solr.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xia.search.solr.service.SolrContext;
import com.xia.search.solr.service.SolrService;
import com.xia.search.solr.service.SolrServiceImpl;
import com.xia.search.solr.util.JasonUtil;

public class SuggestDemoServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String q = req.getParameter("term");
		SolrService service=new SolrServiceImpl(SolrContext.SOLR_SERVER_URL, null);
		try {
			List<String> suggest = service.suggest(q);
			
			resp.setContentType("text/xml");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().print(JasonUtil.toJsonString(suggest));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
