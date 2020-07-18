package com.random.role.lol.profile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

@Entity
@Table(name = "special_profile")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "profile_id", referencedColumnName = "id") })
public class SpecialProfile extends Profile {

	public enum ProfileType {
		ALL_CHAMPIONS, FREE_ROTATION
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "profile_type", nullable = false, unique = true)
	private ProfileType profileType;

	public ProfileType getProfileType() {
		return profileType;
	}

	public void setProfileType(ProfileType profileType) {
		this.profileType = profileType;
	}

}
