package com.apusic.ebiz.smartorg.dao;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.apusic.ebiz.framework.core.config.ConfigService;
import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.dao.QueryService;
import com.apusic.ebiz.model.user.User;
/**
 * When the data manipulation is complicated, write the data manipulation
 * logic to the dao objects.
 * @author achen
 *
 */

@Repository("smartorg_UserDao")
public class UserDaoImpl implements UserDao{


	@Autowired
	private QueryService queryService;

	@Autowired
	@Qualifier("ebiz_ConfigService")
	private ConfigService configService;

	public Page<User> findUserByRole(String roleName) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		detachedCriteria.add(Restrictions.eq("sex", "m"));
		String pageSize = configService.getValueByKey("pageSize");
		int pSize = NumberUtils.toInt(pageSize);
		//The page size can be configurable
		return queryService.findPageBy(detachedCriteria, pSize);
	}

	public Page findUserByParameter(GenericQueryObject para) {
		String pageSize = configService.getValueByKey("pageSize");
		int pSize = NumberUtils.toInt(pageSize);
		//The page size can be configurable
		return queryService.findPageBy(para.getDetachedCriteria(), pSize);
	}

	public User getUserByName(String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("name", userName));
		List<User> users = queryService.findBy(criteria);
		User user = new User();
		user.setName(userName);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

}
