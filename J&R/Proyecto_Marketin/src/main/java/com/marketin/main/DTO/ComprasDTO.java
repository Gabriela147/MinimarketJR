package com.marketin.main.DTO;

import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class ComprasDTO 
{
    private String codCompra;
    private String tipoComprobante;
    private Date fechaCompra;
    private double monto;
    private double deuda;
    private String estado;
    private String razonSocial;

    public ComprasDTO() {
    }
    
    public ComprasDTO(String codCompra, String tipoComprobante, Date fechaCompra, double monto, double deuda, String estado, String rucProveedor) {
        this.codCompra = codCompra;
        this.tipoComprobante = tipoComprobante;
        this.fechaCompra = fechaCompra;
        this.monto = monto;
        this.deuda = deuda;
        this.estado = estado;
        this.razonSocial = rucProveedor;
    }

	public String getCodCompra() {
		return codCompra;
	}

	public void setCodCompra(String codCompra) {
		this.codCompra = codCompra;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getDeuda() {
		return deuda;
	}

	public void setDeuda(double deuda) {
		this.deuda = deuda;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
}
