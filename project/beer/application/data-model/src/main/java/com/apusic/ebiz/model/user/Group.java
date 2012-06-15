package com.apusic.ebiz.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.apusic.ebiz.model.BaseModel;

@Entity
@Table(name = "T_SMARTORG_GROUP")
public class Group extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "F_ID")
	private int id;
	@Column(name = "F_GROUP_NAME")
	private String name;
	@Column(name = "F_GROUP_ALIAS")
	private String alias;
	@ManyToOne(targetEntity = Group.class,cascade = { CascadeType.MERGE })
	@JoinColumn(name = "F_PARENT_ID")
	private Group parent;

	/**
	 * 子群组
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH }, mappedBy = "parent")
	@Fetch(FetchMode.SELECT)
	@BatchSize(size = 20)
	private Set<Group> groups;
	 @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH },
	 mappedBy = "groups")
	 @Fetch(FetchMode.SELECT)
	 @OrderBy("id")
	 @BatchSize(size = 20)
	private Set<User> users;
	@Column(name = "F_DESC", nullable = true)
	private String desc;

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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
