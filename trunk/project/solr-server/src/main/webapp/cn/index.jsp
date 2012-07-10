<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%-- $Id: index.jsp 1089340 2011-04-06 07:49:14Z uschindler $ --%>
<%-- $Source: /cvs/main/searching/SolrServer/resources/admin/index.jsp,v $ --%>
<%-- $Name:  $ --%>

<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="org.apache.solr.request.SolrRequestHandler"%>
<%@ page import="org.apache.solr.handler.ReplicationHandler" %>

<%-- jsp:include page="header.jsp"/ --%>
<%-- do a verbatim include so we can use the local vars --%>
<%@include file="header.jsp" %>
<link type="text/css" rel="stylesheet" href="../js/syntaxhighlighter_3.0.83/styles/shCoreEclipse.css"/>
<link type="text/css" rel="stylesheet" href="../js/jquery-ui-1.8.21/css/redmond/jquery-ui-1.8.21.custom.css"/>
<script type="text/javascript" src="../js/syntaxhighlighter_3.0.83/scripts/shCore.js"></script>
<script type="text/javascript" src="../js/syntaxhighlighter_3.0.83/scripts/shBrushXml.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.21/js/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.form.js"></script>
<script type="text/javascript">
	AC.GlobalNav.Instance = new AC.GlobalNav();
</script>
	<script type="text/javascript">
	$(document).ready(function() {
		SyntaxHighlighter.all();
	});
	function doQuery(){
		if($('#queryForm').find('input[name=q]').val().trim().length==0){
			alert('必须要输入查询条件。');
			return false;
		}
		$('#xmlcode').html('<pre id="preText"  class="brush: xml"></pre>');
		var url="../select/";
		var data=$('#queryForm').formSerialize();
		$('#requestUrl').text("查询URL：http://localhost/solr/select/?"+$('#queryForm').formSerialize());
		$.get(url,data, function(result){
			$('#xmlcode').html('<pre class="brush: xml"></pre>');
			$('pre').html($(result).children());
			SyntaxHighlighter.highlight();
			resizeContent();
		  },'xml').error(function(result) {
			  $('pre').html(result.responseText);
			  });
		
	}
	</script>

<%boolean replicationhandler = !core.getRequestHandlers(ReplicationHandler.class).isEmpty();%>
		<form id='queryForm'  name='queryForm' method="GET" action="../select/" accept-charset="UTF-8" class="form-table">
<div id="globalfooter">
		<div id="breadory">
						<input name="version" type="hidden" value="2.2">
						<input name="indent" type="hidden" value="on">
			<ol id="breadcrumbs1">
				<li class="home_">Solr查询</li>
				<li><input type="text" class="search" name="q" value="<%= defaultSearch %>">
				<input name="start" type="text" class="search" value="0" style="width: 20px;">
						<input name="rows" type="text" class="search" value="10" style="width: 20px;">条
				</li>
				<li> 
				
				<input class="btn-confirm" type="submit" value="查询" 
				        	onclick="doQuery();return false; "></li>
			<li><img src="../css/images/help.png">查询格式:[field:内容],如name:爱</li>
			<li style="float: right;"><img src="../css/images/solr_small.png" style="width: 60px"></li>
			</ol>
		</div><!--/breadory-->
</div>
			</form>
<div id="container">
		<div id="main">
			<div id="content" class="content">
			<div id="requestUrl"> </div>
				<div id="xmlcode">
					<pre  class="brush: xml">
					<xml>Solr has started,pls enjoy it...</xml>
						
					Solr Admin (<%= collectionName %>)
					<%= enabledStatus==null ? "" : (isEnabled ? " - Enabled" : " - Disabled") %> </h1>
					
					<%= hostname %>:<%= port %>
					cwd=<%= cwd %>  SolrHome=<%= solrHome %>
					
					<%String cachingStatus = " HTTP caching is ";  %>
					<%= cachingEnabled ? cachingStatus + " ON": cachingStatus + " OFF" %>
					</pre>
										
				 </div>
			</div><!--/content-->
		</div><!--/main-->
</div><!--/container-->


<div id="globalfooter">
	<div class="gf-sosumi">
		<p>Copyright © 2012 Xia Inc. All rights reserved.</p>
		<ul class="piped">
			<li>当前时间: <%= new Date().toLocaleString() %></li>
			<li> 服务器启动时间: <%= new Date(core.getStartTime()).toLocaleString() %></li>
		</ul>
	</div>
</div>
</body>
</html>
