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
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
<head>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@include file="_info.jsp" %>
<script>
var host_name="<%= hostname %>"
</script>
<title>Solr管理控制台</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="favicon.ico" type="image/ico"></link>
<link rel="shortcut icon" href="favicon.ico" type="image/ico"></link>
<link id="globalheader-stylesheet" rel="stylesheet" href="../css/navigation.css" type="text/css">	
	<link rel="stylesheet" href="../css/base.css" type="text/css">
	<link rel="stylesheet" href="../css/solr.css" type="text/css">
	<style type="text/css">
		html { background-color: #F2F2F2;}
		#main #content { width: 910px; padding-left: 50px;height: 430px;}
		h1 { margin-top:25px; }
		.contacttable { margin-bottom: 10px; }
		.contactleft { font-size: 12px; padding: 3px 15px 3px 3px; }
		.contactright { font-size: 12px; padding: 3px 3px 3px 15px; }
		ul.square li { margin-bottom: 18px; }
		.form-table  input:focus{ border:1px solid #09f}
		.form-table  .search{width: 300px;padding:8px;margin: 3px; border:1px solid #cdcdcd;-moz-border-radius: 3px; -webkit-border-radius: 3px; border-radius:3px;float:left; overflow:hidden;font-size: 14px;font-weight: bold;}
		.form-table input.btn-confirm{ background:url(../css/images/btn.png) no-repeat 0 -38px; width:66px; height:38px; border:0; cursor:pointer; color:#fff; padding:0;padding-bottom:4px; +padding-bottom:0}
		.home_ {font-size: 16px;padding-left: 10px}
	
	
	</style>
	<script type="text/javascript">
	var searchSection = 'global';
	var searchCountry = 'cn';
</script>
<script src="../js/globalnav.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery/jquery-1.7.min.js" type="text/javascript" charset="utf-8"></script>
<script src="../js/jquery/jquery.extend.js" type="text/javascript" charset="utf-8"></script>
</head>

<body  class="privacy">

<nav id="globalheader" class="apple">
	<!--googleoff: all-->
	<ul id="globalnav" role="navigation">
		<li id="gn-apple"><a href="index.jsp"><span>Apple</span></a></li>
		<li id="gn-store"><a href="config.jsp"><span>Store</span></a></li>
		<li id="gn-mac"><a href="/mac/"><span>Mac</span></a></li>
		<li id="gn-ipod"><a href="/ipod/"><span>iPod</span></a></li>
		<li id="gn-iphone"><a href="/iphone/"><span>iPhone</span></a></li>
		<li id="gn-ipad"><a href="/ipad/"><span>iPad</span></a></li>
		<li id="gn-itunes"><a href="/itunes/"><span>iTunes</span></a></li>
		<li id="gn-support" class="gn-last"><a href="/support/"><span>Support</span></a></li>
	</ul>
	<!--googleon: all-->
	<div id="globalsearch">
		<form action="/search/" method="post" class="search" id="g-search"><div class="sp-label">
			<label for="sp-searchtext">Search</label>
			<input type="text" name="q" id="sp-searchtext" accesskey="s" />
		</div></form>
		<div id="sp-magnify"><div class="magnify-searchmode"></div><div class="magnify"></div></div>
		<div id="sp-results"></div>
	</div>
</nav>
