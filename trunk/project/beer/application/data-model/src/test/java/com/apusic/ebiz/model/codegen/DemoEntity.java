package com.apusic.ebiz.model.codegen;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.apusic.ebiz.framework.core.model.IdEntity;
import com.apusic.ebiz.model.codegen.annotation.FieldView;
import com.apusic.ebiz.model.codegen.annotation.FieldViewType;

@FieldView(label = "DEMO示例", subpackage = "demo")
@Entity
@Access(AccessType.FIELD)
public class DemoEntity extends IdEntity { 
	private static final long serialVersionUID = 1843941223621579192L;

	private Long parentId;

	@FieldView(label = "姓名", type = FieldViewType.STRING, sortable = true)
	@NotNull
	private String name;

	@FieldView(label="年龄")
	private Integer age;

	@Size(min = 2, max = 12)
	@Column(name = "f_desc", length = 12)
	@FieldView(needDisplay = true)
	private String desc;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@FieldView(type=FieldViewType.DATE)
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

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
