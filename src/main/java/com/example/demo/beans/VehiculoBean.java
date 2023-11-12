package com.example.demo.beans;


import com.example.demo.model.Vehiculo;
import com.example.demo.services.VehiculoService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ManagedBean(name = "vehiculoBean")
@ViewScoped
@Getter
@Setter
public class VehiculoBean implements Serializable {
    private String placa;
    private String modelo;
    private Integer anio;
    private Date fechaCompra;
    private String observaciones;
    private BigDecimal precio;


    @EJB
    VehiculoService vehiculoService;

    public void guardar() {
        precio = vehiculoService.obtenerPrecioVehiculo(placa);
        Vehiculo vehiculoNuevo = new Vehiculo(placa, modelo, anio, fechaCompra, observaciones, precio);
        vehiculoService.guardarVehiculo(vehiculoNuevo);
    }



}
