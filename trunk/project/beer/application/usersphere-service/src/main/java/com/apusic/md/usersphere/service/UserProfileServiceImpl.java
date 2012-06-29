package com.apusic.md.usersphere.service;

import java.util.List;

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
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.UsersphereException;
import com.apusic.md.usersphere.dao.UserProfileDao;

@Service("usersphere_UserProfileService")
public class UserProfileServiceImpl implements UserProfileService {

	private static final Log logger = LogFactory.getLog(UserProfileServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CrudService crudService;

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	@Transactional
	public void addUserProfile(UserProfile profile, int userId) {
		User user = this.userService.getUserById(userId);
		if(user==null){
			logger.error("用户id = " +userId + "的用户不存在");
			throw new UsersphereException("用户id = " +userId + "的用户不存在");
		}
		profile.setUser(user);
		crudService.create(profile);
	}

	@Transactional
	public void deleteUserProfile(int id) {
		crudService.delete(UserProfile.class, id);
	}

	public UserProfile getUserProfile(int id) {
		return this.crudService.retrieve(UserProfile.class, id);
	}

	@Transactional
	public void updateUserProfile(UserProfile profile) {
		this.crudService.update(profile);
	}

	@Transactional
	public UserProfile getUserProfilebyUser(User user) {
		return userProfileDao.getUserProfilebyUser(user);
	}

	public Page<UserProfile> findUserProfile4Page(GenericQueryObject query) {
		return queryService.findPageBy(query
				.getDetachedCriteria(), configService
				.getIntegerValueByKey("pageSize"));
	}

	public Page<UserProfile> findMemberUser4Page(GenericQueryObject query) {
		query.getParameters().put("memberType", MemberType.PERSONAL);
		return findUserProfile4Page(query);
	}

	public UserProfile getUserProfileByUserId(int id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
		criteria.createAlias("user", "user").add(Restrictions.eq("user.id", id));

		List<UserProfile> profiles=queryService.findBy(criteria);
		if (profiles.size()>0) {
			return profiles.get(0);
		}
		return null;
	}
}
