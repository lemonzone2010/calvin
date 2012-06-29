package com.apusic.md.model.emarket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.apusic.ebiz.model.BaseModel;

public class HelpCenterNavigation extends BaseModel {

	
	private HelpCenterNavigation parent;
	
	private String name;
	
	private Integer level;
	
	private Set<HelpCenterNavigation> childrens;
	
	private Integer serialNumber;
	
	private String cssName;
	

	public HelpCenterNavigation getParent() {
		return parent;
	}

	public void setParent(HelpCenterNavigation parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<HelpCenterNavigation> getChildrens() {
		return childrens;
	}

	public void setChildrens(Set<HelpCenterNavigation> childrens) {
		this.childrens = childrens;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public void addChildren(HelpCenterNavigation hcn){
		if(this.childrens == null){
			this.childrens = new HashSet<HelpCenterNavigation>();
		}
		this.childrens.add(hcn);
	}
	
	public List<HelpCenterNavigation> getChildrenList(){
		return new ArrayList<HelpCenterNavigation>(this.childrens);
	}

	public String getCssName() {
		return cssName;
	}

	public void setCssName(String cssName) {
		this.cssName = cssName;
	}
}
