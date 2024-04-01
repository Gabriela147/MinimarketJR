package com.recipe.web.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.web.modelo.Receta;
import com.recipe.web.repositorio.RecetaRepositorio;

@Controller
@RequestMapping("")
public class HomeControlador {

	@Autowired
	private RecetaRepositorio recetaRepositorio;

	@GetMapping("")
	public ModelAndView verPaginaInicio() {
		List<Receta> nuevasRecetas = recetaRepositorio
				.findAll(PageRequest.of(0, 3, Sort.by("fechasubido").descending())).toList();
      return new ModelAndView("index")
    		  .addObject("nuevasRecetas", nuevasRecetas);
	}
   @GetMapping("/recetas")
   public ModelAndView listarRecetas(@PageableDefault(sort = "fechasubido", direction = Sort.Direction.DESC) Pageable pageable) {
   Page<Receta> recetas = recetaRepositorio.findAll(pageable);
   return new ModelAndView("recetas")
		       .addObject("recetas",recetas);
    }
   @GetMapping("/recetas/{id}")
   public ModelAndView mostrarDetallesReceta(@PathVariable Integer id) {
	   Receta receta = recetaRepositorio.getOne(id);
	   return new ModelAndView("receta").addObject("receta", receta);
   }
}
