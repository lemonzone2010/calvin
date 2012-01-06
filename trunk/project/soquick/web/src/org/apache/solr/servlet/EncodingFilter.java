package org.apache.solr.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;

public class EncodingFilter implements Filter {
	private String characterEncoding = "UTF-8";
	private static final String CHARACTER_ENCODING = "characterEncoding";

	public EncodingFilter() {
		System.out.println("----inited encoding filter----");
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding(characterEncoding);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig config) throws ServletException {
		String enc = config.getInitParameter(CHARACTER_ENCODING);
		if (!StringUtils.isBlank(enc)) {
			characterEncoding = enc;
		}
	}

}
