package com.apusic.md.usersphere.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.usersphere.UserProfile;

@Repository("usersphere_UserProfileDao")
public class UserProfileDaoImpl implements UserProfileDao {

	@Autowired
	private QueryService queryService;

	public UserProfile getUserProfilebyUser(User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserProfile.class);
		criteria.add(Restrictions.eq("user", user));
		List<UserProfile> profiles = queryService.findBy(criteria);
		if(profiles!=null && profiles.size() > 0){
			return profiles.get(0);
		}
		return null;
	}
}
