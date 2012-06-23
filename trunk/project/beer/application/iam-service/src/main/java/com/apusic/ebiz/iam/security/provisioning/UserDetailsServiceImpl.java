package com.apusic.ebiz.iam.security.provisioning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.model.user.User;
import com.apusic.ebiz.smartorg.service.UserRoleService;
import com.apusic.ebiz.smartorg.service.UserService;

@Service("iam_UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(UserDetailsServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = this.userService.getUserByName(username);
		if(user==null){
			String message = "用户" + username + "不存在";
			logger.error(message);
			throw new UsernameNotFoundException(message);
		}
		List<Role> roles = userRoleService.findRolesByUserId(user.getId());
		Collection<GrantedAuthority> auths=new ArrayList<GrantedAuthority>();
		if(roles!=null && roles.size() > 0){
			for(Role item : roles){
				 GrantedAuthorityImpl grantedAuthorityImpl = new GrantedAuthorityImpl(item.getName());
				 auths.add(grantedAuthorityImpl);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("用户：" + username + "拥有角色:" + auths);
		}
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isDisbled(), true, true, true, auths);
	}
}
