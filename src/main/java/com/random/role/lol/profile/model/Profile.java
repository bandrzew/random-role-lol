package com.random.role.lol.profile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "profile")
@Inheritance(strategy = InheritanceType.JOINED)
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

	public Profile() {
	}

	public Profile(String name) {
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
