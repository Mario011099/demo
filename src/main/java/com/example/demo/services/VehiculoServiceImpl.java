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
import java.util.Date;
import java.util.List;

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
        if (FuncionesUtiles.esFeriado(fechaConsulta)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConsulta);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return null;
        }
        List<Vehiculo> vehiculosFechasAdecuadas = vehiculoDao.obtenerVehiculosMantenimiento(fechaConsulta);
        vehiculosFechasAdecuadas.stream().filter(x -> {
            Calendar calendarAux = Calendar.getInstance();
            calendarAux.setTime(x.getFechaCompra());
            return x.getFechaCompra().getDay() == fechaConsulta.getDay() || validarManteniemiento(fechaConsulta, x.getFechaCompra());
        });
        return null;
    }

    public boolean validarManteniemiento(Date fechaConsulta, Date fechaCompra) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaConsulta);
        calendar.add(Calendar.DAY_OF_WEEK, -1);
        fechaConsulta = calcularDiaLaborable(calendar);
        return fechaCompra.compareTo(fechaConsulta) > 0;
    }

    public static Date calcularDiaLaborable(Calendar fechaLaborable){
        switch (fechaLaborable.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SATURDAY:
                fechaLaborable.add(Calendar.DATE, -1);
                break;
            case  Calendar.SUNDAY:
                fechaLaborable.add(Calendar.DATE, -2);
                break;
        }
        if (FuncionesUtiles.esFeriado(fechaLaborable.getTime())){
            fechaLaborable.add(Calendar.DATE, -1);
            calcularDiaLaborable(fechaLaborable);
        }
        return fechaLaborable.getTime();
    }

}
