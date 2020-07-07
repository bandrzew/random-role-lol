package com.random.role.lol.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "champion")
public class Champion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "champion", fetch = FetchType.LAZY)
	private Set<ChampionToRole> roles;

	public Champion() {
	}

	public Champion(String name) {
		this.name = name;
	}

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

	public Set<ChampionToRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<ChampionToRole> championToRoles) {
		this.roles = championToRoles;
	}

}
