<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header" class="span-24 last">
	<div id="title">
		<h2>系统</h2>
		<h3>--</h3>
	</div>
	<div id="menu">
		<ul>
			<li><a href="${ctx}/user">用户管理</a></li>
			<li><a href="${ctx}/role">角色管理</a></li>
			<li><a href="${ctx}/resource">资源管理</a></li>
		</ul>
	</div>
</div>