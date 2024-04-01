package com.marketin.main.Request;

public class VentaRequest 
{
	Long VentaId;
	String fecha;
	String hora;
	double monto;
	String tipocomprobante;
	String dniempleado;
	String dnicliente;
    String ruccliente;
    
	public Long getVentaId() {
		return VentaId;
	}
	public void setVentaId(Long ventaId) {
		VentaId = ventaId;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getTipocomprobante() {
		return tipocomprobante;
	}
	public void setTipocomprobante(String tipocomprobante) {
		this.tipocomprobante = tipocomprobante;
	}
	public String getDniempleado() {
		return dniempleado;
	}
	public void setDniempleado(String dniempleado) {
		this.dniempleado = dniempleado;
	}
	public String getDnicliente() {
		return dnicliente;
	}
	public void setDnicliente(String dnicliente) {
		this.dnicliente = dnicliente;
	}
	public String getRuccliente() {
		return ruccliente;
	}
	public void setRuccliente(String ruccliente) {
		this.ruccliente = ruccliente;
	}
}
