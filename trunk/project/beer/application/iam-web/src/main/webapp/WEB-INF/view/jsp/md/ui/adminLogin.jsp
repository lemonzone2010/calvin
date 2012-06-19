<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登录</title>
<link type="text/css" rel="stylesheet" href="css/md/adminlogin.css" />
<script language="javascript">
function freset(){
   document.getElementById('username').value="";
   document.getElementById('password').value="";
}
</script>
</head>
<body>
<div class="loginname">
	<img src="images/md/loginname.png" />
</div>
<div class="box">
<div class="login">
		<form:form   method="post" id="fm1" name="fm1" commandName="${commandName}" htmlEscape="true">
			<c:if test="${not empty sessionScope.openIdLocalId}">
				<strong>${sessionScope.openIdLocalId}</strong>
				<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
			</c:if>
	    	<p class="login_input" >
	    		<c:if test="${empty sessionScope.openIdLocalId}">
					<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
					<form:input cssClass="input_text" id="username" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
					<label><form:errors path="username" id="status"  /></label>
				</c:if>
	    	</p>
	        <p class="login_input" >
	        	<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
				<form:password cssClass="input_text" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
				<label><form:errors path="password" id="status"  /></label>
	        </p>
	        <p>
	        	<input type="hidden" name="lt" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
	        	<input class="input_btn" name="submit" accesskey="l" value="登录" tabindex="4" type="submit" /> 
                <input class="input_btn" name="reset" accesskey="c" value="重置" tabindex="5" type="button" onclick="javascript:freset();"  />
	        </p>
	        <p class="copyright">锦绣商城 版权所有</p>
		</form:form>
    </div>
</div>
</body>
</html>