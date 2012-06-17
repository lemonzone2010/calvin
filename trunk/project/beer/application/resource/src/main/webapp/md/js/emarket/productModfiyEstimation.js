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
								document.getElementById('addProductPublicationForm:creditRaitoId').disabled =true;
								document.getElementById('addProductPublicationForm:wholeSaleThreholdId').disabled=true;
								document.getElementById('addProductPublicationForm:wholesalePriceId').disabled=true;
							}
							if(enableOpen.checked==true){
								document.getElementById('addProductPublicationForm:creditRaitoId').disabled =false;
								document.getElementById('addProductPublicationForm:wholeSaleThreholdId').disabled=false;
								document.getElementById('addProductPublicationForm:wholesalePriceId').disabled=false;
							}
						 }