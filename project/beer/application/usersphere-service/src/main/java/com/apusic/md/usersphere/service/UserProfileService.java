package com.apusic.md.usersphere.service;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.model.user.User;
import com.apusic.md.model.usersphere.UserProfile;

public interface UserProfileService {

	/**
	 * 添加一个用户扩展属性
	 * @param profile
	 * @param userId
	 */
	void addUserProfile(UserProfile profile,int userId);

	/**
	 * 更新一个扩展属性
	 * @param profile
	 */
	void updateUserProfile(UserProfile profile);

	/**
	 * 删除一个用户的扩展属性
	 * @param id
	 */
	void deleteUserProfile(int id);

	/**
	 *获取用户的扩展属性
	 * @param id
	 * @return
	 */
	UserProfile getUserProfile(int id);

	/**
	 * 获取user的扩展属性
	 * @param user
	 * @return
	 */
	UserProfile getUserProfilebyUser(User user);

	/**
	 * 分页查询用户扩展信息
	 * @return
	 */
	Page<UserProfile> findUserProfile4Page(GenericQueryObject query);


	/**
	 * 分页查询普通会员扩展信息
	 * @return
	 */
	Page<UserProfile> findMemberUser4Page(GenericQueryObject query);

	UserProfile getUserProfileByUserId(int id);


}
