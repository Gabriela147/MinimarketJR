package com.recipe.web.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Categoria {

	@Id
	@Column(name = "id_categoria")
	private Integer id;

	private String titulo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Categoria(Integer id, String titulo) {
		super();
		this.id = id;
		this.titulo = titulo;
	}

	public Categoria() {
		super();
	}

	public Categoria(String titulo) {
		super();
		this.titulo = titulo;
	}

	public Categoria(Integer id) {
		super();
		this.id = id;
	}

}
