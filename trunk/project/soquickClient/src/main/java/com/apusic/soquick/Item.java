package com.apusic.soquick;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class Item {
	@Field
	String id;
	@Field
	String host;
	@Field
	String url;
	@Field
	String title;
	@Field
	String content;
	@Field
	Date publishedDate;
	@Override
	public String toString() {
		return "Item [id=" + id + ", host=" + host + ", title=" + title
				+ ", content=" + content + ", publishedDate=" + publishedDate
				+ "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	/*
	 * @Field("cat") String[] categories;
	 * 
	 * @Field List<String> features;
	 */

}