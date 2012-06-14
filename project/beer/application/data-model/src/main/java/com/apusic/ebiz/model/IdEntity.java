package com.apusic.ebiz.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 统一定义id的entity基类.TODO:把hibernate默认的message转成中文
 * 
 * @author calvin
 */
@JsonIgnoreProperties( { "hibernateLazyInitializer", "handler" })
@MappedSuperclass
public abstract class IdEntity implements Serializable {
	/**
    *
    */
	private static final long serialVersionUID = 2670138237353648210L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		return (this.getClass().hashCode() * ((null == getId()) ? 0 : getId().intValue())) * prime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IdEntity other = (IdEntity) obj;
		if (getId() == 0) {
			return false;// 特殊处理,只要有一个Id为0，都认为是不相等
		} else if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Transient
	String __errorMessage="";

	public IdEntity validate(Validator validator) {
		__errorMessage = "";
		Set<ConstraintViolation<IdEntity>> failures = validator.validate(this);
		if (!failures.isEmpty()) {
			for (ConstraintViolation<IdEntity> failure : failures) {
				__errorMessage += (failure.getPropertyPath().toString() + "," + failure.getMessage());
			}
		}
		if (StringUtils.isNotBlank(__errorMessage)) {
			throw new RuntimeException(__errorMessage);
		}
		return this;
	}
}
