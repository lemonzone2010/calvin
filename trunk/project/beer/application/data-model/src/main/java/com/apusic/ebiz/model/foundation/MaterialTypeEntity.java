package com.apusic.ebiz.model.foundation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.apusic.ebiz.model.IdEntity;

@Entity
@Table(name = "t_material_type")
public class MaterialTypeEntity extends IdEntity {
	private static final long serialVersionUID = 1843941223621579192L;

	@Column(name = "fparent_id", updatable = false)
	private Long parentId;

	@NotBlank
	@Column(name = "fmaterial_type_name", length = 100)
	private String materialTypeName;

	@Column(name = "fmaterial_type_desc", length = 1024)
	private String materialTeyeDesc;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getMaterialTypeName() {
		return materialTypeName;
	}

	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
	}

	public String getMaterialTeyeDesc() {
		return materialTeyeDesc;
	}

	public void setMaterialTeyeDesc(String materialTeyeDesc) {
		this.materialTeyeDesc = materialTeyeDesc;
	}

}
