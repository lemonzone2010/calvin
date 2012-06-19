package com.apusic.ebiz.framework.core;

import com.apusic.ebiz.framework.core.model.IdEntity;


public class Permission extends IdEntity{
	private Integer id;
	private String name;
	private DummyUser user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object p) {
		return (this.id.longValue() == ((Permission) p).getId().longValue());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return (this.getClass().hashCode() * id.hashCode()) * prime;
	}

	public DummyUser getUser() {
		return user;
	}

	public void setUser(DummyUser user) {
		this.user = user;
	}


}
