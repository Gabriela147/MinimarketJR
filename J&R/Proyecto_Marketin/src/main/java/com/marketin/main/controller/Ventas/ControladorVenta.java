package com.marketin.main.controller.Ventas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketin.main.Request.VentaRequest;
import com.marketin.main.models.Ventas;
import com.marketin.main.services.VentasService;

@RestController
@RequestMapping("/ventas")
public class ControladorVenta
{
	
	@Autowired
	VentasService servicioventa;
	
	@GetMapping("/all")
    public List<Ventas> getCompras()
    {
    	return servicioventa.getAll();
    }
	
	@GetMapping("/{idventa}")
    public ResponseEntity<?> getComprasId(@PathVariable Long idventa)
    {
		try 
		{
			return ResponseEntity.ok(servicioventa.getVentasId(idventa));
		}
		catch(Exception e)
		{
			return ResponseEntity.badRequest().body("Error en: " + e.getMessage());
		}
    }
	/*
	@PostMapping("/register/detalle")
    public ResponseEntity<?> registrarDetalle(@RequestBody DetalleVentaRequest request)
	{
		try 
		{
			detalleservicio.guardarDetalle(request);
            return ResponseEntity.ok().body("{\"message\": \"Detalle registrado con exito.\"}");
		}
		catch(Exception e) 
		{
            return ResponseEntity.badRequest().body("Error en la creacion de los detalles: " + e.getMessage());
		}
	}
	*/
	@PostMapping("/register")
    public ResponseEntity<?> RegistrarVenta (@RequestBody VentaRequest compraRequest)
	{
        try 
        {   
        	servicioventa.RegistrarVenta(compraRequest);
            return ResponseEntity.ok().body("{\"message\": \"Compra registrado con exito.\"}");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Error en la creacion de la compra: " + e.getMessage());
        }
    }
}
