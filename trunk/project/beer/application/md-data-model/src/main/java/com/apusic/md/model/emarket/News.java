package com.apusic.md.model.emarket;

import java.util.Date;

import com.apusic.ebiz.model.BaseModel;

/**
 * 公告实体
 * @author xuzhengping
 *
 */
public class News extends BaseModel {


	private String name;//标题

	private Date createTime;

	private String content;//内容


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
