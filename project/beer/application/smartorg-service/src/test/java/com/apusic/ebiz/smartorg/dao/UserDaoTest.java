package com.apusic.ebiz.smartorg.dao;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-ebiz-smartorg-service-user.xml" })
public class UserDaoTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CrudService crudService;

	@Test
	@Transactional
	public void findUserByParameter(){

		User user = new User();
		user.setName("user");
		user.setPassword("123");
		crudService.create(user);

		GenericQueryObject para = new GenericQueryObject(User.class);
		para.in("name", Arrays.asList(new String[]{"user", "ff"}));
		Page page = userDao.findUserByParameter(para);
		assertTrue(page.getObjects().size()==1);
	}
}
