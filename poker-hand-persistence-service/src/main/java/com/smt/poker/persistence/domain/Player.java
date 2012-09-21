package com.smt.poker.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="T_PLAYER")
public class Player {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="PLAYER_GENERATOR")
	@TableGenerator(name="PLAYER_GENERATOR", initialValue=1, allocationSize=1)
	private Integer id;
	
	@Column
	private String name;

	public Player(String name) {
		super();
		this.name = name;
	}
	
	public Player() {
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
