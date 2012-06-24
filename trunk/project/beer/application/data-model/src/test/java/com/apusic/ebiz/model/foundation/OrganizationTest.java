package com.apusic.ebiz.model.foundation;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-fundation.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class OrganizationTest {

	@Autowired
	@Qualifier("ebiz_CrudService")
	private CrudService crudService;
	private Organization org;
	@Test
	@Transactional
	@Rollback(false)
	public void insert() {
		org=new Organization();
		org.setOrgCode("1111");
		org.setName("1111");
		
		Organization org1=new Organization();
		org1.setOrgCode("1112");
		org1.setName("2222");
		
		org.addChild(org1);
		
		crudService.create(org);
		
		Assert.assertNotNull(org);
		Assert.assertEquals(1, org.getChilds().size());
	}
/*
	@Test
	@Transactional
	@Rollback(false)
	public void insert() {
		board3 = mock.getBoard3();// 由2块2米板+4个3#钉子组成
		mock.broad3Add6Childs(board3);

		crudRepository.create(board3);

		Assert.assertNotNull(board3);
		Assert.assertTrue(board3.hasChild());
		Assert.assertEquals(6, board3.getChilds().size());
	}

	@Test
	@Transactional
	@Rollback(false)
	public void update() {
		insert();

		MaterialEntity board3New = crudRepository.retrieve(MaterialEntity.class, board3.getId());
		
		Assert.assertNotNull(board3New);
		Assert.assertEquals(board3New, board3);
		Assert.assertTrue(board3New.hasChild());
		Assert.assertEquals(6, board3New.getChilds().size());
		
		MaterialEntity board2=mock.getBoard2();
		board3New.addChild(board2);
		crudRepository.update(board3New);//更新
		Assert.assertEquals(7, board3New.getChilds().size());
		
		String newName = "3M木板";
		board3New.setName(newName);
		crudRepository.update(board3New);//更新
		
		board2.setName("2M木板");
		crudRepository.update(board2);//更新
		
		board3New = crudRepository.retrieve(MaterialEntity.class, board3.getId());
		Assert.assertEquals(newName, board3New.getName());
	}
	@Test
	@Transactional
	@Rollback(false)
	public void delete() {
		insert();
		
		MaterialEntity board3New = crudRepository.retrieve(MaterialEntity.class, board3.getId());
		crudRepository.delete(board3New);//删除		
		board3New = crudRepository.retrieve(MaterialEntity.class, board3.getId());		
		Assert.assertNull(board3New);
	}*/
}
