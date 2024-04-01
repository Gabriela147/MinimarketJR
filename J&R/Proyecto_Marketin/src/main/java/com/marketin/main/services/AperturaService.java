package com.marketin.main.services;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketin.main.Request.ReporteRequest;
import com.marketin.main.models.Empleado;
import com.marketin.main.models.ReporteDiario;
import com.marketin.main.repository.EmpleadoRepository;
import com.marketin.main.repository.ReporteDiarioRepository;

@Service
public class AperturaService 
{
	@Autowired
	ReporteDiarioRepository diario;
	
	@Autowired
	EmpleadoRepository empleado;
	
	public void aperturacaja (ReporteRequest reporte) 
	{
		Empleado emple = empleado.findById(reporte.getDni())
				.orElseThrow(()->new RuntimeException("No se encontro al empleado"));
		
		ReporteDiario repo = new ReporteDiario();
		repo.setEstado(reporte.getEstado());
		
		DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha = LocalDate.parse(reporte.getFecha(), formatear);
        repo.setFecha(Date.valueOf(fecha)); 
        
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaApertura = LocalTime.parse((CharSequence) reporte.getHoraapertura(), formatter);
		repo.setHoraapertura(horaApertura);
		
		repo.setMontoapertura(reporte.getMontoapertura());
		repo.setIdempleado(emple);
		
		diario.save(repo);
	}
	
	public ReporteDiario reportedia(Date dia) 
	{
		ReporteDiario repo = diario.findByFecha(dia);
		return repo;
	}
}
