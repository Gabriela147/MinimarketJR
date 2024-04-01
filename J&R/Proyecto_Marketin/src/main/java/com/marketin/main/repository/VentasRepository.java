package com.marketin.main.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.marketin.main.models.Cliente;
import com.marketin.main.models.Empleado;
import com.marketin.main.models.Ventas;

public interface VentasRepository extends CrudRepository <Ventas, Long>
{
	List<Ventas> findByFecha(Date fecha);
	
	List<Ventas> findByTipocomprobante(String tipo);
	
	List<Ventas> findByDniempleado(Empleado empleado);
	
	List<Ventas> findByDnicliente(Cliente dnicliente);
	
	List<Ventas> findByRuccliente(Cliente ruccliente);
}
