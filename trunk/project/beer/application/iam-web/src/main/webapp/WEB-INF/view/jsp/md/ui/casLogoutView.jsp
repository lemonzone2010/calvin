<jsp:directive.include file="includes/top.jsp" />
<%	String flag = (String)request.getAttribute("adminFlag"); 
	if("Y".equals(flag)){
	%>
		<c:redirect url="/iam/login?flag=Y"></c:redirect>
	<% 
	}else{
	%>
		<c:redirect url="/iam/login"></c:redirect>
	<%
	}
%>

