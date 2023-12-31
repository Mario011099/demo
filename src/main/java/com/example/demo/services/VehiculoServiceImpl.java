package com.example.demo.services;

import com.example.demo.dao.VehiculoDao;
import com.example.demo.model.Vehiculo;
import com.example.demo.util.demo.FuncionesUtiles;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class VehiculoServiceImpl implements VehiculoService {

    @EJB
    VehiculoDao vehiculoDao;

    private static String apiUrl = "https://auto.jedai.workers.dev?placa=";

    @Override
    public void guardarVehiculo(Vehiculo vehiculo) {
        vehiculoDao.guardar(vehiculo);
    }

    @Override
    public BigDecimal obtenerPrecioVehiculo(String placa) {
        BigDecimal precio = BigDecimal.ZERO;
        try {

            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(apiUrl + placa);
            Response response = target.request().get();
            if (response.getStatus() == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.readEntity(String.class));
                precio = new BigDecimal(jsonResponse.get("precio").asText());
            } else {
                System.err.println("Error en la solicitud. Código de respuesta: " + response.getStatus());
            }
            response.close();
            return precio;
        } catch (Exception e) {
            System.err.println("Error interno");
        }
        return precio;
    }

    @Override
    public List<Vehiculo> obtenerVehiculosMantenimiento(Date fechaConsulta) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConsulta);
        if (FuncionesUtiles.esFeriado(fechaConsulta) || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return Collections.emptyList();
        }
        List<Vehiculo> vehiculosFechasAdecuadas = vehiculoDao.obtenerVehiculosMantenimiento(fechaConsulta);
        return vehiculosFechasAdecuadas.stream().filter(x -> {
            Calendar calendarAux = Calendar.getInstance();
            calendarAux.setTime(x.getFechaCompra());
            return x.getFechaCompra().getDay() == fechaConsulta.getDay() || validarManteniemiento(fechaConsulta, x.getFechaCompra());
        }).collect(Collectors.toList());

    }

    private Boolean validarManteniemiento(Date fechaConsulta, Date fechaCompra) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConsulta);
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        Date fechaUltimoLaborable = calcularUltimoDiaLaborable(calendar);
        return entre(fechaUltimoLaborable, fechaConsulta, fechaCompra.getDay());
    }

    public static Date calcularUltimoDiaLaborable(Calendar fechaLaborable) {
        switch (fechaLaborable.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SATURDAY:
                fechaLaborable.add(Calendar.DATE, -1);
                break;
            case Calendar.SUNDAY:
                fechaLaborable.add(Calendar.DATE, -2);
                break;
        }
        if (FuncionesUtiles.esFeriado(fechaLaborable.getTime())) {
            fechaLaborable.add(Calendar.DATE, -1);
            calcularUltimoDiaLaborable(fechaLaborable);
        }
        return fechaLaborable.getTime();
    }

    private boolean entre(Date fechaUltimoLaborable, Date fechaConsulta, int diaCompra) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaUltimoLaborable);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        while (calendar.getTime().before(fechaConsulta)) {
            if (calendar.getTime().getDay() == diaCompra) {
                return true;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return false;
    }

}
