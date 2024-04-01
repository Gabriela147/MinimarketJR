package com.recipe.web.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipe.web.modelo.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer>{

}
