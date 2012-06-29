package com.apusic.md.usersphere.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.DeliveryAddr;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml",
		"classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class DeliveryAddrDaoTest {

	@Autowired
	private DeliveryAddrDao deliveryAddrDao;

	@Autowired
	private UserService userService;

	@Autowired
	private CrudService crudService;

	static boolean isBefore = false;

	@Before
	public void init() {
		if (isBefore) {
			return;
		}
		isBefore = true;
		User u = new User();
		u.setName("adminDaoTest");
		u.setPassword("adminDaoTest");
		userService.addUser(u);

		User u2 = new User();
		u2.setName("admin2DaoTest");
		u2.setPassword("admin2DaoTest");
		userService.addUser(u2);

		DeliveryAddr d = new DeliveryAddr();
		d.setIsDefault(1);
		d.setArea("南市区");
		d.setCity("深圳市");
		d.setConsignee("张三");
		d.setMobile("13714332174");
		d.setPhone("0755-89868958");
		d.setPostcode("518000");
		d.setProvince("广东省");
		d.setStreetAddr("南山科技园");
		d.setUser(u);
		crudService.create(d);

		DeliveryAddr d2 = new DeliveryAddr();
		d2.setIsDefault(0);
		d2.setArea("南市区2");
		d2.setCity("深圳市2");
		d2.setConsignee("张三2");
		d2.setMobile("13714332174");
		d2.setPhone("0755-89868958");
		d2.setPostcode("518000");
		d2.setProvince("广东省");
		d2.setStreetAddr("南山科技园");
		d2.setUser(u);
		crudService.create(d2);
	}

	@Test
	public void getDeliveryAddrCount() {
		long deliveryAddrCount = deliveryAddrDao
				.getDeliveryAddrCount("adminDaoTest");
		Assert.assertEquals(2, deliveryAddrCount);

		long deliveryAddrCount2 = deliveryAddrDao
				.getDeliveryAddrCount("admin2DaoTest");
		Assert.assertEquals(0, deliveryAddrCount2);
	}

	@Test
	public void getDeliveryAddrByUserName() {
		List<DeliveryAddr> deliveryAddrs = deliveryAddrDao
				.getDeliveryAddrsByUserName("adminDaoTest");
		Assert.assertTrue(deliveryAddrs.size() == 2);
	}

	@Test
	public void getDefaultAddrByUserName() {
		DeliveryAddr deliveryAddr = deliveryAddrDao
				.getDefaultAddrByUserName("adminDaoTest");
		Assert.assertTrue(deliveryAddr.getMobile().equals("13714332174"));
	}

}
