package com.apusic.ebiz.framework.core.solr;

import java.io.StringWriter;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.SessionFactory;
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
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.framework.core.solr.DocumentHelper.FieldAdaptor;
import com.apusic.ebiz.framework.core.solr.DocumentHelper.SolrSchemaDocument;

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
		DocumentHelper documentHelper=new DocumentHelper(sessionFactory.getCurrentSession());
		DummyBook book=new DummyBook();
		
		SolrSchemaDocument document = documentHelper.getDocument(book);
		Assert.assertEquals(5, document.getFields().size());
		
		for (FieldAdaptor field : document.getFields()) {
			System.out.println(toJson(field));
		}

		documentHelper.setCanAddEmptyField(false);
		document = documentHelper.getDocument(book);
		Assert.assertEquals(1, document.getFields().size());
		
		DummyUser user=new DummyUser();
		documentHelper.setCanAddEmptyField(true);
		document = documentHelper.getDocument(user);
		Assert.assertEquals(0, document.getFields().size());
	}

	public static String toJson(Object obj) {
		StringWriter sw=new StringWriter();
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			mapper.writeValue(sw, obj);
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

}
