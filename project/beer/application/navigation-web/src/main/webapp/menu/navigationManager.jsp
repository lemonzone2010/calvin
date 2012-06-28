<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="/resource/md/css/common.css" type="text/css" />
		<link rel="stylesheet" href="/resource/md/css/nav/navigation.css" type="text/css" />
		<script src="/resource/js/jquery/jquery-1.7.2.js" type="text/javascript" ></script>
		<script src="/resource/js/jquery/ui/jquery.ui.core.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.mouse.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.sortable.js" type="text/javascript"></script>
		<script src="/resource/md/js/nav/navigationManage.js" type="text/javascript" ></script>
		<script src="/resource/static/myext/jquery.extend.js" type="text/javascript"></script>
		<title>菜单管理</title>
	</head>  
	<body>
		<div id="man_zone">
			<div>
		        <div class="actionBar">
		            <div class="float_l">
		            	<span class="sysiconBtn btn-show-cate" onclick="openOrCloseAll('open')">全部展开</span>
		            	<span class="sysiconBtn btn-hide-cate" onclick="openOrCloseAll('close')">全部收起</span>&#160;&#160;
		            </div>
		            <div class="clear"></div>
		        </div>
		        <div class="clear"></div>
		        <div class="finder-head">
		           <div class="span-6">站点栏目名称</div>
		           <div class="span-2">添加</div>
		           <div class="span-2">编辑</div>
		           <div class="span-2">显示</div>
		           <div class="span-2">删除</div>
		           <div class="span-2">拖动排序</div>
		           <input type="hidden" id="appId" name="appId" value="${appId}"/>
		       </div>
			</div>
		 	<div class="main_content">
				<div class="finder">
			  		<div class="finder-list">
			      		<div class="row">
			          		<div class="row-line">
			              		<div class=" text-align span-6">
			                		<div class="margin-left10"><span class="rootFolder">&#160;&#160;&#160;&#160;&#160;</span>根目录</div>
			              		</div>
			              		<div class="span-2"><a title="添加子项"  alt="添加子项" class="add_sub_icon" onclick="addNew('0')"></a></div>
			            	</div>
			      		</div>

			      		<div class="row" style="display: none;" id="add_node_0">
			      			<div class="row-line">
			      				<div class=" text-align add_div">
			                		<div class="margin-left60">
			                			名称：<input type="text" name="name" size="10"/>
			                			<input type="hidden" name="navId" value="0"/>
			                			<input type="hidden" name="level" value="1"/>
			                			<input type="hidden" name="parentId" value="0"/>
				                		<input type="hidden" name="status" value="Y"/>
			                			<input class="input_button" type="button" value="保存" onclick="saveOrUpdate('0')"/>
			                			<input class="input_button" type="button" value="取消" onclick="cancel('0')"/>
			                		</div>
			              		</div>
			      			</div>
			      		</div>
			      		<div id="parent_node">
			      		<c:forEach items="${navigations}" var="navigation" varStatus="status_parent">
			      			<div id="parent_item">
				      		<div class="row">
				    			<div class="row-line">
				          			<div class="text-align span-6">
				               			<div class="margin-left20">
				               				<span class="openFolder" onclick="openOrCloseNode('${navigation.id}');">&#160;&#160;&#160;</span>
				               				<a style="margin-right:10px" href="" class="folder" onclick="openOrCloseNode('${navigation.id}');">${navigation.name}</a>
				                 		</div>
				          			</div>
					          		<div class="span-2"><a title="添加子项"  alt="添加子项" class="add_sub_icon" onclick="addNew('${navigation.id}')"></a></div>
							        <div class="span-2"><a title="编辑" alt="编辑" class="edit_icon" onclick="editRoot('${navigation.id}')"></a></div>
					          		<div class="span-2">
					          				<c:choose>
					          					<c:when test="${navigation.status eq 'Y'}">
					          						<a title="点击禁用此栏目" alt="点击禁用此栏目" class="show_icon" onclick="updateItemStatus('${navigation.id}')"></a>
					          					</c:when>
					          					<c:otherwise>
					          						<a title="点击启用此栏目" alt="点击启用此栏目" class="hide_icon" onclick="updateItemStatus('${navigation.id}')"></a>
					          					</c:otherwise>
					          				</c:choose>
					          		</div>
							        <div class="span-2"><a title="删除" alt="删除" class="detel_icon" onclick="del('${navigation.id}')"></a></div>
							        <div class="span-2"><a title="拖动排序" alt="拖动排序" class="sort_icon" onmousedown="sortEnable()" onmouseup="sortDisable()"></a></div>
				      			</div>
				  	  		</div>
				  	  		<div class="row" style="display: none;" id="add_node_${navigation.id}">
				      			<div class="row-line">
				      				<div class=" text-align add_div">
				                		<div class="margin-left60">
				                			名称：<input type="text" name="name" size="15"/>
				                			<span>URL：<input type="text" name="url" size="35"/></span>
			                				<input type="hidden" name="navId" value="${navigation.id}"/>
				                			<input type="hidden" name="level" value="${navigation.level}"/>
				                			<input type="hidden" name="parentId" value="0"/>
				                			<input type="hidden" name="status" value="${navigation.status}"/>
				                			<input type="hidden" name="sequence" value="${navigation.sequence}"/>
				                			<input class="input_button" type="button" value="保存" onclick="saveOrUpdate('${navigation.id}')"/>
				                			<input class="input_button" type="button" value="取消" onclick="cancel('${navigation.id}')"/>
				                		</div>
				              		</div>
				      			</div>
				      		</div>
				  	  		<div id="child_node_${navigation.id}" style="display: none;">
					  	  		<c:forEach items="${navigation.childrens.toArray()}" var="children" varStatus="status_children">
					  	  			<div id="children_item">
						  	  			<div class="row">
										    <div class="row-line">
										        <div class=" text-align span-6">
										        	<div class="margin-left65">
										          		<a style="margin-right:10px" href="" class="new_icon">${children.name}</a>
										          		<input type="hidden" name="url" value="${children.url}"/>
										          	</div>
										        </div>
										        <div class="span-2"><a width="18" height="18" class="empty_img"></a></div>
										        <div class="span-2"><a title="编辑" alt="编辑" class="edit_icon" onclick="editChild('${children.id}')"></a></div>
										      	<div class="span-2">
										      		<c:choose>
							          					<c:when test="${children.status eq 'Y'}">
							          						<a title="点击禁用此菜单" alt="点击禁用此菜单" class="show_icon" onclick="updateItemStatus('${children.id}')"></a>
							          					</c:when>
							          					<c:otherwise>
							          						<a title="点击启用此菜单" alt="点击启用此菜单" class="hide_icon" onclick="updateItemStatus('${children.id}')"></a>
							          					</c:otherwise>
							          				</c:choose>
										      	</div>
										        <div class="span-2"><a title="删除" alt="删除" class="detel_icon" onclick="del('${children.id}')"></a></div>
										        <div class="span-2"><a title="拖动排序" alt="拖动排序" class="sort_icon" onmousedown="sortEnable()" onmouseup="sortDisable()"></a></div>
										    </div>
										</div>
										<div class="row" style="display: none;" id="add_node_${children.id}">
							      			<div class="row-line">
							      			    <div class=" text-align add_div">
											    	<div class="margin-left65">
							                			名称：<input type="text" name="name" size="15"/>
							                			<span>URL：<input type="text" name="url" size="35"/></span>
					                					<input type="hidden" name="navId" value="${children.id}"/>
							                			<input type="hidden" name="level" value="${children.level}"/>
						                				<input type="hidden" name="parentId" value="${navigation.id}"/>
						                				<input type="hidden" name="status" value="${children.status}"/>
				                						<input type="hidden" name="sequence" value="${children.sequence}"/>
							                			<input class="input_button" type="button" value="保存" onclick="saveOrUpdate('${children.id}')"/>
							                			<input class="input_button" type="button" value="取消" onclick="cancel('${children.id}')"/>
							                		</div>
							              		</div>
							      			</div>
							      		</div>
						      		</div>
					  	  		</c:forEach>
							</div>
							</div>
			      		</c:forEach>
			      		</div>
			  	    </div>
			  	</div>
		  	</div>
		</div>
</body>
</html>