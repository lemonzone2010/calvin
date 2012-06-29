package com.apusic.md.usersphere.service;



import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.md.model.usersphere.UserProfile;

public interface ApprovalService {

	void approve(UserProfile userProfile);

	void cancel(UserProfile userProfile);

	//得到待审核的商户列表
	Page<UserProfile> getAwaitingApprovalShopUsers();
}
