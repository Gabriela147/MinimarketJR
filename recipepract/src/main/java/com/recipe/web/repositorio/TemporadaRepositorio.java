package com.recipe.web.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.web.modelo.Temporada;

@Repository
public interface TemporadaRepositorio extends JpaRepository<Temporada, Integer> {

}
