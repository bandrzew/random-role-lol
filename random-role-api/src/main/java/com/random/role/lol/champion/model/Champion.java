package com.random.role.lol.champion.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
