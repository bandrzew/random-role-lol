package com.random.role.lol.champion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "champion")
public class Champion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	private String displayName;

	@Column(name = "\"key\"", nullable = false)
	private int key;

	@OneToMany(mappedBy = "champion", fetch = FetchType.LAZY)
	private Set<ChampionToRole> roles;

	public Champion() {
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Set<ChampionToRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<ChampionToRole> championToRoles) {
		this.roles = championToRoles;
	}

}
