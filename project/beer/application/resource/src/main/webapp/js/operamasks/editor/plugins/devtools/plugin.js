﻿/*
Copyright 2011, AUTHORS.txt (http://ui.operamasks.org/about)
Dual licensed under the MIT or GPL Version 2 licenses.
*/

OMEDITOR.plugins.add('devtools',{lang:['en'],init:function(a){a._.showDialogDefinitionTooltips=1;},onLoad:function(){OMEDITOR.document.appendStyleText(OMEDITOR.config.devtools_styles||'#cke_tooltip { padding: 5px; border: 2px solid #333; background: #ffffff }#cke_tooltip h2 { font-size: 1.1em; border-bottom: 1px solid; margin: 0; padding: 1px; }#cke_tooltip ul { padding: 0pt; list-style-type: none; }');}});(function(){function a(d,e,f,g){var h=d.lang.devTools,i='<a href="http://docs.cksource.com/omeditor_api/symbols/OMEDITOR.dialog.definition.'+(f?f.type=='text'?'textInput':f.type:'content')+'.html" target="_blank">'+(f?f.type:'content')+'</a>',j='<h2>'+h.title+'</h2>'+'<ul>'+'<li><strong>'+h.dialogName+'</strong> : '+e.getName()+'</li>'+'<li><strong>'+h.tabName+'</strong> : '+g+'</li>';if(f)j+='<li><strong>'+h.elementId+'</strong> : '+f.id+'</li>';j+='<li><strong>'+h.elementType+'</strong> : '+i+'</li>';return j+'</ul>';};function b(d,e,f,g,h,i){var j=e.getDocumentPosition(),k={'z-index':OMEDITOR.dialog._.currentZIndex+10,top:j.y+e.getSize('height')+'px'};c.setHtml(d(f,g,h,i));c.show();if(f.lang.dir=='rtl'){var l=OMEDITOR.document.getWindow().getViewPaneSize();k.right=l.width-j.x-e.getSize('width')+'px';}else k.left=j.x+'px';c.setStyles(k);};var c;OMEDITOR.on('reset',function(){c&&c.remove();c=null;});OMEDITOR.on('dialogDefinition',function(d){var e=d.editor;if(e._.showDialogDefinitionTooltips){if(!c){c=OMEDITOR.dom.element.createFromHtml('<div id="cke_tooltip" tabindex="-1" style="position: absolute"></div>',OMEDITOR.document);c.hide();c.on('mouseover',function(){this.show();});c.on('mouseout',function(){this.hide();});c.appendTo(OMEDITOR.document.getBody());}var f=d.data.definition.dialog,g=e.config.devtools_textCallback||a;f.on('load',function(){var h=f.parts.tabs.getChildren(),i;for(var j=0,k=h.count();j<k;j++){i=h.getItem(j);i.on('mouseover',function(){var l=this.$.id;b(g,this,e,f,null,l.substring(4,l.lastIndexOf('_')));});i.on('mouseout',function(){c.hide();});}f.foreach(function(l){if(l.type in {hbox:1,vbox:1})return;var m=l.getElement();if(m){m.on('mouseover',function(){b(g,this,e,f,l,f._.currentTabId);});m.on('mouseout',function(){c.hide();});}});});}});})();
