package com.example.demo.controller;

import com.example.demo.services.VehiculoService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path(value = "/vehiculos")
@ApplicationScoped
public class VehiculoController {

    @EJB
    VehiculoService vehiculoService;

    @GET
    @Path("/obtenerVehiculosMantenimiento/{fechaConsulta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVehiculosMantenimiento(@PathParam("fechaConsulta") String fechaConsultaStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
        Date fechaConsulta = formatter.parse(fechaConsultaStr);
        return Response.ok().status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(vehiculoService.obtenerVehiculosMantenimiento(fechaConsulta)).build();
    }
}
