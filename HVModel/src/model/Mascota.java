package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import model.Person;

@Entity
public class Mascota {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	public String typeClass;
	private String name;
	private float weight;
	private float height;
	private float length;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="persona_id", nullable = false)
	private Person owner;
	
	public int getId() {
		return id;
	}/*
	public void setId(int id) {
		this.id = id;
	}*/
	public String getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(String typeClass) {
		if (typeClass==null)
			throw new RuntimeException("null typeClass entered in setTypeClass");
		this.typeClass = typeClass;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		//if (owner==null)
		//	throw new RuntimeException("null set entered in setOwner");
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}

}
