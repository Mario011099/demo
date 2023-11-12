package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "placa")
    String placa;
    @Column(name = "modelo")
    String modelo;
    @Column(name = "anio")
    Integer anio;
    @Column(name = "fechaCompra")
    Date fechaCompra;
    @Column(name = "observaciones")
    String observaciones;
    @Column(name = "precio")
    BigDecimal precio;

    public Vehiculo(String placa, String modelo, Integer anio, Date fechaCompra, String observaciones, BigDecimal precio) {
        this.placa = placa;
        this.modelo = modelo;
        this.anio = anio;
        this.fechaCompra = fechaCompra;
        this.observaciones = observaciones;
        this.precio = precio;
    }
}
