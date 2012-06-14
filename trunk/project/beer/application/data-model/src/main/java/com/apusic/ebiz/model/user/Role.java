package com.apusic.ebiz.model.user;

import java.util.HashSet;
import java.util.Set;

import com.apusic.ebiz.model.BaseModel;

public class Role extends BaseModel {

    private static final long serialVersionUID = 7197134222421547656L;

    private int id;

    private String name;

    private String alias;

    private String desc;

    private Set<Resource> resources;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
        return name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

	public void addResource(Resource resource){
		if (resources == null){
		    resources = new HashSet<Resource>();
		}
		resources.add(resource);
	}

	public void removeResource(Resource resource){
		if (resource == null){
		    resources = new HashSet<Resource>();
		}
		resources.remove(resource);
	}

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }
}
