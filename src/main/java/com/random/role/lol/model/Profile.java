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
@Table(name = "profile")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, name = "removal_restricted")
	private boolean removalRestricted;

	@OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
	private Set<ProfileToChampion> champions;

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

	public boolean isRemovalRestricted() {
		return removalRestricted;
	}

	public void setRemovalRestricted(boolean removalRestricted) {
		this.removalRestricted = removalRestricted;
	}

	public Set<ProfileToChampion> getChampions() {
		return champions;
	}

	public void setChampions(Set<ProfileToChampion> champions) {
		this.champions = champions;
	}

}
