package org.jasig.cas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeneralData extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String callback = request.getParameter("jsoncallback");
		String username = (String) request.getSession().getAttribute("username");
		if(username!=null){
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(callback + "({'username':'"+username+"'})");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
