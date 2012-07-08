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
		$('#globalheader').attr('class','store')
	$(document).ready(function() {
		$( "#tabs" ).tabs({
			   select: function(event, ui) { 
				if(ui.index==0){
					getShcema();
				}else{
					getConfig();
				}
			   }
		});
		AC.GlobalNav.Instance = new AC.GlobalNav();
		//SyntaxHighlighter.all();
		getShcema();
	});
		
		function getShcema(){
			var url="../admin/file/?contentType=text/xml;charset=utf-8&file=schema.xml"
				getXml(url,'#tabs-1');
		}
		function getConfig(){
			var url="../admin/file/?contentType=text/xml;charset=utf-8&file=solrconfig.xml"
				getXml(url,'#tabs-2');
		}
		function getXml(url,divid){
				$.get(url,null, function(result){
					$(divid).html('<pre class="brush: xml"></pre>');
					$(divid+' > pre').html($(result).children());
					SyntaxHighlighter.highlight();
				  },'xml').error(function(result) {
					  $(divid+' > pre').html(result.responseText);
					  });
		}
	</script>

<%boolean replicationhandler = !core.getRequestHandlers(ReplicationHandler.class).isEmpty();%>

<div id="container">
		<div id="main">
					<script>
	$(function() {
		$( "#tabs" ).tabs();
	});
	</script>



<div class="demo">

	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">Schema.xml</a></li>
			<li><a href="#tabs-2">Config.xml</a></li>
		</ul>
		<div id="tabs-1">
				<pre id="schema"  class="brush: xml">
				
				</pre>
		</div>
		<div id="tabs-2">
			<pre id="config"  class="brush: xml">
			
			</pre>
		</div>
	</div>

</div><!-- End demo -->

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
