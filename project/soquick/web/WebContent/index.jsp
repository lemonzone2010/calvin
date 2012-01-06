<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>

<% org.apache.solr.core.CoreContainer cores = (org.apache.solr.core.CoreContainer)request.getAttribute("org.apache.solr.CoreContainer"); %>

<html>
<head>
    
    <title>控制台</title>
    
    <link rel="stylesheet" type="text/css" href="css/screen.css">
    <link rel="icon" type="image/ico" href="img/favicon.ico">
    
    <script type="text/javascript">
    
    var app_config = {};
    
    app_config.solr_path = '<%= request.getContextPath() %>';
    app_config.core_admin_path = '<%= cores.getAdminPath() %>';
    app_config.zookeeper_path = 'zookeeper.jsp';
    app_config.schema_path = '/admin/file?file=schema.xml&contentType=text/xml;charset=utf-8';
    app_config.config_path = '/admin/file?file=solrconfig.xml&contentType=text/xml;charset=utf-8';
    
    </script>
    
</head>
<body>
    
    <div id="wrapper">
    
        <div id="header">
            
            <a href="./" id="solr"><span>Apache SOLR</span></a>


            <p id="environment">&nbsp;</p>

        </div>

        <div id="main" class="clearfix">
        
            <div id="content-wrapper">
            <div id="content">
                
                &nbsp;
                
            </div>
            </div>
            
            <div id="menu-wrapper">
            <div id="menu">
                
                <ul>

                    <li id="index" class="global">
                        <p><a href="#/">仪表盘</a></p>
                    </li>

                    <li id="logging" class="global">
                        <p><a href="#/logging">日志</a></p>
                    </li>

                    <li id="cloud" class="global optional">
                        <p><a href="#/cloud">Cloud</a></p>
                    </li>

                    <li id="cores" class="global optional">
                        <p><a href="#/cores">Core Admin</a></p>
                    </li>

                    <li id="java-properties" class="global">
                        <p><a href="#/java-properties">系统属性</a>
                    </li>

                    <li id="threads" class="global">
                        <p><a href="#/threads">线程</a></p>
                    </li>
                    
                </ul>
                
            </div>
            </div>
      
            
        </div>
    
    </div>
    
    <script type="text/javascript" src="js/0_console.js"></script>
    <script type="text/javascript" src="js/1_jquery.js"></script>
    <script type="text/javascript" src="js/jquery.timeago.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/jquery.sammy.js"></script>
    <script type="text/javascript" src="js/jquery.sparkline.js"></script>
    <script type="text/javascript" src="js/jquery.jstree.js"></script>
    <script type="text/javascript" src="js/highlight.js"></script>
    <script type="text/javascript" src="js/script.js"></script>
    
</body>
</html>