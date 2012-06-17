package com.apusic.ebiz.framework.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandler {
	void handle(HttpServletRequest request, HttpServletResponse response, Exception exception);
}
