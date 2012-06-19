package com.apusic.ebiz.smartorg.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.Group;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.SmartOrgException;
import com.apusic.ebiz.smartorg.dao.UserDao;
import com.apusic.ebiz.smartorg.util.UserPasswordCredentialPolicyManager;

@Service("smartorg_UserService")
public class UserServiceImpl implements UserService {
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);

	@Autowired
	private CrudService crudService;

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserPasswordCredentialPolicyManager upcpm;

	public User findUserById(int userid) {
		return this.crudService.retrieve(User.class, userid);
	}

	@Transactional
	public void addUser(User user) {
		String password = this.upcpm.encrypt(user);
		user.setPassword(password);
		this.crudService.create(user);
	}

	@Transactional
	public void addUser(User user, String groupName) throws SmartOrgException {
		Group group = this.groupService.getGroupByName(groupName);
		if (group == null) {
			throw new SmartOrgException(groupName + " group do not exist!");
		}
		String password = this.upcpm.encrypt(user);
		user.setPassword(password);
		this.crudService.create(user);
		user.addGroup(group);
	}

	@Transactional
	public void changePassword(String userName, String oldPwd, String newPwd)
			throws SmartOrgException {
		if(StringUtils.equals(oldPwd, newPwd)){
			return;
		}
		User user = new User();
		user.setName(userName);
		user.setPassword(oldPwd);
		String oldEncryptPwd = this.upcpm.encrypt(user);
		user = getUserByName(userName);
		if (StringUtils.equals(oldEncryptPwd, user.getPassword())) {
			user.setPassword(newPwd);
			user.setPassword(upcpm.encrypt(user));
		} else {
			throw new SmartOrgException(userName + " old password is not right!");
		}
	}

	@Transactional
	public void deleteUser(String userName){
		User user = getUserByName(userName);
		if(user!=null){
			this.crudService.delete(user);
		}
	}

	@Transactional
	public void deleteUserById(int id){
		this.crudService.delete(User.class, id);
	}

	public Page findUser4Page(GenericQueryObject query) {
		return queryService.findPageBy(query
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	public long findUserCountByQueryParameter(GenericQueryObject query) {
		return queryService.getRowCount(query.getDetachedCriteria());
	}

	public User getUserById(int id) {
		return this.crudService.retrieve(User.class, id);
	}

	public User getUserByName(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("name", userName));
		List<User> users = queryService.findBy(criteria);
		User user = new User();
		user.setName(userName);
		//List<User> result = queryService.findByExample(user);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Transactional
	public void setPassword(String userName, String password)
			throws SmartOrgException {
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		String encryptPassword = this.upcpm.encrypt(user);
		user = getUserByName(userName);
		if(!StringUtils.equals(encryptPassword, user.getPassword())){
			user.setPassword(encryptPassword);
		}
	}

	@Transactional
	public void updateUser(User user) throws SmartOrgException {
		this.crudService.update(user);//以后修改，不能通过这个方法进行修改密码
	}

	public boolean userExists(String userName) {
		return getUserByName(userName) != null;
	}

	@Transactional
	public void deleteAllUsers(Collection<User> c) {
		this.crudService.deleteAll(c);
	}

	@Transactional
	public void validatePassword(String userName, String password)
			throws SmartOrgException {
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		String encryptPwd = this.upcpm.encrypt(user);
		user = getUserByName(userName);
		if (!StringUtils.equals(encryptPwd, user.getPassword())) {
			throw new SmartOrgException(userName + " old password is not right!");
		}
	}

	public void disableByUserId(int id) {
		User user = crudService.retrieve(User.class, id);
		user.setDisbled(false);
		crudService.update(user);
	}

	public void enableByUserId(int id) {
		User user = crudService.retrieve(User.class, id);
		user.setDisbled(true);
		crudService.update(user);
	}
}
