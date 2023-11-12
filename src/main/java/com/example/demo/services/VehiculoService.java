package com.example.demo.services;

import com.example.demo.model.Vehiculo;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Local
public interface VehiculoService {
    void guardarVehiculo(Vehiculo vehiculo);

    BigDecimal obtenerPrecioVehiculo(String placa);

    List<Vehiculo> obtenerVehiculosMantenimiento(Date fechaConsulta);
}
