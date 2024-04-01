package com.recipe.web.controlador;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@RestController
@RequestMapping("/assets")
public class AssetsControlador {
	@Value("${storage.location}")
	private String storageLocation;
	
	  
	@GetMapping("/{filename:.+}")
	public Resource obtenerRecurso(@PathVariable("filename") String filename) {
		// filename :  nombreArchivo
		//return  servicio.cargarComoRecurso(filename);
		try {
			Path archivo = Paths.get(storageLocation).resolve(filename);
			Resource recurso = new UrlResource(archivo.toUri());
			 if(recurso.exists() || recurso.isReadable()) {
				 return recurso;
			 }else {
				 throw new com.recipe.web.excepciones.FileNotFoundException("No se pudo encontrar el archivo " + filename);
			 }
			
		}catch (MalformedURLException excepcion) {
			 throw new com.recipe.web.excepciones.FileNotFoundException("No se pudo encontrar el archivo " + filename, excepcion);
		}
	}
	
}
