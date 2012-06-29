package com.apusic.md.usersphere.service;

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
public class DeliveryAddrServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private DeliveryAddrService deliveryAddrService;


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
		u.setName("adminAddrServiceTest");
		u.setPassword("admin");

		userService.addUser(u);

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
	public void getDeliveryAddrsByUserName() {
		List<DeliveryAddr> deliveryAddrs = deliveryAddrService
				.getDeliveryAddrsByUserName("adminAddrServiceTest");
		Assert.assertTrue(deliveryAddrs.size() == 2);
	}

	@Test
	public void getDeliveryAddrById() {
		DeliveryAddr deliveryAddr = deliveryAddrService.getDeliveryAddrById(1);
		Assert.assertTrue(deliveryAddr.getId() == 1);


	}
	@Test
	public void getDefalutAddrByUserName() {
		DeliveryAddr deliveryAddr = deliveryAddrService
				.getDefalutAddrByUserName("adminAddrServiceTest");
		Assert.assertTrue(deliveryAddr.getIsDefault() == 1);
	}

	@Test
	public void changeDefault() {


		List<DeliveryAddr> addrs=deliveryAddrService.getDeliveryAddrsByUserName("adminAddrServiceTest");
		int id=addrs.get(0).getId();
		deliveryAddrService.changeDefault("adminAddrServiceTest", id);
		DeliveryAddr addr = deliveryAddrService.getDeliveryAddrById(id);
		Assert.assertTrue(addr.getIsDefault() == 1);

	}

	@Test
	public void addDeliveryAddr() {
		// User user=userService.getUserByName("admin");
		DeliveryAddr deliveryAddr = new DeliveryAddr();
		deliveryAddr.setArea("南山区3");
		deliveryAddr.setCity("深圳市3");
		deliveryAddr.setConsignee("肖君诺3");
		// deliveryAddr.setIsDefault(0);
		deliveryAddr.setMobile("111111113");
		deliveryAddr.setPhone("22222222");
		deliveryAddr.setPostcode("333333");
		deliveryAddr.setProvince("广东省");
		deliveryAddr.setStreetAddr("金蝶软件园B栋5楼北");

		deliveryAddrService.addDeliveryAddr(deliveryAddr, "adminAddrServiceTest", true);
		DeliveryAddr deliveryAddr2 = deliveryAddrService
				.getDeliveryAddrById(deliveryAddr.getId());
		Assert.assertTrue(deliveryAddr2.getId() > 0);
		Assert.assertTrue(deliveryAddr2.getIsDefault() > 0);

		// deliveryid=deliveryAddr.getId();
	}

	@Test
	@Transactional
	public void updateDeliveryAddr() {

		DeliveryAddr deliveryAddr = deliveryAddrService.getDeliveryAddrById(2);
		deliveryAddr.setIsDefault(1);
		deliveryAddr.setCity("悉尼");
		User user = userService.getUserById(deliveryAddr.getUser().getId());
		deliveryAddr.setUser(user);
		deliveryAddrService.updateDeliveryAddr(deliveryAddr,"adminAddrServiceTest");

		deliveryAddr = deliveryAddrService.getDeliveryAddrById(2);
		Assert.assertTrue(deliveryAddr.getCity().equals("悉尼"));
		Assert.assertTrue(deliveryAddr.getIsDefault() > 0);

	}

	@Test
	public void deleteDeliveryAddr() {

		deliveryAddrService.deleteDeliveryAddr(2);
		DeliveryAddr deliveryAddr = deliveryAddrService.getDeliveryAddrById(2);

		Assert.assertTrue(deliveryAddr == null);
	}

}
