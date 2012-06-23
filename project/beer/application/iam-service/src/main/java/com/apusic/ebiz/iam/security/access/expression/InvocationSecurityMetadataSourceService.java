package com.apusic.ebiz.iam.security.access.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.Role;
import com.apusic.ebiz.smartorg.service.ResourceService;
import com.apusic.ebiz.smartorg.service.RoleResourceService;

@Service("iam_SecurityMetadataSource")
public class InvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	private static final Log logger = LogFactory.getLog(InvocationSecurityMetadataSourceService.class);

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private RoleResourceService roleResourceService;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private TransactionTemplate transactionTemplate;

	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	@PostConstruct
	public void inits() {
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus arg0) {
						loadResourceDefine();
					}
				});
	}

	public void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		// 通过数据库中的信息设置，resouce和role
		for (Resource item : resourceService.getAllResources()) {
			List<Role> roles = roleResourceService.findRolesbyResourceId(item.getId());
			if (roles != null && roles.size() > 0) {
				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
				for (Role role : roles) {
					atts.add(new SecurityConfig(role.getName()));
				}
				if (logger.isDebugEnabled()) {
					logger.debug("resouce：[" + item.getResName() + "]拥有的role有：" + atts);
				}

				resourceMap.put("/" + item.getResContext() + item.getResValue(), atts);
			}
		}
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		HttpServletRequest request = filterInvocation.getHttpRequest();

		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(resURL,request.getMethod());
			if (requestMatcher.matches(request)) {
				Collection<ConfigAttribute> returnCollection = resourceMap.get(resURL);
				return returnCollection;
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}
}
