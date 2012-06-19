package com.apusic.ebiz.iam.cas;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.session.HashMapBackedSessionMappingStorage;
import org.jasig.cas.client.session.SessionMappingStorage;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;

public class SingleSignOutFilter extends AbstractConfigurationFilter {
	private String artifactParameterName;
	private static SessionMappingStorage SESSION_MAPPING_STORAGE = new HashMapBackedSessionMappingStorage();
	private static Log log = LogFactory.getLog(SingleSignOutFilter.class);

	public SingleSignOutFilter() {
		this.artifactParameterName = "ticket";
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		setArtifactParameterName(getPropertyFromInitParams(filterConfig,
				"artifactParameterName", "ticket"));
		init();
	}

	public void init() {
		CommonUtils.assertNotNull(this.artifactParameterName,
				"artifactParameterName cannot be null.");
		CommonUtils.assertNotNull(SESSION_MAPPING_STORAGE,
				"sessionMappingStorage cannote be null.");
	}

	public void setArtifactParameterName(String artifactParameterName) {
		this.artifactParameterName = artifactParameterName;
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final String logoutRequest = CommonUtils.safeGetParameter(request,
				"logoutRequest");
		Enumeration ff = request.getParameterNames();
		String a = request.getQueryString();
		if (CommonUtils.isNotBlank(logoutRequest)) {
			final String sessionIdentifier = XmlUtils.getTextForElement(
					logoutRequest, "SessionIndex");

			if (CommonUtils.isNotBlank(sessionIdentifier)) {
				final HttpSession session = SESSION_MAPPING_STORAGE
						.removeSessionByMappingId(sessionIdentifier);

				if (session != null) {
					String sessionID = session.getId();
					try {
						session.invalidate();
					} catch (final IllegalStateException e) {

					}
				}
			}
		}

		else {
			final String artifact = CommonUtils.safeGetParameter(request,
					this.artifactParameterName);
			final HttpSession session = request.getSession(false);

			if (CommonUtils.isNotBlank(artifact) && session != null) {
				try {
					SESSION_MAPPING_STORAGE
							.removeBySessionById(session.getId());
				} catch (final Exception e) {

				}
				SESSION_MAPPING_STORAGE.addSessionById(artifact, session);
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void setSessionMappingStorage(SessionMappingStorage storage) {
		SESSION_MAPPING_STORAGE = storage;
	}

	public static SessionMappingStorage getSessionMappingStorage() {
		return SESSION_MAPPING_STORAGE;
	}

	public void destroy() {
	}
}
