<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><sitemesh:write property='title'/>-XX管理系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" /><%--

<link href="/resource/static/jquery-validation/1.9.0/milk.css" type="text/css" rel="stylesheet" />
--%><link href="/resource/static/mini-web.css" type="text/css" rel="stylesheet" />
<%--<link href="/resource/static/blueprint/1.0.1/screen.css" type="text/css" rel="stylesheet" media="screen, projection" />
--%><link href="/resource/static/blueprint/1.0.1/print.css" type="text/css" rel="stylesheet" media="print" />
<!--[if lt IE 8]><link href="/resource/static/blueprint/1.0.1/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
<link href="/resource/static/blueprint/src/grid.css" type="text/css" rel="stylesheet" media="screen, projection" />
<%--<link href="/resource/static/blueprint/src/forms.css" type="text/css" rel="stylesheet" media="screen, projection" />--%>
<link href="/resource/static/blueprint/src/reset.css" type="text/css" rel="stylesheet" media="screen, projection" />
<link href="/resource/static/blueprint/src/typography.css" type="text/css" rel="stylesheet" media="screen, projection" />

<link href="/resource/static/jquery-jquery-1.8.6/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link href="/resource/static/jquery-jquery-1.8.6/themes/base/jquery.ui.dialog.css" type="text/css" rel="stylesheet" />
<link href="/resource/static/jquery-jquery-1.8.6/themes/redmond/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="/resource/static/jquery-easyui-1.2.6/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="/resource/static/jquery-easyui-1.2.6/themes/icon.css">

<link href="/resource/static/jquery.json-2.3.js" type="text/css" rel="stylesheet" />
<script src="/resource/static/jquery-jquery-1.8.6/jquery-1.6.2.js" type="text/javascript"></script>

<script src="/resource/static/jquery-easyui-1.2.6/jquery.easyui.min.js" type="text/javascript"></script>
<script src="/resource/static/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="/resource/static/myext/jquery.extend.js" type="text/javascript"></script>
<script src="/resource/xia/js/easyui.extend.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/resource/css/easy.ui.my.css">

<sitemesh:write property='head' />
</head>

<body>
	<div class="container">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div id="content">
			<div class="span-24 last">
				<sitemesh:write property='body' />
			</div>
			<%@ include file="/WEB-INF/layouts/footer.jsp"%>
		</div>
	</div>
</body>
</html>