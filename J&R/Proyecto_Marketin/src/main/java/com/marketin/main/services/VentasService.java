package com.marketin.main.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketin.main.Request.VentaRequest;
import com.marketin.main.models.Cliente;
import com.marketin.main.models.Empleado;
import com.marketin.main.models.Ventas;
import com.marketin.main.repository.ClienteRepository;
import com.marketin.main.repository.EmpleadoRepository;
import com.marketin.main.repository.VentasRepository;

@Service
public class VentasService 
{
	@Autowired
	VentasRepository repository;
	
	@Autowired
	EmpleadoRepository empleados;
	
	@Autowired
	ClienteRepository clientes;
	
	public List<Ventas> getAll()
	{
		return (List<Ventas>) repository.findAll();
	}
	
	public Ventas getVentasId(Long codCompra) 
	{
		Ventas vent = repository.findById(codCompra)
				.orElseThrow(()->new RuntimeException("No se encontro la venta"));
		return vent;
	}
	
	public List<Ventas> getVentaByEmpleado(String dniempleado)
	{
		Empleado emple = empleados.findById(dniempleado)
				.orElseThrow(()->new RuntimeException("No se encontro al empleado"));
		
		return repository.findByDniempleado(emple);
	}
	
	public List<Ventas> getVentaByCliente(String codcliente)
	{
		Cliente clie = clientes.findById(codcliente)
				.orElseThrow(()->new RuntimeException("No se encontro al cliente"));
		
		return repository.findByDnicliente(clie);
	}
	
	public List<Ventas> getVentaByFecha(String fecha)
	{
		DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechald = LocalDate.parse(fecha, formatear);
		Date fechaformateada = (Date.valueOf(fechald));
		
		return repository.findByFecha(fechaformateada);
	}
	
	public void RegistrarVenta (VentaRequest request) 
	{
		Ventas ventaExistente = repository.findById(request.getVentaId()).orElse(null);

		if (ventaExistente != null) 
		{
		    throw new RuntimeException("Ya existe una venta con el mismo código");
		} 
		else 
		{
			if(request.getTipocomprobante() == "Boleta")
			{
				Empleado emple = empleados.findById(request.getDniempleado())
						.orElseThrow(()->new RuntimeException("No se encontro al empleado"));
				
				Cliente clie = clientes.findById(request.getDnicliente())
						.orElseThrow(()->new RuntimeException("No se encontro al cliente"));
				
				Ventas nuevaVenta = new Ventas();
				
				DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    LocalDate fecha = LocalDate.parse(request.getFecha(), formatear);
				nuevaVenta.setFecha(Date.valueOf(fecha));
			    
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		        LocalTime horaApertura = LocalTime.parse((CharSequence) request.getHora(), formatter);
				nuevaVenta.setHora(horaApertura);
				
				nuevaVenta.setDniempleado(emple);
				nuevaVenta.setMonto(request.getMonto());
				nuevaVenta.setTipocomprobante(request.getTipocomprobante());
				nuevaVenta.setDnicliente(clie);
			    repository.save(nuevaVenta);
			}
			else if (request.getTipocomprobante() == "Factura")
			{
				Empleado emple = empleados.findById(request.getDniempleado())
						.orElseThrow(()->new RuntimeException("No se encontro al empleado"));
				
				Cliente clie = clientes.findById(request.getRuccliente())
						.orElseThrow(()->new RuntimeException("No se encontro al cliente"));
				
				Ventas nuevaVenta = new Ventas();
				
				DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			    LocalDate fecha = LocalDate.parse(request.getFecha(), formatear);
				nuevaVenta.setFecha(Date.valueOf(fecha));
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		        LocalTime horaApertura = LocalTime.parse((CharSequence) request.getHora(), formatter);
				nuevaVenta.setHora(horaApertura);
			    
				nuevaVenta.setDniempleado(emple);
				nuevaVenta.setMonto(request.getMonto());
				nuevaVenta.setTipocomprobante(request.getTipocomprobante());
				nuevaVenta.setRuccliente(clie);
			    repository.save(nuevaVenta);
			}
		}
	}
}
