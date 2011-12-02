package com.apusic.soquick;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "F_Table")
public class ItemH {
	@Column
	String id;
	@Column(name = "F_title")
	String title;
	@Column(name = "F_content")
	String content;
	@Column(name = "F_publishedDate")
	Date publishedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ItemH [id=" + id + ", title=" + title + ", content=" + content + ", publishedDate=" + publishedDate
				+ "]";
	}

}
