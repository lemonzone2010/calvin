package com.apusic.ebiz.model.navigation;

import java.util.Set;

import com.apusic.ebiz.model.BaseModel;
import com.apusic.ebiz.model.codegen.annotation.FieldView;

/**
 * 导航菜单数据
 * @author guguoqing
 *
 */
@FieldView(label = "导航菜单数据", subpackage = "menu")
public class Navigation extends BaseModel implements Comparable<Navigation>{
    private static final long serialVersionUID = -7997030155458753497L;

    @FieldView(label = "状态")
    private String status;
    @FieldView(label = "名称")
    private String name;
    @FieldView(label = "URL")
    private String url;
    
    
    /**
     * 角色，表示该资源可以被多少角色访问，多个角色用逗号隔开
     */
    @FieldView(label = "角色")
    private String roles;
    
    /**
     * 树形结构数据，用来标记该记录是第几级
     */
    private Integer level;
    
    /**
     * 序号，用来制定资源排列顺序
     */
    private Integer sequence;
    
    private Integer applicationId;
    
    private Navigation parent;
    
    private Set<Navigation> childrens;
    
    private Integer parentId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Navigation getParent() {
        return parent;
    }

    public void setParent(Navigation parent) {
        this.parent = parent;
    }

    public Set<Navigation> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<Navigation> childrens) {
        this.childrens = childrens;
    }

    public int compareTo(Navigation o) {
        
        return 0;
    }

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
