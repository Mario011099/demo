package com.example.demo.util.demo;

import java.util.Date;

public class FuncionesUtiles {

    public static Date[] feriados = {
            new Date(122, 0, 1),   // Año 2022, enero 1
            new Date(122, 4, 1),   // Año 2022, mayo 1
            new Date(122, 11, 25),  // Año 2022, diciembre 25
            new Date(123, 10, 13),  // Año 2022, diciembre 25
            new Date(123, 10, 14),
            new Date(123, 10, 15),
            new Date(123, 10, 16),
            new Date(123, 10, 17),
            new Date(123, 10, 18),
    };

    public static boolean esFeriado(Date fecha) {
        for (Date feriado : feriados) {
            if (feriado.getMonth() == fecha.getMonth() && feriado.getDate() == fecha.getDate()) {
                return true;
            }
        }
        return false;
    }
}