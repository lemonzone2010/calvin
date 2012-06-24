package com.hme.ebiz.foundation.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.foundation.Organization;
import com.apusic.ebiz.model.util.Util;
import com.home.ebiz.foundation.service.OrganizationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-hibernate.xml" ,"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class OrganizationServiceTest {
	@Autowired
	private OrganizationService organizationService;
	
	@Test
	public void findNoChildTree() {
		Page<Organization> findNoChild = organizationService.findNoChild();
		System.out.println(Util.toJson(findNoChild));
		List<Organization> rows = findNoChild.getRows();
		for (Organization organization : rows) {
			Assert.assertTrue(organization.getChilds().size()<1);
		}
	}
}
