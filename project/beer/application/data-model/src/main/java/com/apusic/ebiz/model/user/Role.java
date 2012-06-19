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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apusic.ebiz.model.BaseModel;
@Entity
@Table(name = "T_SMARTORG_ROLE")
public class Role extends BaseModel {

    private static final long serialVersionUID = 7197134222421547656L;
//    @Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "F_ID")
//    private int id;
    @Column(name = "F_NAME")
    private String name;
    @Column(name = "F_ALIAS")
    private String alias;
    @Column(name = "F_DESC")
    private String desc;
    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "roles")
	@Fetch(FetchMode.SELECT)
	@OrderBy("id")
	@BatchSize(size = 20)
    private Set<Resource> resources;


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
