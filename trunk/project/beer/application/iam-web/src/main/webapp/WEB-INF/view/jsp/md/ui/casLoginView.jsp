<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="includes/top.jsp" />
<script language="javascript">

function freset(){
   document.getElementById('username').value="";
   document.getElementById('password').value="";
}
</script>
	<fmt:bundle basename="iam_system_config" >
		<fmt:message key="server_ip"  var="server_ip"  scope="request"/>
	</fmt:bundle>
	

<form:form   method="post" id="fm1" name="fm1" commandName="${commandName}" htmlEscape="true">
<div class="row margin_top10">
<div class="column grid_16">
  	<div class="mt_form">
		<h2>用户登录</h2>
		<b></b>
	</div>
    </div>
</div>
<div class="row">
<div class="column grid_16">
  <div class="mc_form">
		<div class="entry_form">
			<div class="item">
				<form:errors path="" id="status" element="div" />
			</div>
			<div class="item">
				<span class="label"> <label for="username"><spring:message code="screen.welcome.label.netid" /></label></span>
				<div class="fl">
					<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
					</c:if>
					<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<form:input cssClass="required"  cssStyle="border: 1px solid #BBBBBB;font-size: 14px;height: 16px;padding: 4px 3px; width: 240px;" cssErrorClass="text" id="username" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						<label><form:errors path="username" id="status"  /></label>
					</c:if>
				</div>
			</div><div class="clear"></div>
			<br/>
			<div class="item">
				<span class="label"><label for="password"><spring:message code="screen.welcome.label.password" /></label></span>
				<div class="fl">
					<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
					<form:password cssClass="required" cssStyle="border: 1px solid #BBBBBB;font-size: 14px;height: 16px;padding: 4px 3px; width: 240px;" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
					<label><form:errors path="password" id="status"  /></label>
				</div>
			</div>
			<div class="clear"></div>
			<div class="item"  style="margin-top: 15px;">
				<span class="label">&nbsp;</span>
                <input type="hidden" name="lt" value="${flowExecutionKey}" />
				<input type="hidden" name="_eventId" value="submit" />
                <input class="btn-img btn-entry" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" /> <input class="btn-img btn-entry" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="button" onclick="javascript:freset();"  />
               &nbsp; &nbsp; &nbsp; &nbsp;<label><a class="flk13" href="http://${server_ip}/usersphere/userprofile/passwordReset.faces">忘记密码?</a></label>
			</div><span class="clear"></span>
		</div>
		<!--[if !ie]>form end<![endif]-->
		<div id="guide">
			<h5>还不是锦绣商城用户？</h5>
			<div class="content">现在免费注册成为锦绣商城用户，便能立刻享受便宜又放心的购物乐趣。</div>
			<div class="btn-link btn-personal"><a href="http://${server_ip}/usersphere/register/personRegister.faces">注册新用户</a></div>
			<div class="btns"><a href="http://${server_ip}/usersphere/register/companyRegister.faces" class="flk13">商户注册</a></div>
		</div>
		<!--[if !ie]>guide end<![endif]-->
		<span class="clear"></span>

	</div>
    </div>
</div>
</form:form>
<jsp:directive.include file="includes/bottom.jsp" />