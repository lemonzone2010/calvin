package com.apusic.md.usersphere.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.apusic.ebiz.framework.core.dao.CrudService;
import com.apusic.ebiz.framework.core.mail.MailBuilder;
import com.apusic.ebiz.framework.core.mail.MailService;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserService;
import com.apusic.md.model.usersphere.RetrievePW;
import com.apusic.md.model.usersphere.UserProfile;
import com.apusic.md.usersphere.UsersphereException;
import com.apusic.md.usersphere.dao.RetrievePwDao;

@Service("usersphere_RetrievePwService")
public class RetrievePwServiceImpl implements RetrievePwService {

	@Autowired
	@Value(value="2")
	private Integer timeoutDay;

	@Autowired
	private CrudService crudService;

	@Autowired
	private UserService userService;

	@Autowired
	private RetrievePwDao retrievePwDao;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	@Qualifier("usersphere_sendMailBuild")
	private MailBuilder mailBuilder;

	@Autowired
	private MailService mailService;

	@Transactional
	public void submitRetrievePw(RetrievePW p) {
		User user = userService.getUserByName(p.getName());
		Assert.notNull(user,"该用户不存在！");
		UserProfile profile = userProfileService.getUserProfilebyUser(user);
		Assert.notNull(profile,"用户不存在！");
		String securityCode = RandomStringUtils.randomNumeric(6);
		String resetCode = DigestUtils.md5Hex(securityCode + p.getName());

		p.setEmail(profile.getEmail());
		p.setName(user.getName());
		p.setResetCode(resetCode);
		p.setSecurityCode(securityCode);
		Date currenttime = new Date();
		Date timeoutDate = DateUtils.addDays(currenttime, timeoutDay);
		p.setTimeout(timeoutDate);

		RetrievePW retrievePW = retrievePwDao.getRetrievePWByName(user.getName());
		if(retrievePW!=null){
			p.setId(retrievePW.getId());
			crudService.update(p);
		}else{
			crudService.create(p);
		}

		Map context = new HashMap();
		context.put("userName", p.getName());
		context.put("datetime", (new Date()).toString());
		context.put("resetCode", p.getResetCode());
		context.put("securityCode", p.getSecurityCode());
		mailBuilder.setTo(p.getEmail());
		mailService.sendAsynchronously(mailBuilder, context);
	}

	@Transactional
	public void resetPassword(RetrievePW p,String password){
		RetrievePW retrievePW = this.retrievePwDao.getRetrievePWByName(p.getName());
		if(retrievePW==null){
			throw new UsersphereException("该重置服务非法!");
		}

		if(!StringUtils.equals(retrievePW.getName(), p.getName())){
			throw new UsersphereException("该重置服务非法!");
		}

		if(!StringUtils.equals(p.getResetCode(), retrievePW.getResetCode())){
			throw new UsersphereException("该重置服务非法!");
		}

		if(!StringUtils.equals(p.getSecurityCode(), retrievePW.getSecurityCode())){
			throw new UsersphereException("该重置服务非法!");
		}

		Date d = new Date();
		if(retrievePW.getTimeout()!=null && d.after(retrievePW.getTimeout())){
			throw new UsersphereException("该重置服务已经过期");
		}
		userService.setPassword(retrievePW.getName(), password);
		crudService.delete(retrievePW);
	}

	@Transactional
	public void validateRetrievePwURL(String userName, String resetCode) {
		RetrievePW retrievePW = this.retrievePwDao.getRetrievePWByName(userName);
		if(retrievePW==null){
			throw new UsersphereException("该重置服务非法!");
		}
		if(!StringUtils.equals(resetCode, retrievePW.getResetCode())){
			throw new UsersphereException("该重置服务非法!");
		}
	}

	@Transactional
	public UserProfile getUserProfilebyName(String name) {
		User user = userService.getUserByName(name);
		Assert.notNull(user,"该用户不存在！");
		UserProfile profile = userProfileService.getUserProfilebyUser(user);
		Assert.notNull(profile,"用户不存在！");
		return profile;
	}
}
