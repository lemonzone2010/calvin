package com.apusic.md.usersphere.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.Contact;
import com.apusic.md.usersphere.UsersphereException;

@Service("usersphere_ContactService")
public class ContactServiceImpl implements ContactService {

	private static final Log logger = LogFactory.getLog(ContactServiceImpl.class);

	@Autowired
	private CrudService crudService;

	@Autowired
	private UserService userService;

	@Transactional
	public void addContact(Contact contact, int userId) {
		User user = userService.getUserById(userId);
		if(user==null){
			logger.error("用户id = " +userId + " 的用户不存在");
			throw new UsersphereException("用户id = " +userId + " 的用户不存在");
		}
		contact.setUser(user);
		crudService.create(contact);
	}

	@Transactional
	public void deleteContact(int id) {
		this.crudService.delete(Contact.class, id);
	}

	public Contact getContact(int id) {
		return crudService.retrieve(Contact.class, id);
	}

	@Transactional
	public void updateContact(Contact contact) {
		this.crudService.update(contact);
	}

}
