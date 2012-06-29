package com.apusic.md.usersphere.service;

import com.apusic.md.model.usersphere.Contact;

public interface ContactService {

	/**
	 * 添加一个联系人
	 * @param contact
	 * @param userId
	 */
	void addContact(Contact contact,int userId);

	/**
	 * 更新一个联系人
	 *
	 * @param contact
	 */
	void updateContact(Contact contact);

	/**
	 * 删除一个联系人
	 * @param id
	 */
	void deleteContact(int id);

	/**
	 * 获取一个联系人
	 * @param id
	 * @return
	 */
	Contact getContact(int id);

}
