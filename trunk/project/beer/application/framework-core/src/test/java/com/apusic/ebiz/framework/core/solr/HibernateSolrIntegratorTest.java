/*package com.apusic.ebiz.framework.core.solr;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.SessionFactory;
import org.hibernate.search.query.engine.spi.EntityInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.DummyBook;
import com.apusic.ebiz.framework.core.DummyUser;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.xia.search.solr.entity.SolrEntityInfoImpl;
import com.xia.search.solr.entity.SolrObjectLoaderHelper; 
//import com.xia.search.solr.query.SolrQueryHelper;
import com.xia.search.solr.query.Page;
import com.xia.search.solr.query.Query;
import com.xia.search.solr.schema.SolrDocumentHelper;
import com.xia.search.solr.schema.FieldAdaptor;
import com.xia.search.solr.schema.SolrSchemaDocument;
import com.xia.search.solr.service.SolrContext;
import com.xia.search.solr.service.SolrService;
import com.xia.search.solr.service.SolrServiceImpl;
//import com.xia.search.solr.update.SolrUpdateHelper;
import com.xia.search.solr.util.JasonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:apusic-ebiz-framework-core.xml",
		"classpath:apusic-ebiz-framework-core-solr.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class HibernateSolrIntegratorTest {

	@Autowired
	@Qualifier("queryRepository")
	QueryService queryService;
	
	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void search() {
		//List<DummyBook> findAll = queryService.findAll(DummyBook.class);
		//System.out.println(findAll);
		Map<Class<?>, SolrEntity> mappingMap = SolrMapping.getMappingMap();
		Assert.assertTrue(mappingMap.size()==2);//has 2mapping files
	}
	@Test
	public void document() {
		SolrDocumentHelper documentHelper=new SolrDocumentHelper(sessionFactory);
		DummyBook book=new DummyBook();
		
		
		SolrSchemaDocument document = documentHelper.getDocument(book);
		Assert.assertEquals(5, document.getFields().size());
		
		for (FieldAdaptor field : document.getFields()) {
			System.out.println(JasonUtil.toJsonString(field));
		}

		documentHelper.setCanAddEmptyField(false);
		document = documentHelper.getDocument(book);
		Assert.assertEquals(1, document.getFields().size());
		
		DummyUser user=new DummyUser();
		documentHelper.setCanAddEmptyField(true);
		document = documentHelper.getDocument(user);
		Assert.assertEquals(0, document.getFields().size());
	}
	@Test
	public void indexOne() throws SolrServerException, IOException {
		DummyBook book=new DummyBook();
		book.setTitle("我是你大爷夏勇先生一天二天三天");
		book.setSubtitle("是你哦，啊昌听");
		book.setPublicationDate(new Date());		
		crudService.create(book);
		
		SolrDocumentHelper documentHelper=new SolrDocumentHelper(sessionFactory);
		SolrSchemaDocument document = documentHelper.getDocument(book);
		System.out.println(document);
		//SolrUpdateHelper.updateSchema(document);
		
	}
	@Test
	public void query() throws Exception {
		DummyBook book=new DummyBook();
		book.setTitle("11111");
		book.setSubtitle("2222");
		
		crudService.create(book);
		
		SolrService service=SolrContext.getMySolrService();
		List<DummyBook> list = queryService.findAll(DummyBook.class);
		service.indexSaveOrUpdate(list.toArray());
		
		Page<DummyBook> page = service.query(new Query(DummyBook.class, "id", "15").or("id", "16"));
		assertNotNull(page);
		assertTrue(page.getNumFound()==2);
	}
	@Test
	public void loadEntity() {
		DummyBook book=new DummyBook();
		book.setTitle("11111");
		book.setSubtitle("2222");
		
		crudService.create(book);
		EntityInfo entityInfo=new SolrEntityInfoImpl(book.getClass(), "id",book.getId(), null);
		DummyBook load = (DummyBook) SolrObjectLoaderHelper.load(entityInfo, sessionFactory.getCurrentSession());
		Assert.assertEquals(book.getId(), load.getId());
	}

}
*/