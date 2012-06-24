package com.apusic.ebiz.model.foundation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.codegen.annotation.FieldView;
import com.apusic.ebiz.model.codegen.annotation.FieldViewType;

@FieldView(label = "组织", subpackage = "foundation")
@Entity
@Table(name = "t_org")
public class Organization extends BaseModel {

    private static final long serialVersionUID = 1L;

    // 编码
    @FieldView(label = "编码", type = FieldViewType.STRING)
	@NotNull
	@Size(min = 2, max = 5)
    @Column(name = "F_ORG_CODE", length = 10, unique = true)
    private String orgCode;
    // 组织简称
    @FieldView(label = "组织简称", type = FieldViewType.STRING)
   	@Size(min = 2, max = 20)
    @Column(name = "F_ORG_NAME", length = 50)
    private String name;

    // 组织全称
    @FieldView(label = "组织全称", type = FieldViewType.STRING)
   	@Size(max = 40)
    @Column(name = "F_ORG_FULL_NAME", length = 80)
    private String fullName;

    // 组织描述
    @FieldView(label = "组织描述", type = FieldViewType.STRING)
   	@Size(max = 40)
    @Column(name = "F_DESCRIPTION", length = 80)
    private String description;

    // 组织类型
   /* @Column(name = "F_ORG_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private OrgType orgType;

    // 组织级别
    @Column(name = "F_ORG_LEVEL", length = 20)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private OrgLevel orgLevel;*/
//    @ManyToOne
//    @JoinColumn(name="parent_ID", nullable=false)
//    private Organization parent ;
    @OneToMany(targetEntity = Organization.class, cascade = { CascadeType.ALL },fetch=FetchType.EAGER)
    private List<Organization> childs = new ArrayList<Organization>();

  /*  // 父组织编码
    @Column(name = "F_PARENT_ORG_CODE", length = 10)
    private String parentOrgCode;

    // 组织地址
    @Column(name = "F_POSTAL_ADDRESS", length = 255)
    private String postalAddress;

    // 电话号码
    @Column(name = "F_TELEPHONE_NUMBER", length = 20)
    private String telephoneNumber;

    // 邮箱
    @Column(name = "F_MAIL", length = 50)
    private String mail;

    // 组织负责人唯一标识,baseUser的uid
    @Column(name = "F_CAPTAIN", length = 11)
    private String captain;
    
    // 组织联系人唯一标识,baseUser的uid
    @Column(name = "F_CONTACT", length = 11)
    private String contact;

    // 所属一级单位编码
    @Column(name = "F_DISTRICT_CODE", length = 10)
    private String districtCode;
*/
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "F_CREATE_TIME")
    private Date createTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "F_LAST_UPDATE_TIME")
    private Date lastUpdateTime = new Date();

   /* @Column(name = "F_DISPLAY_ORDER")
    private int displayOrder;
    
    @Transient
    private String orgCaptainName;
    
    // 是否内置用户，若是，则不可删除
    @Column(name = "F_INTERNAL", updatable = false)
    private boolean internal;*/
    
    @Transient
    private Integer parentId;

    public void addChild(Organization child) {
		childs.add(child);
	}
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

	public List<Organization> getChilds() {
		return childs;
	}

	public void setChilds(List<Organization> childs) {
		this.childs = childs;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

    public List<Organization> getChildren() {
    	return childs;
    }
    

}
