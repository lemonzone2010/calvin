/**
 *
 */
package com.apusic.ebiz.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.codegen.annotation.FieldView;
import com.apusic.ebiz.model.codegen.annotation.FieldViewType;
@FieldView(label = "资源", subpackage = "resource")
@Entity
@Table(name = "T_SMARTORG_RESOURCE")
public class Resource extends BaseModel {

    private static final long serialVersionUID = 1L;
    @FieldView(label = "资源名", type = FieldViewType.STRING, sortable = true)
	@NotNull
	@Size(min = 2, max = 50)
    @Column(name = "F_resName")
    private String resName;
    
    @FieldView(label="资源类型")
    @NotNull
    @Column(name = "F_resType")
    private String resType;
    
    @FieldView(label="资源")
    @NotNull
    @Size(max = 250)
    @Column(name = "F_resValue")
    private String resValue;
    
    @NotNull
    @FieldView(label="所属工程")
    @Column(name = "F_resContext")
    private String resContext;
    
    @FieldView(label = "级别", type = FieldViewType.INT, sortable = true)
    @Column(name = "F_priority")
    private Integer priority;
    
    @FieldView(label="描述")
	@Size( max = 250)
    @Column(name = "F_desc")
    private String desc;
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "resources")
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@BatchSize(size = 20)
    @JsonIgnore
    private Set<Role> roles;

    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getResContext() {
		return resContext;
	}

	public void setResContext(String resContext) {
		this.resContext = resContext;
	}

	public String getResValue() {
		return resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}


    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void addRole(Role role){
    	if(roles==null){
    		roles = new HashSet<Role>();
    	}
    	roles.add(role);
    }

    public void removeRole(Role role){
    	if(roles==null){
    		roles = new HashSet<Role>();
    	}
    	roles.remove(role);
    }

    public String getRoleForString(){
    	if(roles!=null && roles.size()>0){
    		StringBuffer sb = new StringBuffer();
    		for(Role r : roles){
    			sb.append(r.getAlias());
    			sb.append(",");
    		}
    		String result = sb.toString();
			return result.substring(0,result.length()-1);
    	}
    	return null;
    }
}
