package com.apusic.ebiz.framework.core;

import java.util.Date;
import java.util.Set;

public class DummyUser {

	private Long id;

	private String userName;

	private String emailAddress;

	private String name;

	private String sex;

	private String password;

	private DummyRole role;

	private UserType userType;
	
	private Date createTime;

	private Set<Permission> permissions;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}



	/**
	 * @hibernate.property column="name" type="string" length="50"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property column="sex" type="string" length="10"
	 * @return
	 */
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @hibernate.property column="password" type="string" length="50"
	 *                     not-null="true"
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @hibernate.many-to-one column="roleid" class="com.apusic.md.model.Role"
	 * @return
	 */
	public DummyRole getRole() {
		return role;
	}

	public void setRole(DummyRole role) {
		this.role = role;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean equals(Object u) {
		return (id.longValue() == ((DummyUser) u).getId().longValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return (this.getClass().hashCode() * id.hashCode()) * prime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
