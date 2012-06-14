/**
 *
 */
package com.apusic.ebiz.model.user;

import java.util.HashSet;
import java.util.Set;

import com.apusic.ebiz.model.BaseModel;

/**
 * @author xuzhengping
 * @2011-7-15 create
 *
 */
public class Resource extends BaseModel {

    private static final long serialVersionUID = 1L;

    private int id;

    private String resName;

    private String resType;

    private String resValue;

    private String resContext;

    private Integer priority;

    private String desc;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
