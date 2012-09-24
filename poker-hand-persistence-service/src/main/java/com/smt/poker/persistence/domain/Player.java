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
	
	@Column(nullable=false, unique=true)
	private String name;

	public Player(String name) {
		super();
		this.name = name;
	}
	
	public Player() {
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
