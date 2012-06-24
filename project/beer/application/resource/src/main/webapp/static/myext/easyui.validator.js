/**
 *  邮箱验证：<input type="text" validtype="email" required="true" missingMessage="不能为空" invalidMessage="邮箱格式不正确" /><br />
    网址验证：<input type="text" validtype="url" invalidMessage="url格式不正确[http://www.example.com]" /><br />
    长度验证：<input type="text" validtype="length[8,20]" invalidMessage="有效长度8-20" /><br />
    手机验证：<input type="text" validtype="mobile"  /><br />
    邮编验证：<input type="text" validtype="zipcode" /><br />
    账号验证：<input type="text" validtype="account[8,20]" /><br />
    汉子验证：<input type="text" validtype="CHS" /><br />
    远程验证：<input type="text" validtype="remote['checkname.aspx','name']" invalidMessage="用户名已存在"/>
    远程验证2：<input type="text" name="orgCode" id="orgCode" value=""   required="true" 
			        	  validType="check['/foundation-web/organization/check/orgCode','orgCode','该编码号已使用!',2,5]" 
			        	      missingMessage="编码不能为空"          class="easyui-validatebox text" 
			        		       />
 */
//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉子
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //移动手机号码验证
    mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确.'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    //用户账号验证(只能包括 _ 数字 字母) 
    account: {//param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    },
    check:  
    {  
        validator: function(value, param) {  
        	var len = $.trim(value).length;
        	  if (len< param[3] ||len > param[4]) {
                  this.message = '长度必须在' + param[3] + '至' + param[4] + '范围';
                  return false;
              }
        	  var postdata = {};  
              postdata['value'] = value;  
        	  var result = $.ajax({  
                  url: param[0],  
                  data: postdata,  
                  async: false,  
                  type: "get" ,
                  dataType : 'JSON',
              }).responseText;  
        	 var ret= $.parseJSON(result);
              if (ret.result.status) {  
            	  this.message = param[2];   
                  return false;  
              }  
              else {  
                  return true;  
              }  
        	  return true;
        },  
        message: ''  
    }  
})