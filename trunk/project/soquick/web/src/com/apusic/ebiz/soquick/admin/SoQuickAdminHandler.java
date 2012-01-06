package com.apusic.ebiz.soquick.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.params.CoreAdminParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.apache.solr.handler.admin.CoreAdminHandler;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

public class SoQuickAdminHandler extends CoreAdminHandler {
	
	/**
	 * Overloaded ctor to inject CoreContainer into the handler.
	 * 
	 * @param coreContainer Core Container of the solr webapp installed.
	 */
	public SoQuickAdminHandler(final CoreContainer coreContainer) {
		super(coreContainer);
	}

	protected boolean handleCustomAction(SolrQueryRequest req,
			SolrQueryResponse rsp) {
		CoreContainer container = super.getCoreContainer();
		SolrParams params = req.getParams(); 
		String action = params.get( CoreAdminParams.ACTION );
		if ("update".equalsIgnoreCase(action)){
			String cname = params.get(CoreAdminParams.CORE); 
			String instanceDir = params.get(CoreAdminParams.INSTANCE_DIR);
			String indexDir = params.get(CoreAdminParams.INDEX_DIR);
			String dataDir = params.get(CoreAdminParams.DATA_DIR);
			SolrCore core = coreContainer.getCore(cname);
		    if (core != null) {
		    	if (StringUtils.isNotBlank(instanceDir)){
		    		//TODO: UPDATE THE INSTANCE DIR
		    	}
		    	
		    	if (StringUtils.isNotBlank(dataDir)){
		    		core.getCoreDescriptor().setDataDir(dataDir);
		    	}
		    	
		    }
		}
	
		return true;
	}
}
