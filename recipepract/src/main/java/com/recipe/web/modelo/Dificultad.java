package com.recipe.web.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Dificultad {

	@Override
	public String toString() {
		return "Dificultad [id=" + id + ", nombre=" + nombre + "]";
	}

	@Id
	@Column(name = "id_dificultad")
	private Integer id;

	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Dificultad() {
		super();
	}

	public Dificultad(Integer id) {
		super();
		this.id = id;
	}

	public Dificultad(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
	
}
