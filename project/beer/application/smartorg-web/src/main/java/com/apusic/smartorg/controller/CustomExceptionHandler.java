package com.apusic.smartorg.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 基于HandlerExceptionResolver接口的异常处理类
 * 
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) {
		exception.printStackTrace();
		if (exception instanceof IOException) {
			return new ModelAndView("error");
		} else if (exception instanceof SQLException) {
			return new ModelAndView("error");
		}
		return null;
	}

}