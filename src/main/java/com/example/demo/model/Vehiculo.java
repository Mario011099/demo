package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vehiculo")
@NoArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa")
    private String placa;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "fechaCompra")
    private Date fechaCompra;
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "precio")
    private BigDecimal precio;

    public Vehiculo(String placa, String modelo, Integer anio, Date fechaCompra, String observaciones, BigDecimal precio) {
        this.placa = placa;
        this.modelo = modelo;
        this.anio = anio;
        this.fechaCompra = fechaCompra;
        this.observaciones = observaciones;
        this.precio = precio;
    }
}
