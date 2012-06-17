/*
Content-Type: multipart/related; boundary="_CLOUDGAMER"

--_CLOUDGAMER
Content-Location:blankImage
Content-Transfer-Encoding:base64

R0lGODlhAQABAJEAAAAAAP///////wAAACH5BAEAAAIALAAAAAABAAEAAAICVAEAOw==
*/
var ImagePreview = function(file, img, options) {

	this.file = file;//文件对象
	this.img = img;//预览图片对象

	this._preload = null;//预载图片对象
	this._data = "";//图像数据

	var opt = this._setOptions(options);

	this.ratio = opt.ratio;
	this.maxWidth = opt.maxWidth;
	this.maxHeight = opt.maxHeight;

	this.onCheck = opt.onCheck;
	this.onShow = opt.onShow;
	this.onErr = opt.onErr;

	//设置数据获取程序
	this._getData = this._getDataFun(opt.mode);
	//设置预览显示程序
	this._show = opt.mode !== "filter" ? this._simpleShow : this._filterShow;
};
//根据浏览器获取模式
ImagePreview.MODE = $.browser.ie ? "filter" :
	$.browser.firefox ? "domfile" :
	$.browser.opera || $.browser.chrome || $.browser.safari ? "remote" : "simple";
//透明图片
ImagePreview.TRANSPARENT = $.browser.ie7 || $.browser.ie6 ?
	"mhtml:" + document.scripts[document.scripts.length - 1].getAttribute("src", 4) + "!blankImage":"data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
	//alert(document.scripts[document.scripts.length - 1].getAttribute("src", 4) + "!blankImage");
ImagePreview.prototype = {
  //设置默认属性
  _setOptions: function(options) {
    this.options = {//默认值
		mode:		ImagePreview.MODE,//预览模式
		ratio:		0,//自定义比例
		maxWidth:	0,//缩略图宽度
		maxHeight:	0,//缩略图高度
		onCheck:	function(){},//预览检测时执行
		onShow:		function(){},//预览图片时执行
		onErr:		function(){}//预览错误时执行
    };
    return $.extend(this.options, options || {});
  },
  //开始预览
  preview: function() {

	if ( this.file && false !== this.onCheck() ) {
		this._preview( this._getData() );
	}
  },

  //根据mode返回数据获取程序
  _getDataFun: function(mode) {
	switch (mode) {
		case "filter" :
			return this._filterData;
		case "domfile" :
			return this._domfileData;
		case "remote" :
			return this._remoteData;
		case "simple" :
		default :
			return this._simpleData;
	}
  },
  //滤镜数据获取程序
  _filterData: function() {
	this.file.select();
	try{
		return document.selection.createRange().text;
	} finally { document.selection.empty(); }
  },
  //domfile数据获取程序
  _domfileData: function() {
	return this.file.files[0].getAsDataURL();
  },
  //一般数据获取程序
  _simpleData: function() {
	return this.file.value;
  },

  //预览程序
  _preview: function(data) {
	//空值或相同的值不执行显示
	if ( !!data && data !== this._data ) {
		this._data = data; this._show();
	}
  },

  //设置一般预载图片对象
  _simplePreload: function() {
	if ( !this._preload ) {
		var preload = this._preload = new Image(), oThis = this,
			onload = function(){ oThis._imgShow( oThis._data, this.width, this.height ); };
		this._onload = function(){ this.onload = null; onload.call(this); }
		preload.onload = $.browser.ie ? this._onload : onload;
		preload.onerror = function(){/*oThis._error(); */};
	} else if ( $.browser.ie ) {
		this._preload.onload = this._onload;
	}
  },
  //一般显示
  _simpleShow: function() {
	this._simplePreload();
	this._preload.src = this._data;
  },

  //设置滤镜预载图片对象
  _filterPreload: function() {
	if ( !this._preload ) {
		var preload = this._preload = document.createElement("div");
		//隐藏并设置滤镜
		$(preload).css({
			width: "1px", height: "1px",
			visibility: "hidden", position: "absolute", left: "-9999px", top: "-9999px",
			filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image')"
		});
		//插入body
		var body = document.body; body.insertBefore( preload, body.childNodes[0] );
	}
  },
  //滤镜显示
  _filterShow: function() {
	this._filterPreload();
	var preload = this._preload,
		data = this._data.replace(/[)'"%]/g, function(s){ return escape(escape(s)); });
	try{
		preload.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = data;
	}catch(e){ this._error("filter error"); return; }
	//设置滤镜并显示
	this.img.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + data + "\")";
	this._imgShow( ImagePreview.TRANSPARENT, preload.offsetWidth, preload.offsetHeight );
  },

  //显示预览
  _imgShow: function(src, width, height) {
	var img = this.img, style = img.style,
		ratio = Math.max( 0, this.ratio ) || Math.min( 1,
			Math.max( 0, this.maxWidth ) / width  || 1,
			Math.max( 0, this.maxHeight ) / height || 1
		);
	//设置预览尺寸
	style.width = Math.round( width * ratio ) + "px";
	style.height = Math.round( height * ratio ) + "px";
	//设置src
	img.src = src;
	this.onShow();
  },

  //销毁程序
  dispose: function() {
	//销毁上传文件对象
	if ( this._upload ) {
		this._upload.dispose(); this._upload = null;
	}
	//销毁预载图片对象
	if ( this._preload ) {
		var preload = this._preload, parent = preload.parentNode;
		this._preload = preload.onload = preload.onerror = null;
		parent && parent.removeChild(preload);
	}
	//销毁相关对象
	this.file = this.img = null;
  },
  //出错
  _error: function(err) {
	this.onErr(err);
  },
  resetFile:function(){
	  var file = this.file;
	  file.value = "";//ff chrome safari
		if ( file.value ) {
			if ( $.browser.ie ) {//ie
				with(file.parentNode.insertBefore(document.createElement('form'), file)){
					appendChild(file); reset(); removeNode(false);
				}
			} else {//opera
				file.type = "text"; file.type = "file";
			}
		}
	 $(this.img).css({'filter':'','width':'0px','height':'0px'});
	 $(this.img).attr("src","/ebiz-resource/md/image/common/img_white.gif");
  }
}

//检测程序
var exts = "jpg|gif|bmp|png";
function CheckPreview(){
	var value = this.file.value, check = true;
	//var fileSize = $(this).attr("fileSize");
	if ( !value ) {
		check = false;
		alert("请先选择文件！");
	} else if ( !RegExp( "\.(?:" + exts + ")$$", "i" ).test(value) ) {
		check = false;
		alert("只能上传以下类型：" + exts);
	}
	check || this.resetFile();
	return check;
}

//显示预览
function ShowPreview(){
	var file = this.file, value = file.value, oThis = this;
}
/*ajax fileupload*/
jQuery.extend({
    createUploadIframe: function(id, uri)
	{
			//create frame
            var frameId = 'jUploadFrame' + id;

            if(window.ActiveXObject) {
                var io = document.createElement('<iframe id="' + frameId + '" name="' + frameId + '" />');
                if(typeof uri== 'boolean'){
                    io.src = 'javascript:false';
                }
                else if(typeof uri== 'string'){
                    io.src = uri;
                }
            }
            else {
                var io = document.createElement('iframe');
                io.id = frameId;
                io.name = frameId;
            }
            io.style.position = 'absolute';
            io.style.top = '-1000px';
            io.style.left = '-1000px';

            document.body.appendChild(io);

            return io
    },
    createUploadForm: function(id, fileElementId)
	{
		//create form
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = $('<form  action="" method="POST" name="' + formId + '" id="' + formId + '" enctype="multipart/form-data"></form>');

		//add by Kingua
		if(fileElementId){
			var oldElement,newElement,fileSize,waterMark,isCompress,isSquare;
			for(var i = 0;i < fileElementId.length; i ++){
				oldElement = $('#' + fileElementId[i]);
				newElement = $(oldElement).clone();
				fileSize = $(oldElement).attr("fileSize");
				waterMark = $(oldElement).attr("waterMark");
				isCompress = $(oldElement).attr("isCompress");
				isSquare = $(oldElement).attr("isSquare");
				$(oldElement).attr('id', fileId);
				$(oldElement).before(newElement);
				$(oldElement).appendTo(form);
				if(typeof(fileSize)!="undefined" && !isNaN(parseInt(fileSize))){
					$('<input type="hidden" name="'+fileElementId[i]+'_fileSize" value="'+fileSize+'"/>').appendTo(form);
				}
				if(typeof(waterMark)!="undefined" && !isNaN(parseInt(waterMark))){
					$('<input type="hidden" name="'+fileElementId[i]+'_waterMark" value="'+waterMark+'"/>').appendTo(form);
				}
				if(typeof(isCompress)!="undefined" ){
					$('<input type="hidden" name="'+fileElementId[i]+'_isCompress" value="'+isCompress+'"/>').appendTo(form);
				}
				if(typeof(waterMark)!="undefined" ){
					$('<input type="hidden" name="'+fileElementId[i]+'_isSquare" value="'+isSquare+'"/>').appendTo(form);
				}
			}
		}
		/*
		var oldElement = $('#' + fileElementId);
		var newElement = $(oldElement).clone();
		$(oldElement).attr('id', fileId);
		$(oldElement).before(newElement);
		$(oldElement).appendTo(form);
		*/
		//set attributes
		$(form).css('position', 'absolute');
		$(form).css('top', '-1200px');
		$(form).css('left', '-1200px');
		$(form).appendTo('body');
		return form;
    },

    ajaxFileUpload: function(s) {
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime()
		var form = jQuery.createUploadForm(id, s.fileElementId);
		var io = jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;
        // Watch for a new set of requests
        if ( s.global && ! jQuery.active++ )
		{
			jQuery.event.trigger( "ajaxStart" );
		}
        var requestDone = false;
        // Create the request object
        var xml = {}
        if ( s.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
        // Wait for a response to come back
        var uploadCallback = function(isTimeout)
		{
			var io = document.getElementById(frameId);
            try
			{
				if(io.contentWindow)
				{
					 xml.responseText = io.contentWindow.document.body?io.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = io.contentWindow.document.XMLDocument?io.contentWindow.document.XMLDocument:io.contentWindow.document;

				}else if(io.contentDocument)
				{
					 xml.responseText = io.contentDocument.document.body?io.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = io.contentDocument.document.XMLDocument?io.contentDocument.document.XMLDocument:io.contentDocument.document;
				}
            }catch(e)
			{
				jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout")
			{
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );

                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        jQuery.handleError(s, xml, status);
                } catch(e)
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(io).unbind()

                setTimeout(function()
									{	try
										{
											$(io).remove();
											$(form).remove();

										} catch(e)
										{
											jQuery.handleError(s, xml, null, e);
										}

									}, 100)

                xml = null

            }
        }
        // Timeout checker
        if ( s.timeout > 0 )
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try
		{
           // var io = $('#' + frameId);
			var form = $('#' + formId);
			$(form).attr('action', s.url);
			$(form).attr('method', 'POST');
			$(form).attr('target', frameId);
            if(form.encoding)
			{
                form.encoding = 'multipart/form-data';
            }
            else
			{
                form.enctype = 'multipart/form-data';
            }
            $(form).submit();

        } catch(e)
		{
            jQuery.handleError(s, xml, null, e);
        }
        if(window.attachEvent){
            document.getElementById(frameId).attachEvent('onload', uploadCallback);
        }
        else{
            document.getElementById(frameId).addEventListener('load', uploadCallback, false);
        }
        return {abort: function () {}};

    },

    uploadHttpData: function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" ){
        	//console.log("**"+data+"**");
            eval( "data = " + data );
        }
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();
			//alert($('param', data).each(function(){alert($(this).attr('value'));}));
        return data;
    },
    handleError: function( s, xhr, status, e ) {
		// If a local callback was specified, fire it
		if ( s.error ) s.error( xhr, status, e );

		// Fire the global callback
		if ( s.global )
			jQuery.event.trigger( "ajaxError", [xhr, s, e] );
	}
})

function ajaxFileUpload(formId,callbackId,params,callback)
	{
		var fileIds=[];
		$('#'+formId).find("input:file").each(function(i,n){
			fileIds[i] = $(n).attr('id');
			//console.log(fileIds[i]);
		});
		$.ajaxFileUpload
		({
			url:'/'+location.pathname.split('/')[1]+'/FileUploadServlet',
			secureuri:false,
			fileElementId:fileIds,
			dataType: 'json',
			success: function (data, status)
			{
				if(typeof(data) != 'undefined'){
					if(!data.success){
						//alertError(data.msg);
						alert(data.msg);
					}
				}
				if(typeof(callbackId)!='undefined'){
					//add file path
					$.each(fileIds,function(i,v){
						if($("#"+v+"_path").size()>0){
							callbackId.addParam($("#"+v+"_path").attr('id'),$("#"+v+"_path").attr('value'));
						}
					});
					if(typeof(params)!='undefined'){
						$.each(params,function(i,v){
							callbackId.addParam(i,v);
						});
					}
					if(typeof(data) != 'undefined'){
						callbackId.addParam('fileUploadStatus',data.success?1:0);
					}
					if(typeof(callback)=='function')callback();
					callbackId.submit();
				}
			},
			error: function (data, status, e)
			{
				//alertError(e);
				alert(e);
			}
		});
	}