package com.apusic.md.emarket.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.apusic.ebiz.framework.core.job.ScheduleJob;

public class HomepageHtmlScheduleJob implements ScheduleJob {
	public static final Log logger = LogFactory
			.getLog(HomepageHtmlScheduleJob.class);

	@Autowired
	private HomepageHtmlGenerator homepageHtmlGenerator;

	public HomepageHtmlScheduleJob() {
		if (logger.isDebugEnabled()) {
			logger.debug("####已装载定时任务 HomepageHtmlScheduleJob");
		}
	}

	public void perform() {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("#### 执行定时任务  HomepageHtmlScheduleJob  生成静态主页");
			}
			homepageHtmlGenerator.generate(0);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("由定时任务 HomepageHtmlScheduleJob 生成静态主页失败", e);
			}
		}
	}

}
