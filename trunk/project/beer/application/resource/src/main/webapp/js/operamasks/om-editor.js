/*
 * operamasks-ui omEditor 0.0.7
 *
 * Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://ui.operamasks.org/license
 *
 * http://ui.operamasks.org/docs/
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 */
;(function($) {
    
    /** 
     * @name omEditor
     * @author 李聪平
     * @class HTML富文本编辑器.<br/>
     * @constructor
     * @description 构造函数. 
     * @param p 标准config对象：{}
     * @example
     * $('#editor').omEditor({toolbar:'Basic'});
     */
    $.widget("om.omEditor", {
        
        options: /** @lends omEditor.prototype */{
        /**
         * 工具箱（别名工具栏）的定义。它是一个工具栏的名称或数组。
         * editor默认定义了两个工具箱，名字分别为'Full'和'Basic'。
         * @type Array,String
         * @default 'Full'
         * @example
         * // 定义一个工具条只包含”源代码“、一个分隔符，”加粗“和”斜体“按钮 
         * var config = {};
         * config.toolbar =
         * [
         *     [ 'Source', '-', 'Bold', 'Italic' ]
         * ];
         * $( ".selector" ).omEditor(config);
         * @example
         * // 加载名字为'Basic'的工具集.
         * $( ".selector" ).omEditor({ toolbar: 'Basic' });
         */
            toolbar : 'Full' ,

            /**
             * 编辑器的高度
             * Note: 不支持百分比
             * @type Number
             * @default 200
             * @example
             * $( ".selector" ).omEditor({ height: 500 });
             */
            height : 200,

            /**
             * 编辑器的皮肤。可以是安装路径下的皮肤文件夹的名字，或者是用逗号分隔路径和文件夹的名字
             * @type String
             * @default 'eac'
             * @example
             * $( ".selector" ).omEditor({ skin: 'kama' });
             * $( ".selector" ).omEditor({ skin: 'myskin,/customstuff/myskin/' });
             */
            skin : 'eac',

            /**
             * 编辑器的宽度，可设置具体的像素值或百分比
             * @type String,Number
             * @default '' (empty)
             * @example
             * $( ".selector" ).omEditor({ width: 850 });
             * @example
             * $( ".selector" ).omEditor({ width: '75%' });
             */
            width : '',
            
            /**
             * 编辑器是否只读 
             * @type Boolean
             * @default false
             * @example
             * $( ".selector" ).omEditor({ readOnly: true });
             */
            readOnly : false,
            
            /**
             * 是否在“源码”模式中对“所见即所得”模式里的HTML实体进行转义, 包括:nbsp,gt,lt,amp;
             * @type Boolean
             * @default true
             * @example
             * $( ".selector" ).omEditor({ basicEntities: false });
             */
            basicEntities : true,
            
            // 设置为false且暂时不开放这个属性，当设置为true且basicEntities设置为false时会出现转义异常
            // 见：http://jira.apusic.net/browse/AOM-33
            entities : false,
            
            /**
             * 定义一组快捷键列表用来阻止浏览器对这些按键的默认行为
             * @type Array
             * @default (查看该属性示例)
             * @name omEditor#blockedBrowserKeystrokes
             * @example
             * // 这是默认值
             * var config = {};
             * config.blockedBrowserKeystokes =
             * [
             *     OMEDITOR.CTRL + 66 &#47;*B*&#47;,
             *     OMEDITOR.CTRL + 73 &#47;*I*&#47;,
             *     OMEDITOR.CTRL + 85 &#47;*U*&#47;
             * ];
             * $( ".selector" ).omEditor(config);
             */
            
            /**
             * 设置加载为HTML编辑器的内容时，要使用的DOCTYPE。
             * @type String
             * @default '&lt;!DOCTYPE HTML&gt;'
             * @example
             * // Set the doctype to the HTML 4 (quirks) mode.
             * $( ".selector" ).omEditor({ docType: '&lt;!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"&gt;' });
             */
            docType : '<!DOCTYPE HTML>',
            
            /**
             * 电子邮件地址防垃圾邮件保护选项。
             * 保护将被应用在通过编辑器界面创建或修改电子邮件中的链接的时候。
             * 有两种保护方法可供选择：
             * <ol> <li>电子邮件部分（名称，域和任何其他的查询字串）组装成一个函数调用模式。
             *          这样的功能必须提供开发人员将使用在网页内容</li>
             *      <li>把e-mail地址混淆成一个对人或防垃圾邮件程序没有任何意义的特殊字符串，
             *          但它是浏览器所能正确渲染和接受的。</li></ol>
             * 这两种方法都需要启用JavaScript。
             * @type String
             * @default '' (empty string = disabled)
             * @example
             * // href="mailto:tester@ckeditor.com?subject=subject&body=body"
             * $( ".selector" ).omEditor({ emailProtection: '' });
             * @example
             * // href="<a href=\"javascript:void(location.href=\'mailto:\'+String.fromCharCode(116,101,115,116,101,114,64,99,107,101,100,105,116,111,114,46,99,111,109)+\'?subject=subject&body=body\')\">e-mail</a>"
             * $( ".selector" ).omEditor({ emailProtection: 'encode' });
             * @example
             * // href="javascript:mt('tester','ckeditor.com','subject','body')"
             * $( ".selector" ).omEditor({ emailProtection: 'mt(NAME,DOMAIN,SUBJECT,BODY)' });
             */
            
            emailProtection : '',
            
            /**
             * 允许上下文敏感的tab键行为，包括下列情况：
             * <h5>当在表格里面按下tab键时:</h5>
             * <ul>
             *      <li>如果 TAB 被按下, 选择表格的下一个单元格，如果当前是表格的最后一个单元格则新建一行并focus到该行的第一个单元格</li>
             *      <li>如果 SHIFT+TAB 被按下, 选择表格的上一个单元格，如果当前是表格的第一个单元格则什么事都不做.</li>
             * </ul>
             * @type Boolean
             * @default true
             * @example
             * $( ".selector" ).omEditor({ enableTabKeyTools: false });
             */
            
            enableTabKeyTools : true,
            
            /**
             * 设置回车键的行为. 这也决定了在编辑器中的其他行为规则, 例如元素 &lt;br&gt;是否作为段落分隔时使用。
             * 允许的值包括以下常量，以及它们的相对行为:
             * <ul>
             *     <li>{@link OMEDITOR.ENTER_P} (1): 创建新的 &lt;p&gt; 段落;</li>
             *     <li>{@link OMEDITOR.ENTER_BR} (2): 行被 &lt;br&gt; 元素分隔;</li>
             *     <li>{@link OMEDITOR.ENTER_DIV} (3): 创建新的 &lt;div&gt; 块</li>
             * </ul>
             * <strong>注意</strong>: 建议使用
             * {@link OMEDITOR.ENTER_P} 值，因为它的语义值和正确性。并且编辑器会优化这个值.
             * @type Number
             * @default OMEDITOR.ENTER_P
             * @example
             * // 不建议使用的.
             * $( ".selector" ).omEditor({ enterMode: OMEDITOR.ENTER_BR });
             */
            enterMode : OMEDITOR.ENTER_P,
            
            /**
             * 快捷键关联命令的列表。
             * 列表中的每个元素是一个数组，其中第一项是按键，第二个是要执行的命令的名称。
             * 注意：如果设置了这个属性值则默认值会被覆盖
             * @type Array
             * @default (see example)
             * @name omEditor#keystrokes
             * @example
             * // 下面是默认值.
             * var config={};
             * config.keystrokes =
             * [
             *     [ OMEDITOR.ALT + 121 &#47;*F10*&#47;, 'toolbarFocus' ],
             *     [ OMEDITOR.ALT + 122 &#47;*F11*&#47;, 'elementsPathFocus' ],
             *
             *     [ OMEDITOR.SHIFT + 121 &#47;*F10*&#47;, 'contextMenu' ],
             *
             *     [ OMEDITOR.CTRL + 90 &#47;*Z*&#47;, 'undo' ],
             *     [ OMEDITOR.CTRL + 89 &#47;*Y*&#47;, 'redo' ],
             *     [ OMEDITOR.CTRL + OMEDITOR.SHIFT + 90 &#47;*Z*&#47;, 'redo' ],
             *
             *     [ OMEDITOR.CTRL + 76 &#47;*L*&#47;, 'link' ],
             *
             *     [ OMEDITOR.CTRL + 66 &#47;*B*&#47;, 'bold' ],
             *     [ OMEDITOR.CTRL + 73 &#47;*I*&#47;, 'italic' ],
             *     [ OMEDITOR.CTRL + 85 &#47;*U*&#47;, 'underline' ],
             *
             *     [ OMEDITOR.ALT + 109 &#47;*-*&#47;, 'toolbarCollapse' ]
             * ];
             * $( ".selector" ).omEditor(config);
             */
            
            /**
             * 是否允许改变编辑器大小
             * @type Boolean
             * @default true
             * @example
             * $( ".selector" ).omEditor({ resizable:true });
             */
            resizable : true,
            
            /**
             * 设置页面加载的时候编辑器是否默认focus
             * @type Boolean
             * @default false
             * @example
             * $( ".selector" ).omEditor({ startupFocus:true });
             */
            
            startupFocus : false,
            
            /**
             * 设置编辑器启动时加载的模式。
             * 这取决于加载的插件。默认情况下，“所见即所得”(wysiwyg)和“源”(source)模式可供选择。
             * @type String
             * @default 'wysiwyg'
             * @example
             * $( ".selector" ).omEditor({ startupMode:'source' });
             */
            startupMode : 'wysiwyg',
            
            /**
             * 要保持的撤销步骤的数量。此值设置越高则占用内存越大
             * @type Number
             * @default 20
             * @example
             * $( ".selector" ).omEditor({ undoStackSize:50 });
             */
            undoStackSize : 20
            
            /**
             * 允许上传的图片类型
             * @type Array
             * @default ['jpg','gif','png']
             * @name omEditor#allowImageType
             * @example
             * $( ".selector" ).omEditor({ allowImageType:['jpg','gif','png','bmp'] });
             */
            
            /**
             * 编辑器中释放键盘时的事件
             * @event
             * @type Function
             * @default null
             * @param event
             * @name omEditor#onKeyUp
             * @example
             * $(".selector").omEditor({
			 *	onKeyUp: function(e) {
			 *		alert($('.selector').omEditor('getData').length);
			 *	}
			 * });
             */

        },
        _create: function() {
        	if(!this.options.allowImageType){
        		this.options.allowImageType = ['jpg','gif','png'];
        	}
        	if(!this.options.keystrokes){
        		this.options.keystrokes =   
        			[
                         [ OMEDITOR.ALT + 121 /*F10*/, 'toolbarFocus' ],
                         [ OMEDITOR.ALT + 122 /*F11*/, 'elementsPathFocus' ],

                         [ OMEDITOR.SHIFT + 121 /*F10*/, 'contextMenu' ],
                         [ OMEDITOR.CTRL + OMEDITOR.SHIFT + 121 /*F10*/, 'contextMenu' ],

                         [ OMEDITOR.CTRL + 90 /*Z*/, 'undo' ],
                         [ OMEDITOR.CTRL + 89 /*Y*/, 'redo' ],
                         [ OMEDITOR.CTRL + OMEDITOR.SHIFT + 90 /*Z*/, 'redo' ],

                         [ OMEDITOR.CTRL + 76 /*L*/, 'link' ],

                         [ OMEDITOR.CTRL + 66 /*B*/, 'bold' ],
                         [ OMEDITOR.CTRL + 73 /*I*/, 'italic' ],
                         [ OMEDITOR.CTRL + 84 /*U*/, 'underline' ],

                         [ OMEDITOR.ALT + ( OMEDITOR.env.ie || OMEDITOR.env.webkit ? 189 : 109 ) /*-*/, 'toolbarCollapse' ],
                         [ OMEDITOR.ALT + 48 /*0*/, 'a11yHelp' ]
                     ];
        	}
        	if(!this.options.blockedBrowserKeystrokes){
        		this.options.blockedBrowserKeystrokes = 
        		[
                    OMEDITOR.CTRL + 66 /*B*/,
                    OMEDITOR.CTRL + 73 /*I*/,
                    OMEDITOR.CTRL + 85 /*U*/
                ];
        	}
        	this.options.blockedKeystrokes = this.options.blockedBrowserKeystrokes;
            var options=this.options;
            this.element.filter( 'textarea, div, p' ).each( function(){
                var $element = $( this ),
                editor = $element.data( 'omeditorInstance' ),
                element = this;
                if (!editor){
                    editor = OMEDITOR.replace( element, options );
                    $element.data( 'omeditorInstance', editor );
                }
            });  
        },
        _init: function() {
            
        },
        _get:function($el) {
            return $el.data('omeditorInstance');
        },
        /**
         * 得到editor的HTML内容。
         * @name omEditor#getData
         * @function
         * @returns omEditor的HTML内容
         */  
        getData:function(){
            var editor = this._get(this.element.eq(0));
            return editor.getData();
        },
        /**
         * 得到editor的内容。包含纯文本。不包含IMG、EMBED元素以及文本头尾的换行符和空格符。
         * @name omEditor#getPlainText
         * @function
         * @returns omEditor的文本内容
         */         
        getPlainText:function(){
            var data = this.getData();
            data = data.replace(/<(?!img|embed).*?>/ig, '');
            data = data.replace(/&nbsp;/ig, ' ');
            
            data = data.replace(/<(?:img|embed).*?>/ig, '');
            data = data.replace(/\r\n|\n|\r/g, '');
            data = OMEDITOR.tools.trim(data);
            return data;  
        },
        
        /**
         * 设置editor的内容。
         * @name omEditor#setData
         * @function
         * @param params 要设置的内容
         */
        setData:function(params){
            var _self = this;
            this.element.each(function(){
                var editor = _self._get($(this));
                editor.setData(params);
            });
        },
        /**
         * 设置editor为只读模式。
         * @name omEditor#setReadOnly
         * @function
         * @param params true or false
         */
        setReadOnly:function(params){
            var _self = this;
            this.element.each(function(){
                var editor = _self._get($(this));
                editor.setReadOnly(params);
            });
        },
        /**
         * 插入HTML内容。
         * @name omEditor#insertHtml
         * @function
         * @param params 要插入的HTML内容
         */        
        insertHtml:function(params){
            var _self = this;
            this.element.each(function(){
                var editor = _self._get($(this));
                editor.insertHtml(params);
            }); 
        },
        /**
         * 插入文本内容。
         * @name omEditor#insertText
         * @function
         * @param params 要插入的内容
         */        
        insertText:function(params){
            var _self = this;
            this.element.each(function(){
                var editor = _self._get($(this));
                editor.insertText(params);
            });
        },
        /**
         * 更新&lt;textarea&gt;元素，取而代之的是在编辑器中当前的数据。
         * @name omEditor#updateElement
         * @function
         */
        updateElement:function(){
        	var _self = this;
        	this.element.each(function(){
        		var editor = _self._get($(this));
        		editor.updateElement();
        	});
        }
    });
})(jQuery);