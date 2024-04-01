package com.recipe.web.modelo;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Receta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_receta")
	private Integer id;

	@NotBlank
	private String titulo;

	@NotBlank
	private String descripcion;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate fechasubido;

	@NotBlank
	private String recetaTutorialId;

	private String rutaPortada;
	
	@ManyToOne
	@JoinColumn(name="id_dificultad")
	private Dificultad dificultad;
	
	@ManyToOne
	@JoinColumn(name="id_temporada")
	private Temporada temporada;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "categoria_receta", joinColumns = @JoinColumn(name = "id_receta"), inverseJoinColumns = @JoinColumn(name = "id_categoria"))
	private List<Categoria> categorias;

	@Transient
	private MultipartFile portada;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechasubido() {
		return fechasubido;
	}

	public void setFechasubido(LocalDate fechasubido) {
		this.fechasubido = fechasubido;
	}

	public String getRecetaTutorialId() {
		return recetaTutorialId;
	}

	public void setRecetaTutorialId(String recetaTutorialId) {
		this.recetaTutorialId = recetaTutorialId;
	}

	public String getRutaPortada() {
		return rutaPortada;
	}

	public void setRutaPortada(String rutaPortada) {
		this.rutaPortada = rutaPortada;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}
	

	public Temporada getTemporada() {
		return temporada;
	}

	public void setTemporada(Temporada temporada) {
		this.temporada = temporada;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	

	public MultipartFile getPortada() {
		return portada;
	}

	public void setPortada(MultipartFile portada) {
		this.portada = portada;
	}

	public Receta(Integer id, @NotBlank String titulo, @NotBlank String descripcion, @NotNull LocalDate fechasubido,
			@NotBlank String recetaTutorialId, String rutaPortada, Dificultad dificultad, Temporada temporada,
			@NotEmpty List<Categoria> categorias, MultipartFile portada) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechasubido = fechasubido;
		this.recetaTutorialId = recetaTutorialId;
		this.rutaPortada = rutaPortada;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.categorias = categorias;
		this.portada = portada;
	}

	public Receta() {
		super();
	}

		
}
