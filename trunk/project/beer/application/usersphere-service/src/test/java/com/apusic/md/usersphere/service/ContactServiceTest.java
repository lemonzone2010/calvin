package com.apusic.md.usersphere.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:apusic-ebiz-framework-core.xml",
		"classpath*:apusic-md-usersphere-service.xml", "classpath*:apusic-ebiz-smartorg-service.xml",
		"classpath*:apusic-md-usersphere-service-test.xml" })
@Transactional
public class ContactServiceTest {

	@Autowired
	private UserService userService;

	@Autowired
	private ContactService contactService;

	@Test
	public void addContact(){
		User user = new User();
		user.setName("ContactServiceTest");
		user.setPassword("admin");
		userService.addUser(user);

		Contact c= new Contact();
		c.setAddress("address");
		c.setContactName("contactName");
		c.setEmail("email@apusic.com");
		c.setUser(user);
		contactService.addContact(c, 1);
	}

	@Test
	public void udpateContact(){
		Contact contact = contactService.getContact(1);
		contact.setAddress("xxxxx");
		contactService.updateContact(contact);
	}

	@Test
	public void deleteContact(){
		contactService.deleteContact(1);
		User userById = userService.getUserById(1);
		Assert.assertNotNull(userById);
	}

}
