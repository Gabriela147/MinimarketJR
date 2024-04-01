package com.marketin.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControladorVentas
{
    @GetMapping("/venta")
    public String ventas()
    {
        return "Ventas/venta.html";
    }

    @GetMapping("/registrarventa")
    public String registrarventa()
    {
        return "Ventas/registrarventa.html";
    }

    @GetMapping("/apertura")
    public String detalleventa()
    {
        return "Ventas/aperturacaja.html";
    }
}