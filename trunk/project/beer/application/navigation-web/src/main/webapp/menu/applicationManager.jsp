<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="/resource/md/css/common.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/resource/md/css/_common.css" />
		<link rel="stylesheet" href="/resource/md/css/nav/navigation.css" type="text/css" />
		<script src="/resource/js/jquery/jquery.js" type="text/javascript" ></script>
		<script src="/resource/js/jquery/ui/jquery.ui.core.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.mouse.js" type="text/javascript"></script>
		<script src="/resource/js/jquery/ui/jquery.ui.sortable.js" type="text/javascript"></script>
		<script src="/resource/md/js/nav/applicationInfo.js" type="text/javascript" ></script>
		<script src="/resource/static/myext/jquery.extend.js" type="text/javascript"></script>
		<title>应用管理</title>
	</head>
	<w:page title="应用管理">
		<div id="man_zone">
		<w:form id="infoFrom">
			<div class="actionBar">
				<div class="actionItems" style="height: 28px;">
					<span class="sysiconBtn" onclick="add()">新增应用</span>
				</div>
			</div>
			<div class="clear"></div>
			<div class="finder-head row_height">
				<div class="span-1">序号</div>
				<div class="span-4">名称</div>
				<div class="span-3">状态</div>
				<div class="span-7">操作</div>
				<div class="span-2">拖动排序</div>
			</div>
			<ajax:updater id="dataUpdateInfo">
				<div class="main_content">
					<div class="finder">
						<div class="finder-list">
							<div class="row" id="add_info" style="display: none;">
								<div class="row-line">
							  		<div class="span-1">&#160;</div>
							  		<div class="span-4"><input id="new_name" size="16" /></div>
							  		<div class="span-3">启用</div>
							  		<div class="span-7">
							  			<input class="input_button" type="button" value="保存" onclick="addBefore('0');save();" /> 
							  			<input class="input_button" type="button" value="取消" onclick="cancel()" /> 
							  			<input type="hidden" name="id" /> 
							  			<input type="hidden" name="applicationName" /> 
							  			<input type="hidden" name="status" />
							  			<input type="hidden" name="sequence" />
							  		</div>
							  	</div>
							</div>
							<div id="date_info">
								<c:forEach items="${applications}" var="app" varStatus="status_info">
								  	<div class="row" id="data_info_${app.id}">
								  		<div class="row-line">
								  			<input type="hidden" name="appSortSequence" value="${app.sequence}"/>
								  			<input type="hidden" name="appSortId" value="${app.id}"/>
								  			<div class="span-1" id="index_${app.id}">${status_info.index+1}</div>
								  			<div class="span-4" id="name_${app.id}">${app.applicationName}</div>
								  			<c:choose>
												<c:when test="${app.status eq 'Y'}">
													<div class="span-3" id="status_${app.id}">启用</div>
													<div class="span-7"><input class="input_button" type="button" value="修改" onclick="edit('${app.id}')" /> 
														<input class="input_button" type="button" value="保存" onclick="updateBefore('${app.id}');save();" style="display: none;" /> 
														<input class="input_button" type="button" value="取消" onclick="recover('${app.id}')" style="display: none;" /> 
														<input class="input_button" type="button" value="禁用" onclick="disable('${app.id}')" /> 
														<input class="input_button" type="button" value="删除" onclick="deleteItem('${app.id}')" />
													</div>
												</c:when>
												<c:otherwise>
													<div id="status_${app.id}" class="span-3">禁用</div>
													<div class="span-7"><input class="input_button" type="button" value="修改" onclick="edit('${app.id}')" /> 
														<input class="input_button" type="button" value="保存" onclick="updateBefore('${app.id}');save()" style="display: none;" /> 
														<input class="input_button" type="button" value="取消" onclick="recover('${app.id}')" style="display: none;" /> 
														<input class="input_button" type="button" value="启用" onclick="enable('${app.id}')" /> 
														<input class="input_button" type="button" value="删除" onclick="deleteItem('${app.id}')" />
													</div>
												</c:otherwise>
											</c:choose>
											<div class="span-2"><a title="拖动排序" alt="拖动排序" class="sort_icon" onmousedown="sortEnable()" onmouseup="sortDisable()"></a></div>
								  		</div>
								  	</div>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</ajax:updater>
		</w:form>
		</div>


	</w:page>
</f:view>