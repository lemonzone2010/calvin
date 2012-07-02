package com.xia.search.solr.update;

import java.io.IOException;
import java.net.URL;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrCore;
import org.apache.solr.core.SolrInfoMBean.Category;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.CommitUpdateCommand;
import org.apache.solr.update.DeleteUpdateCommand;
import org.apache.solr.update.DirectUpdateHandler2;
import org.apache.solr.update.MergeIndexesCommand;
import org.apache.solr.update.RollbackUpdateCommand;
import org.apache.solr.update.UpdateHandler;

public class SchemaUpdateHandler extends UpdateHandler {

	public SchemaUpdateHandler(SolrCore core) {
		super(core);
	}

	@Override
	public NamedList getStatistics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addDoc(AddUpdateCommand cmd) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("need to add:"+cmd);
		return 0;
	}

	@Override
	public void delete(DeleteUpdateCommand cmd) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByQuery(DeleteUpdateCommand cmd) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int mergeIndexes(MergeIndexesCommand cmd) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void commit(CommitUpdateCommand cmd) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback(RollbackUpdateCommand cmd) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

	public String getName() {
		return DirectUpdateHandler2.class.getName();
	}

	public String getVersion() {
		return SolrCore.version;
	}

	public String getDescription() {
		return "Update handler that efficiently directly updates the on-disk main lucene index";
	}

	public Category getCategory() {
		return Category.UPDATEHANDLER;
	}

	public String getSourceId() {
		return "$Id: DirectUpdateHandler2.java 1297074 2012-03-05 14:40:17Z uschindler $";
	}

	public String getSource() {
		return "$URL: https://svn.apache.org/repos/asf/lucene/dev/branches/branch_3x/solr/core/src/java/org/apache/solr/update/DirectUpdateHandler2.java $";
	}

	public URL[] getDocs() {
		return null;
	}

}
