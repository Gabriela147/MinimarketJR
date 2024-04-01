package com.marketin.main.models;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Detalle_Venta")
public class DetalleVenta 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "idventa")
    private Ventas idventa;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Productos idproducto;

    @Column
    Date FechaEntrega;
    int Cantidad;
    Double SubTotal;
    Double IGV;
    Double Total;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ventas getIdventa() {
		return idventa;
	}
	public void setIdventa(Ventas idventa) {
		this.idventa = idventa;
	}
	public Productos getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Productos idproducto) {
		this.idproducto = idproducto;
	}
	public Date getFechaEntrega() {
		return FechaEntrega;
	}
	public void setFechaEntrega(Date fechaEntrega) {
		FechaEntrega = fechaEntrega;
	}
	public int getCantidad() {
		return Cantidad;
	}
	public void setCantidad(int cantidad) {
		Cantidad = cantidad;
	}
	public Double getSubTotal() {
		return SubTotal;
	}
	public void setSubTotal(Double subTotal) {
		SubTotal = subTotal;
	}
	public Double getIGV() {
		return IGV;
	}
	public void setIGV(Double iGV) {
		IGV = iGV;
	}
	public Double getTotal() {
		return Total;
	}
	public void setTotal(Double total) {
		Total = total;
	}
}