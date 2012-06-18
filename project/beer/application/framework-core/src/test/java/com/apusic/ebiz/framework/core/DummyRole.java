package com.apusic.ebiz.framework.core;

import com.apusic.ebiz.framework.core.model.IdEntity;

/**
 * @hibernate.mapping default-lazy="false"
 * @hibernate.class table="t_role"
 */
public class DummyRole  extends IdEntity{

    private static final long serialVersionUID = 7197134222421547656L;


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


	public boolean equals(Object r) {
		return (id == ((DummyRole) r).getId());
	}

}
