<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>欢迎登陆锦绣商城登陆</title>
        <link type="text/css" rel="stylesheet" href="css/md/form.css" />
        <link type="text/css" rel="stylesheet" href="css/md/grid.css" />
        <link type="text/css" rel="stylesheet" href="css/md/main.css" />
        <link type="text/css" rel="stylesheet" href="css/md/table.css" />
        <link type="text/css" rel="stylesheet" href="css/md/all.css" />
         <script type="text/javascript" src="js/common_rosters.js"></script>
    
		
	</head>
	
	<body id="cas" onload="init();showShoppingcartCount();">
	

	<fmt:bundle basename="iam_system_config"  >
		<fmt:message key="server.ip" var="server_ip"  scope="session"/>
	</fmt:bundle>
	
		<div class="row topmessage">
		  <div class="column grid_6"> &#160; </div>
		  <div class="column grid_10" >
		  	<div class="float_right">
				您好，欢迎来到锦绣商城！<a href="http://${server_ip}:7001/iam/login">[请登录]</a>，新用户？<a href="http://${server.ip}/usersphere/register/personRegister.faces">[免费注册]</a>
			 	| <a href="http://${server_ip}/usersphere/deliveryaddr/deliveryAddr.faces" style="color:black;">我的锦绣</a>
			 	| <a href="http://${server_ip}/emarket/shoppingcart/shoppingCart.faces">购物车<span id="shoppingCartCookieCount">（0）</span></a>
				
				</div>
		    </div>
		</div>
		<div class="row margin_top10">
		  <div class="column grid_1"> &nbsp; </div>
		  <div class="column grid_3"><a href="http://${server_ip}/product/index.html"><img src="images/md/toplogo.jpg" /></a></div>
		  <div class="column grid_12">
		    &nbsp;
		  </div>
		</div>

