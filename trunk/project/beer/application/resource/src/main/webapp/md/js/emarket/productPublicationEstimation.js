                  $(document).ready(function() {
           					$("#pfId").hide();
           				    enableProperty();
						});
                   function loadingEnableProperty(obj){
                	  if(obj=="WHOLESALE"){
                		  document.getElementById('enableOpenId').checked=true;
                	  }else{
                		  document.getElementById('enableOpenId').checked=false;
                	  }
                	  enableProperty();
                   }
	     			  function enableProperty(){
							var enableOpen=document.getElementById('enableOpenId');
							if(enableOpen.checked==false){
								document.getElementById('creditRaitoId').disabled =true;
								document.getElementById('wholeSaleThreholdId').disabled=true;
								document.getElementById('wholesalePriceId').disabled=true;
								$("#pfId").hide();
							}
							if(enableOpen.checked==true){
								document.getElementById('creditRaitoId').disabled =false;
								document.getElementById('wholeSaleThreholdId').disabled=false;
								document.getElementById('wholesalePriceId').disabled=false;
								$("#pfId").show();
							}
							
						 }