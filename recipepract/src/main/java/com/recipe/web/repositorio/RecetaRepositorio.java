package com.recipe.web.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.web.modelo.Receta;

public interface RecetaRepositorio extends JpaRepository<Receta, Integer> {

}
