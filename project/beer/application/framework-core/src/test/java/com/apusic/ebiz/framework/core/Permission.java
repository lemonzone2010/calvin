package com.apusic.ebiz.framework.core;


public class Permission {
	private Long id;
	private String name;
	private DummyUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
