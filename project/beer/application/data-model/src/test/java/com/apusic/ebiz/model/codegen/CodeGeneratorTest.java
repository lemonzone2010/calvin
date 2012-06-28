package com.apusic.ebiz.model.codegen;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.apusic.ebiz.model.foundation.Organization;
import com.apusic.ebiz.model.navigation.ApplicationInfo;
import com.apusic.ebiz.model.navigation.Navigation;
import com.apusic.ebiz.model.user.Resource;
import com.apusic.ebiz.model.user.User;

public class CodeGeneratorTest {
	@Test
	public void process() {
		CodeGenerateFactory.generateMVC(DemoEntity.class); 
		System.out.println(StringUtils.center("  代码已生成，请刷新targert   ", 80, "*"));
	}

	@Test
	public void generateCode() {
//		CodeGenerateFactory.generateMVC(Resource.class); 
//		CodeGenerateFactory.generateMVC(User.class); 
//		CodeGenerateFactory.generateMVC(Organization.class); 
//		CodeGenerateFactory.generateMVC(Navigation.class); 
		CodeGenerateFactory.generateMVC(ApplicationInfo.class); 
		System.out.println(StringUtils.center("  代码已生成，请刷新targert   ", 80, "*"));
	}
}
