<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="header" class="span-24 last">
	<div id="title">
		<h2>Antia家具管理系统</h2>
		<h3>--效率 方便 快捷</h3>
	</div>
	<div id="menu">
		<ul>
			<li><a href="${ctx}/account/user/list">帐号列表</a></li>
			<li><a href="${ctx}/account/group/list">权限组列表</a></li>
			<li><a href="${ctx}/logout">退出登录</a></li>
		</ul>
	</div>
</div>