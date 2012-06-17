	
   $(document).ready(function() {
	   $("#template").change(function(){
		   var tem = $("select[name='template']").val();
		   $('.editor').empty().omEditor('setData',tem);
	   });
	}); 
  
   $(document).ready(function() {
			$('.editor').omEditor({
				 filebrowserImageUploadUrl : '/emarket/omEditorFileUploadServlet?type=Images',
				 allowImageType : ['jpg','gif','png','bmp']
			});
			$("#saveTemplateId").hide();
		});
		function setBrandInfo(setBrandInfo) {
		  	var array = eval(setBrandInfo);
		  	if(null!=array&&array.length!=0){
		  		$("#brandDiv").show();
		  		$("#brandDiv").empty();
				for(var i = 0; i < array.length; i++){
				     var setValue =array[i].setValue;
				      var setValues = setValue.split("|");
				      var optionDisplay = "";
				     for(var j=0;j<setValues.length;j++){
				     	optionDisplay +='<option value='+array[i].setName+':'+setValues[j]+'>'+setValues[j]+'</option>';
				    }
				     var displayView='<div class="item_goods"><span class=label_goods>'+array[i].setName+'：</span>';
				     displayView +='<div class=fl><select id=brandIdSelect name=brandIdSelect>'+optionDisplay+'</select></div></div>';
					 document.getElementById("brandDiv").innerHTML += displayView;
				}
			}else{
                  		$("#brandDiv").innerHTML = "";
                  		$("#brandDiv").hide();
			}
         }
		
          function getCategoryAttribute(){
        	 document.getElementById("tiShiCategoryId").innerHTML="";
          	getBrandIdListView.addParam("categoryId", $("#categoryId").val());
          	getBrandIdListView.submit();
          }
          
          function  addRow(){
	              var vTb=$("#TbData");
	              var vNum=$("#TbData").find("tr").length;
	              if(vNum>5){
	                  alert("不能大于5条数据");
	                return;
	              }
	              var vTr=$("#TbData #trDataRow1"); 
	              var vTrClone=vTr.clone(true);
	              vTrClone[0].id="trDataRow"+vNum;
	              vTrClone.appendTo(vTb); 
	          	$("#trDataRow"+vNum+" input").each(function(){
					$(this).attr("value","");
				});
		 } 
          
		 function deleteRow(obj){
		       var vNum=$("#TbData").find("tr").length;
	              if(vNum<=2)
	              {
	                   alert('请至少留一行');
	                   return;
	              }       
	              var vbtnDel=$(obj);          
	              var vTr=vbtnDel.parent().parent("td").parent("tr");             
	              if(vTr.attr("id")=="trDataRow1")
	              {
	                 alert('第一行不能删除!'); 
	                 return;
	              }else{
	                 vTr.remove();
	              }
		 }

	   function creditRaito(obj){
		 document.getElementById("tiShiBaiFenBi").innerHTML="<font color='red'>预付款比例必须在0和100之间</font>";
		 if(obj.value>=0&&obj.value<=100){
		 document.getElementById("tiShiBaiFenBi").innerHTML="";
		 }
	   }
	   
	 var flg=true;
	   function submitFunction(obj){
			 $('.editor').val($('.editor').omEditor('getData'));
			 if(!ebiz_isAllPass()){
				 return false;
			 };
			var brandId=document.getElementById("brandId").value;
			var categoryId=document.getElementById("categoryId").value;
			if(0==categoryId){
				document.getElementById("tiShiCategoryId").innerHTML="<font color='red'>选择不能为空,请您选择的商品类别</font>";
				return false;
			}
			if(0==brandId){
				document.getElementById("tiShiBrandId").innerHTML="<font color='red'>品牌不能为空,请您选择的商品类别第三项</font>";
				return false;
			}
			
			var pics =""+$('[name=picId]').attr("src");
			if(pics=="undefined"){
				alert("商品图片最少上传一张");
				return false;
			}
			var enableOpen=document.getElementById('enableOpenId');
			var retailPriceId=document.getElementById("retailPriceId").value;
			var wholesalePriceId=document.getElementById("wholesalePriceId").value;
			if(enableOpen.checked==false){
				if(retailPriceId==0){
					 if(confirm("该商品零售价为零(0),你是否提交！")){
						panDuanTijiao(obj);
  					 }
				}else{
					panDuanTijiao(obj);
				}
			}
			if(enableOpen.checked==true){
				if(wholesalePriceId==0||retailPriceId==0){
					 if(confirm("该商品零售价与批发价为零(0),你是否提交！")){
						panDuanTijiao(obj);
  					 }
				}else{
					panDuanTijiao(obj);
				}
			}
	   }
	   
	   function panDuanTijiao(obj){
		 if(obj==1){
			 addProductPublication.submit();
		 }else if(obj==2){
		    addProductPublicationTemporaryStorage.submit();
		 }else if(obj==3){
		    preview.submit();
		 }else if(obj==4){
			var templateName = document.getElementById('templateName').value;  
			if(templateName.length>0||templateName.length!= ""){
		      saveProductTemplate.submit();
			}else{
				$("#saveTemplateId").show();
				return;
			}
		 }else if(obj==5){
			 wholesaleView.submit();
		 }
	   }
	   
	   

      				   
