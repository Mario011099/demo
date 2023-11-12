package com.example.demo.controller;

import com.example.demo.services.VehiculoService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path(value = "/vehiculos")
public class VehiculoController {

    @EJB
    VehiculoService vehiculoService;

    @POST
    @Path(value = "/obtenerVehiculosMantenimiento")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerVehiculosMantenimiento(Date fechaConsulta) {
        return Response.ok().status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(vehiculoService.obtenerVehiculosMantenimiento(fechaConsulta)).build();
    }
}
