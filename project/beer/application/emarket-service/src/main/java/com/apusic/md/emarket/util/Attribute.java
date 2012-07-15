package com.apusic.md.emarket.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Attribute {
	private String name;
	private String[] items;

	public Attribute(String name, String[] items) {
		super();
		this.name = name;
		this.items = items;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getItems() {
		return items;
	}
	public void setItems(String[] items) {
		this.items = items;
	}

	public static List<Attribute> parseAttribute(String customization) {
		List<Attribute> customizations=new ArrayList<Attribute>();
		//String customization= product.getCustomization();
		//颜色:红色|蓝色|黑色;尺码:L|M|XL|XXL
		if(StringUtils.isBlank(customization)){
			return new ArrayList<Attribute>(0);
		}
		String[] items=StringUtils.split(customization, ";");
		for (String item : items) {
			String[] nameAndValue= StringUtils.split(item,":");
			if(nameAndValue.length<2){
				continue;
			}
			Attribute c=new Attribute(nameAndValue[0],StringUtils.split(nameAndValue[1], "|"));
			customizations.add(c);
		}
		return customizations;
	}

}
