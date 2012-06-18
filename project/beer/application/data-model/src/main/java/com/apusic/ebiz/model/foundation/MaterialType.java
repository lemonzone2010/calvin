package com.apusic.ebiz.model.foundation;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

import com.apusic.ebiz.framework.core.model.IdEntity;

@Entity
public class MaterialType extends IdEntity{
	@NotBlank
	private String name;
	@NotBlank
	@Column(name="f_desc",length=500)
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
