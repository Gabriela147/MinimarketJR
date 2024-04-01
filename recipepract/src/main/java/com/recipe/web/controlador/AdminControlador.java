package com.recipe.web.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.recipe.web.excepciones.AlmacenExcepcion;
import com.recipe.web.modelo.Categoria;
import com.recipe.web.modelo.Dificultad;
import com.recipe.web.modelo.Receta;
import com.recipe.web.modelo.Temporada;
import com.recipe.web.repositorio.CategoriaRepositorio;
import com.recipe.web.repositorio.DificultadRepositorio;
import com.recipe.web.repositorio.RecetaRepositorio;
import com.recipe.web.repositorio.TemporadaRepositorio;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
	
	@Value("${storage.location}")
	private String storageLocation;

	@Autowired
	private RecetaRepositorio recetaRepositorio;

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;
	@Autowired
	private DificultadRepositorio dificultadrepositorio;
    @Autowired
    private TemporadaRepositorio temporadarepositorio;
	

	@GetMapping("")
	public ModelAndView verPaginaInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
		Page<Receta> recetas = recetaRepositorio.findAll(pageable);
		return new ModelAndView("admin/index").addObject("recetas", recetas);
	}

	@GetMapping("/recetas/nuevo")
	public ModelAndView mostrarFormularioNuevaReceta() {
		List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("titulo"));
		List<Dificultad> dificultades = dificultadrepositorio.findAll();
		List<Temporada> temporadas = temporadarepositorio.findAll();
	
		return new ModelAndView("admin/nueva-receta")
				.addObject("receta", new Receta())
				.addObject("categorias",categorias)
				.addObject("dificultades", dificultades)
		        .addObject("temporadas", temporadas);

	}
	
   @PostMapping("/recetas/nuevo")
   public ModelAndView registrarReceta(@Validated Receta receta,BindingResult bindingResult ) {
	   if(bindingResult.hasErrors() || receta.getPortada().isEmpty()) {
		   if(receta.getPortada().isEmpty()) {
			   bindingResult.rejectValue("portada", "MultipartNotEmpty");			   
		   }
		   List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("titulo"));
		   List<Dificultad> dificultades = dificultadrepositorio.findAll(); 
		   List<Temporada> temporadas = temporadarepositorio.findAll();
		   return new ModelAndView("admin/nueva-receta")
				   .addObject("receta",receta)
				   .addObject("categorias",categorias)
				   .addObject("dificultades", dificultades)
				   .addObject("temporadas", temporadas);
	   }
	   String rutaPortada = receta.getPortada().getOriginalFilename();
	   
	   if(rutaPortada.isEmpty()) {
       	throw new AlmacenExcepcion("No se puede almacenar un archivo vacío");
       }
       try {
       	InputStream inputStream = receta.getPortada().getInputStream();
       	Files.copy(inputStream,Paths.get(storageLocation).resolve(rutaPortada),StandardCopyOption.REPLACE_EXISTING);
      
       }catch (IOException excepcion ) {
       	throw new AlmacenExcepcion("Error al almacenar el archivo" + rutaPortada,excepcion);
       }
	   	   	   
	   receta.setRutaPortada(rutaPortada);
	     
	   recetaRepositorio.save(receta);
	   return new ModelAndView("redirect:/admin");
	   
   }
   @GetMapping("/recetas/{id}/editar")
   public ModelAndView mostrarFormularioEditarReceta(@PathVariable Integer id) {
	   Receta receta = recetaRepositorio.getOne(id);
	   List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("titulo"));
		List<Dificultad> dificultades = dificultadrepositorio.findAll();
		  List<Temporada> temporadas = temporadarepositorio.findAll();
		return new ModelAndView("admin/editar-receta")
				   .addObject("receta",receta)
				   .addObject("categorias",categorias)
				   .addObject("dificultades", dificultades)
				   .addObject("temporadas", temporadas);
   }
    
   @PostMapping("/recetas/{id}/editar")
   public ModelAndView actualizaReceta(@PathVariable Integer id, @Validated Receta receta,BindingResult bindingResult ) {
	 if(bindingResult.hasErrors()) {
		 List<Categoria> categorias = categoriaRepositorio.findAll(Sort.by("titulo"));
		 List<Dificultad> dificultades = dificultadrepositorio.findAll(); 
		   List<Temporada> temporadas = temporadarepositorio.findAll();
		 return new ModelAndView("admin/editar-receta")
				   .addObject("receta",receta)
				   .addObject("categorias",categorias)
				   .addObject("dificultades", dificultades)
				   .addObject("temporadas", temporadas);
	 }
	 Receta recetaDB = recetaRepositorio.getOne(id);
	 recetaDB.setTitulo(receta.getTitulo());
	 recetaDB.setDescripcion(receta.getDescripcion());
	 recetaDB.setFechasubido(receta.getFechasubido());
	 recetaDB.setRecetaTutorialId(receta.getRecetaTutorialId());
	 recetaDB.setCategorias(receta.getCategorias());
	 recetaDB.setDificultad(receta.getDificultad()); 
	 recetaDB.setTemporada(receta.getTemporada());
	 
	 if(!receta.getPortada().isEmpty()) {
			Path archivo = Paths.get(storageLocation).resolve(recetaDB.getRutaPortada());
			try {
				
				FileSystemUtils.deleteRecursively(archivo);
				
			}catch(Exception excepcion) {
				System.out.println(excepcion);
			}
		 
			//receta.getPortada()
		 
		 String rutaPortada = receta.getPortada().getOriginalFilename();
		 
		 if(receta.getPortada().isEmpty()) {
	        	throw new AlmacenExcepcion("No se puede almacenar un archivo vacío");
	        }
	        try {
	        	InputStream inputStream = receta.getPortada().getInputStream();
	        	Files.copy(inputStream,Paths.get(storageLocation).resolve(rutaPortada),StandardCopyOption.REPLACE_EXISTING);
	       
	        }catch (IOException excepcion ) {
	        	throw new AlmacenExcepcion("Error al almacenar el archivo" + rutaPortada,excepcion);
	        }
			 
		 recetaDB.setRutaPortada(rutaPortada);
		}
	 recetaRepositorio.save(recetaDB);
	 return new ModelAndView("redirect:/admin");	 
   }
   @PostMapping("/recetas/{id}/eliminar")
   public String eliminarReceta(@PathVariable Integer id) {
	   Receta receta = recetaRepositorio.getOne(id);
	   recetaRepositorio.delete(receta);
	   // nombre archivo :  receta.getRutaPortada()
	   Path archivo = Paths.get(storageLocation).resolve(receta.getRutaPortada());
		try {
			
			FileSystemUtils.deleteRecursively(archivo);
			
		}catch(Exception excepcion) {
			System.out.println(excepcion);
		}
	   
	   return "redirect:/admin";
	   
   }
   
   }

