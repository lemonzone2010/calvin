<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="includes/top.jsp" />
<fmt:bundle basename="iam_system_config" >
<fmt:message key="server.ip" var="server_ip"  scope="request"/>
</fmt:bundle>

<c:redirect url="http://${server_ip}/smartorg-web/"></c:redirect>