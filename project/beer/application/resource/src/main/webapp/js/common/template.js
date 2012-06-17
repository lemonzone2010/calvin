/*消息提示脚本
-----------------------------------------------------------------------------------------------------*/
(function(jQuery){
	$.fn.doc = function(){
//		return window.top.document;
		return window.document;
	}

	$.fn.win = function(){
		//return window.top.window;
		return window;
	}
	var MsgBox = function(index,w,h){
		this.layer = {'width' : w, 'height': h};
		this.title = "信息提示";
		this.time = 4000;
		this.timer1 = null;
		this.errBox = false;
		this.boxId = null;
		this.index = index;
		this.box = null;
		this.speed = 600;

		this.inits = function(text,time ,errBox){
			if(typeof(errBox) != 'undefined')this.errBox = errBox;
			if(this.errBox)this.title = "错误提示";
			if(time>=0)this.time = time;
			var html = '<div class="ebiz_message" style="z-index:99999;width:'
				+ this.layer.width + 'px;height:' + this.layer.height
				+ 'px;position:fixed;bottom:0px;right:0px;">'+''
				+'<div class="ebiz_message_tit">'
				+'<h1 style="float:left;">'+this.title
				+'</h1>'
				+'<span class="ebiz_message_close" style="float:right;cursor:pointer;"><img src="/ebiz-resource/image/common/message/close_btn.gif" /></span>'+''
				+'<div class="nsec-clear"/>'
				+'</div>'
				+'<div class="ebiz_message_content"><p class="'+(errBox?"nsec_tip_error":"")+'">'
				+ text + '</p></div></div>';
			this.boxId = 'ebiz_message'+ index;

			this.box = $(html).prependTo($.fn.doc().body).hide().css({
				'width':this.layer.width+'px',
				'heigth':this.layer.height + 'px'
			}).attr('id',this.boxId);
		};
	};
	var MsgBoxes = function(){
		this.boxes = new Array();
		this.index = 0;

		this.add = function(obj){
			this.boxes.push(obj);
			this.index += 1;
		};

		this.remove = function(id){
			var temp = new Array();
			var rmBox = null;
			for(var i=0,j=0;i < this.boxes.length;i++){
				if(this.boxes[i].boxId != id){
					temp[j]=this.boxes[i];
					j++;
				}else{
					rmBox = this.boxes[i];
				}
			}
			this.boxes = temp;
			if(this.boxes.length == 0)this.index = 0;
			return rmBox;
		}

		this.getBox = function(id){
			for(var i=0;i < this.boxes.length;i++){
				if(this.boxes[i].boxId == id){
					return this.boxes[i];
				}
			}
		}

		this.show = function(msg,time,err){
			var box = new MsgBox(this.index,209,117);
			box.inits(msg,time,err);

			box.box.find(".ebiz_message_close").click(function(){
				setTimeout("$.msgBoxes.close('"+box.boxId+"')", 1);
			});
			this.add(box);
			this.setPosition();
			box.box.slideDown(box.speed);

			if(box.time > 0){
				setTimeout("$.msgBoxes.close('"+box.boxId+"')", box.time);
			}
		};
		this.close = function(boxId){
			var rmBox = this.remove(boxId);
			if(rmBox != null){
				rmBox.box.slideUp(rmBox.speed);
				setTimeout("$('#"+boxId+"').remove();", rmBox.speed);
			}
			this.setPosition();
		};

		this.getPosTop = function(index){
			var curBox = this.boxes[index];
			var top = 0;
			var boxH = curBox.layer.height + 2;
			if($.browser.ie && $.browser.v < 7){
				var scrollHeight = Math.max($($.fn.doc().documentElement).attr('scrollHeight'),$($.fn.doc().body).attr('scrollHeight'));
				var offsetHeight = Math.max($($.fn.doc().documentElement).attr('offsetHeight'),$($.fn.doc().body).attr('offsetHeight'));
				if(scrollHeight < offsetHeight){
					top = $($.fn.win()).height()-boxH*index;
				}else{
					top = scrollHeight - boxH*index;
				}
			}else{
				top = $($.fn.doc().documentElement).attr('clientHeight') - boxH*(index+1) + $($.fn.doc().documentElement).attr('scrollTop');
			}
			return top;
		};

		this.getPosLeft = function(index){
			var curBox = this.boxes[index];
			var left = 0;
			var boxW = curBox.layer.width + 2;
			if($.browser.ie && $.browser.v < 7){
				var scrollWidth = Math.max($($.fn.doc().documentElement).attr('scrollWidth'),$($.fn.doc().body).attr('scrollWidth'));
				var offsetWidth = Math.max($($.fn.doc().documentElement).attr('offsetWidth'),$($.fn.doc().body).attr('offsetWidth'));
				if(scrollWidth < offsetWidth){
					left = $($.fn.win()).width()-boxW;
				}else{
					left = scrollWidth - boxW;
				}
			}else{
				left = $($.fn.doc().documentElement).attr('clientWidth') - boxW + $($.fn.doc().documentElement).attr('scrollLeft');
			}
			return left;
		};

		this.setPosition = function(){
			if(this.boxes.length > 0){
				$(this.boxes).each(function(i,v){
					v.box.css({
						'top':$.msgBoxes.getPosTop(i)+'px',
						'left':$.msgBoxes.getPosLeft(i)+'px'
					});
				});
			}
		};
	};
	$.extend({msgBoxes: $.fn.win().$.msgBoxes?$.fn.win().$.msgBoxes:new MsgBoxes()});

	$($.fn.win()).scroll(function(){
		//$.msgBoxes.setPosition();
	}).resize(function(){
		//$.msgBoxes.setPosition();
	});

})(jQuery);

function alertMessage(msg){
	$.msgBoxes.show(msg,4000,false);
}

function alertError(msg){
	$.msgBoxes.show(msg,999999999,true);
}

function showMessage(msg,time){
	$.msgBoxes.show(msg,time,false);
}