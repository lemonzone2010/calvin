package com.apusic.md.usersphere.service;

import com.apusic.md.model.usersphere.RetrievePW;
import com.apusic.md.model.usersphere.UserProfile;

public interface RetrievePwService {

	/**
	 * 提交一个重置密码的服务
	 * @param p
	 */
	void submitRetrievePw(RetrievePW p);

	/**
	 * 重置密码
	 * @param p
	 * @param password
	 */
	void resetPassword(RetrievePW p,String password);

	/**
	 *验证重置服务URL是否有效
	 * @param userName
	 * @param resetCode
	 */
	void validateRetrievePwURL(String userName,String resetCode);

	/**
	 * 通过Username得到UserProfile信息
	 * @return
	 */
	UserProfile getUserProfilebyName(String name);

}
