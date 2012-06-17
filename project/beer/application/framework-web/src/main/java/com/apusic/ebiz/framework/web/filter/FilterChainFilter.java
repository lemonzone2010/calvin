package com.apusic.ebiz.framework.web.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.filter.GenericFilterBean;


public class FilterChainFilter extends GenericFilterBean implements ApplicationContextAware, FilterChain {

	private ApplicationContext applicationContext;

	private List<String> filters;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		for (String filterName : filters){
			Filter filter = applicationContext.getBean(filterName, Filter.class);
			filter.doFilter(request, response, filterChain);
		}
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}

}
