package com.apusic.ebiz.model.datamanagement;

import java.util.Set;

import com.apusic.ebiz.model.BaseModel;

public class Category extends BaseModel {

	private int id;

	private String name;
	private String intro;
	private Set<ConfigurationData> datas;
	
	public Category(){}
	
	public Category(String name){
		this.name = name;
	}
	
	public Category(int id,String name){
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ConfigurationData> getDatas() {
		return datas;
	}

	public void setDatas(Set<ConfigurationData> datas) {
		this.datas = datas;
	}
}
