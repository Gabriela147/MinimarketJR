package com.marketin.main.controller.Ventas;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketin.main.models.Cliente;
import com.marketin.main.services.ClientesService;

@RestController
@RequestMapping("/registroventa")
public class ControladorRegistroVenta 
{
	@Autowired
	ClientesService clientes;
	
	@GetMapping("/cliente/{dniruc}")
	public ResponseEntity<?> getClientes(@PathVariable String dniruc)
	{
		try 
		{
			return ResponseEntity.ok(clientes.clienteById(dniruc));
		}
		catch(Exception e) 
		{
			return ResponseEntity.badRequest().body("No se encontro el cliente");
		}
	}
	
	@PostMapping("/registro")
	public ResponseEntity<?> guardarClientes(@RequestBody Cliente cliente)
	{
		try 
		{
			clientes.guardar(cliente);
			return ResponseEntity.ok(Map.of("mensaje", "Cliente creado exitosamente"));
		}
		catch (Exception e) 
		{
			return ResponseEntity.badRequest().body("Hubo un error al guardar al cliente");
		}
	}
}
