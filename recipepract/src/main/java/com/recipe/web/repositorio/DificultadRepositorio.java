package com.recipe.web.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.web.modelo.Dificultad;

@Repository
public interface DificultadRepositorio extends JpaRepository<Dificultad, Integer>{

}
