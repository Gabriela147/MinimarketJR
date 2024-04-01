package com.marketin.main.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketin.main.DTO.ComprasDTO;
import com.marketin.main.Request.CompraRequest;
import com.marketin.main.models.Compras;
import com.marketin.main.models.Proveedores;
import com.marketin.main.repository.ComprasRepository;
import com.marketin.main.repository.ProveedoresRepository;

@Service
public class ComprasService 
{
	@Autowired
	ComprasRepository repository;
	
	@Autowired
	ProveedoresRepository proveedorre;
	
	public List<Compras> getAll() 
	{
	    return (List<Compras>) repository.findAll();
	}
	
	public List<ComprasDTO> getAllDTO() {
	    List<Compras> compras = getAll();
	    return convertirAComprasDTO(compras);
	}

	private List<ComprasDTO> convertirAComprasDTO(List<Compras> compras) {
	    List<ComprasDTO> comprasDTOList = new ArrayList<>();
	    
	    for (Compras compra : compras) 
	    {
	        String razonSocial = compra.getRucproveedor() != null ? compra.getRucproveedor().getRazonSocial() : null;
	        
	        ComprasDTO dto = new ComprasDTO(compra.getCodCompra(), compra.getTipoComprobante(),
	                compra.getFechaCompra(), compra.getMonto(), compra.getDeuda(),
	                compra.getEstado(), razonSocial);

	        comprasDTOList.add(dto);
	    }
	    
	    return comprasDTOList;
	}
	
	public Compras getComprasId(String codCompra) 
	{
		Compras comp = repository.findById(codCompra)
				.orElseThrow(()->new RuntimeException("No se encontro la compra"));
		return comp;
	}
	
	public List<Compras> getComprasByProveedor(String ruc)
	{
		Proveedores prove = proveedorre.findById(ruc)
				.orElseThrow(()->new RuntimeException("No se encontro al proveedor"));
		
		return repository.findByRucproveedor(prove);
	}
	
	public List<Compras> getCompraByFecha(String fecha)
	{
		DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate fechald = LocalDate.parse(fecha, formatear);
		Date fechaformateada = (Date.valueOf(fechald));
		
		return repository.findByFechaCompra(fechaformateada);
	}
	
	public void RegistrarCompra (CompraRequest compraRequest) 
	{
		String codCompra = compraRequest.getSerie() + compraRequest.getComprobante();
		Compras compraExistente = repository.findById(codCompra).orElse(null);

		if (compraExistente != null) 
		{
		    throw new RuntimeException("Ya existe una compra con el mismo código");
		} 
		else 
		{
		   Proveedores proveedor= proveedorre.findById(compraRequest.getProveedor())
		                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

		    Compras nuevaCompra = new Compras();

		    nuevaCompra.setCodCompra(codCompra);
		    nuevaCompra.setTipoComprobante(compraRequest.getTipoComprobante());
		    
		    DateTimeFormatter formatear = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDate fecha = LocalDate.parse(compraRequest.getFechaCompra(), formatear);
		    nuevaCompra.setFechaCompra(Date.valueOf(fecha));
		    
		    nuevaCompra.setMonto(compraRequest.getMonto());
		    nuevaCompra.setDeuda(compraRequest.getDeuda());
		    nuevaCompra.setEstado(compraRequest.getEstado());
		    nuevaCompra.setRucproveedor(proveedor);

		    repository.save(nuevaCompra);
		}
	}
	
	public void actualizarCompra(String codCompra, CompraRequest compraRequest) 
	{
		Compras compraExistente = repository.findById(codCompra).orElseThrow(() -> new RuntimeException("Compra no encontrado"));
        
        double resultado = compraExistente.getDeuda() - compraRequest.getDeuda();
    	BigDecimal resultadoRedondeado = new BigDecimal(resultado).setScale(2, RoundingMode.HALF_UP);
    	compraExistente.setDeuda(resultadoRedondeado.doubleValue());
    	
        repository.save(compraExistente);
	}
	
	public void pagarCompra(String codCompra, CompraRequest compraRequest) 
	{
		Compras compraExistente = repository.findById(codCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrado"));
        compraExistente.setDeuda(compraExistente.getDeuda()-compraRequest.getDeuda());
        compraExistente.setEstado("Pagado");
        	            
        repository.save(compraExistente);
	}
}
