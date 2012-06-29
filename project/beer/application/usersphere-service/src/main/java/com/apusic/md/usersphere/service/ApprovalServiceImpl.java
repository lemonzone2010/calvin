package com.apusic.md.usersphere.service;


import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.framework.core.dao.GenericQueryObject;
import com.apusic.ebiz.framework.core.dao.Page;
import com.apusic.ebiz.framework.core.mail.MailBuilder;
import com.apusic.ebiz.framework.core.mail.MailService;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.MemberType;
import com.apusic.md.model.usersphere.UserProfile;

@Service("usersphere_ApprovalService")
public class ApprovalServiceImpl implements ApprovalService{

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("usersphere_approvalFailMailBuilder")
	private MailBuilder mailBuilder;

	@Autowired
	private MailService mailService;

	@Transactional
	public void approve(UserProfile userProfile){
		//设置用户为可用，disbled为true
		userProfile.getUser().setDisbled(true);
		//更新MemberType为商户
		userProfile.setMemberType(MemberType.SHOP);
	}

	/**
	 *审核不通过
	 */
	@Transactional
	public void cancel(UserProfile userProfile) {
		//更新MemberType为商户
		userProfile.setMemberType(MemberType.CACELLED_SHOP);
		//发送邮件
		Map context = new HashMap();
		context.put("userName", userProfile.getUser().getName());
		mailBuilder.setTo(userProfile.getEmail());
		mailService.sendAsynchronously(mailBuilder, context);
	}

	/**
	 * 得到待审核的商户列表
	 */
	@Transactional(readOnly=true)
	public Page<UserProfile> getAwaitingApprovalShopUsers() {
		GenericQueryObject queryObject = new GenericQueryObject(UserProfile.class);
		//待审批商城商户
		queryObject.eq("memberType", MemberType.UNATHORIZED_SHOP);
		queryObject.getDetachedCriteria().addOrder(Order.desc("createTime")); 
		return userProfileService.findUserProfile4Page(queryObject);
	}







}
