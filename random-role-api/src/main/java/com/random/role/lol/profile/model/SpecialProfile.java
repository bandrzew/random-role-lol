package com.random.role.lol.profile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.PrimaryKeyJoinColumns;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "special_profile")
@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "profile_id", referencedColumnName = "id") })
public class SpecialProfile extends Profile {

	@Enumerated(EnumType.STRING)
	@Column(name = "profile_type", nullable = false, unique = true)
	private ProfileType profileType;

}
