<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.beans.factory.FactoryBean"%>
<%@page import="com.apusic.ebiz.iam.security.access.intercept.FilterSecurityInterceptor"%>
<%@page import="com.apusic.ebiz.iam.security.access.expression.InvocationSecurityMetadataSourceService"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>smartorg测试页面</title>
</head>
<body>
	<div>激活授权</div>

	<%
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
	    InvocationSecurityMetadataSourceService f = (InvocationSecurityMetadataSourceService) ac.getBean("iam_SecurityMetadataSource");
		f.loadResourceDefine();
	 %>
</body>
</html>