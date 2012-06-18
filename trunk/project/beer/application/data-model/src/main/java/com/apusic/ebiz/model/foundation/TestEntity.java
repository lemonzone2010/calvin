package com.apusic.ebiz.model.foundation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.apusic.ebiz.framework.core.model.IdEntity;

@Entity
public class TestEntity extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 */
	@NotNull(message = "名字 不能为空")
	private String name;
	/**
	 */
	@NotBlank
	private String value;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate = new Date();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
