package com.recipe.web.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Temporada {


	@Override
	public String toString() {
		return "Temporada [id=" + id + ", nombre=" + nombre + "]";
	}

	@Id
	@Column(name = "id_temporada")
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

	public Temporada() {
		super();
	}

	public Temporada(Integer id) {
		super();
		this.id = id;
	}

	public Temporada(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
}
