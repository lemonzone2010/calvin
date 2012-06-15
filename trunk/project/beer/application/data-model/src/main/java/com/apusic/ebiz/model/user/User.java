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
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apusic.ebiz.model.BaseModel;
@Entity
@Table(name = "T_SMARTORG_USER")
public class User extends BaseModel {

    private static final long serialVersionUID = 184659046811923584L;
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "F_ID")
    private int id;
    @Column(name = "F_NAME")
    private String name;
    @Column(name = "F_PASSWORD")
    private String password;
    @Column(name = "F_DISABLED")
    private boolean disbled;
    @Column(name = "F_TYPE")
    private String userType;
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user")
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@BatchSize(size = 20)
    @Transient
    private Set<Role> roles;
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "user")
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@BatchSize(size = 20)
    private Set<Group> groups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisbled(boolean disbled) {
        this.disbled = disbled;
    }

    public boolean isDisbled() {
        return disbled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public void addRole(Role userRole){
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		roles.add(userRole);
	}

	public void removeRole(Role role){
		if (roles == null) {
			roles = new HashSet<Role>();
		}
		roles.remove(role);
	}

	public void addGroup(Group group){
		if(groups==null){
			groups = new HashSet<Group>();
		}
		groups.add(group);
	}

	public void addRoleById(Class<Role> clazz, int id){
		Role userRole = newInstance(clazz);
		userRole.setId(id);
		this.addRole(userRole);
	}

	public void removeRoleById(Class<Role> clazz, int id){
		Role userRole = newInstance(clazz);
		userRole.setId(id);
		this.removeRole(userRole);
	}

	public boolean hasAnyRole(String... roleNames) {
		if (roleNames == null) {
			throw new IllegalArgumentException("Role names cannot be null");
		}

		if (this.roles == null || roles.size() == 0) {
			return false;
		}

		for (String roleName : roleNames){
			for (Role userRole : roles){
				if (roleName!=null && roleName.equals(userRole.getName())){
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasAllRole(String... roleNames){
		if (roleNames == null) {
			throw new IllegalArgumentException("Role names cannot be null");
		}

		if (this.roles == null || roles.size() == 0) {
			return false;
		}

		Set<String> allRoles = new HashSet<String>();
		for (String roleName : roleNames){
			for (Role userRole : roles){
				if (roleName!=null && roleName.equals(userRole.getName())){
					allRoles.add(userRole.getName());
				}
			}
		}
		return roleNames.length == allRoles.size();
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
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
