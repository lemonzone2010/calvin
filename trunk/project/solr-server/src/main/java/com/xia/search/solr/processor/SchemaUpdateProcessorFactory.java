package com.xia.search.solr.processor;

/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.CommitUpdateCommand;
import org.apache.solr.update.DeleteUpdateCommand;
import org.apache.solr.update.MergeIndexesCommand;
import org.apache.solr.update.RollbackUpdateCommand;
import org.apache.solr.update.UpdateHandler;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;
import org.xml.sax.SAXException;

import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.service.SolrContext;
import com.xia.search.solr.update.SchemaUpdateHandler;
import com.xia.search.solr.util.JasonUtil;
import com.xia.search.solr.xml.SolrConfig;

/**
 * Pass the command to the UpdateHandler without any modifications
 * 
 * @since solr 1.3
 */
public class SchemaUpdateProcessorFactory extends UpdateRequestProcessorFactory {
	@Override
	public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next) {
		return new SchemaUpdateProcessor(req, next);
	}
}

class SchemaUpdateProcessor extends UpdateRequestProcessor {
	private final SolrQueryRequest req;
	private final UpdateHandler updateHandler;

	public SchemaUpdateProcessor(SolrQueryRequest req, UpdateRequestProcessor next) {
		super(next);
		this.req = req;
		this.updateHandler = new SchemaUpdateHandler(req.getCore());
	}

	@Override
	public void processAdd(AddUpdateCommand cmd) throws IOException {
		// FIXME modify schema here
		SolrInputDocument docs = cmd.getSolrInputDocument();
		for (SolrInputField field : docs) {
			FieldAdaptor f = JasonUtil.toObjectFromJson(field.getValue().toString(), FieldAdaptor.class);
			String name = f.getEntityName() + SolrContext.ENTITY_FIELD_SPILT + f.getFieldName();
			SchemaField field2 = req.getSchema().getFieldOrNull(name);
			if (null == field2) {
				SolrConfig.getSchemaConfig().addField(f);
			}
		}
		boolean needReload = SolrConfig.getSchemaConfig().isChanged();
		SolrConfig.getSchemaConfig().save();
		if (needReload) {
			// schema结束后，重载
			CoreContainer coreContainer = req.getCore().getCoreDescriptor().getCoreContainer();
			coreContainer.getCoreNames();
			for (String coreName : coreContainer.getCoreNames()) {
				try {
					coreContainer.reload(coreName);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// cmd.doc = DocumentBuilder.toDocument(docs, req.getSchema());
		// updateHandler.addDoc(cmd);
		super.processAdd(cmd);
	}

	@Override
	public void processDelete(DeleteUpdateCommand cmd) throws IOException {
		if (cmd.id != null) {
			updateHandler.delete(cmd);
		} else {
			updateHandler.deleteByQuery(cmd);
		}
		super.processDelete(cmd);
	}

	@Override
	public void processMergeIndexes(MergeIndexesCommand cmd) throws IOException {
		updateHandler.mergeIndexes(cmd);
		super.processMergeIndexes(cmd);
	}

	@Override
	public void processCommit(CommitUpdateCommand cmd) throws IOException {
		updateHandler.commit(cmd);
		super.processCommit(cmd);
	}

	/**
	 * @since Solr 1.4
	 */
	@Override
	public void processRollback(RollbackUpdateCommand cmd) throws IOException {
		updateHandler.rollback(cmd);
		super.processRollback(cmd);
	}
}
