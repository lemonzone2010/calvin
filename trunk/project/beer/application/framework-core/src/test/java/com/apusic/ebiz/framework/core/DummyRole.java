package com.apusic.ebiz.framework.core;

/**
 * @hibernate.mapping default-lazy="false"
 * @hibernate.class table="t_role"
 */
public class DummyRole {

    private static final long serialVersionUID = 7197134222421547656L;

    private int id;

    private String roleName;

    /**
     * @hibernate.property column="roleName" type="string" length="50"
     * @return
     */
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean equals(Object r) {
		return (id == ((DummyRole) r).getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return (this.getClass().hashCode() * id) * prime;
	}

}
