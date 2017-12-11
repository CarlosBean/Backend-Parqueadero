/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.rest.services;

import com.adsi.parqueadero.jpa.entities.Carros;
import com.adsi.parqueadero.jpa.entities.Horarios;
import com.adsi.parqueadero.jpa.entities.Puestos;
import com.adsi.parqueadero.jpa.sessions.CarrosFacade;
import com.adsi.parqueadero.jpa.sessions.HorariosFacade;
import com.adsi.parqueadero.jpa.sessions.PuestosFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author carlos
 */
@Path("carros")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarrosREST {

    @EJB
    private CarrosFacade carroEJB;

    @EJB
    private PuestosFacade puestoEJB;

    @EJB
    private HorariosFacade horarioEJB;

    @GET
    public List<Carros> findAll() {
        return carroEJB.findAll();
    }

    @GET
    @Path("{id}")
    public Carros findBye(
            @PathParam("name") String name) {
        return carroEJB.find(name);
    }

    @GET
    @Path("find")
    public Carros findByPlaca(
            @QueryParam("Placa") String placa) {
        return carroEJB.findCarrosByPlaca(placa);
    }
    
    /*
       Este metodo permite registrar un carro y asignarle un puesto, hace su respetivas verificaciones con condicionales. 
    */
    @POST
    public Response createCarro(@QueryParam("placa") String placa) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Horarios horario = horarioEJB.find(1);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        try {
            if (calendar.get(Calendar.HOUR_OF_DAY) >= horario.getHoraApertura()
                    && calendar.get(Calendar.HOUR_OF_DAY) <= horario.getHoraCierre()) {
                if (puestoEJB.consultarPuestoDisp() != null) {
                    Puestos puesto = puestoEJB.consultarPuestoDisp();
                    if (carroEJB.findCarrosByPlaca(placa) == null) {
                        Carros carro = new Carros();
                        carro.setPlaca(placa);
                        carro.setHoraLlegada(date);
                        carroEJB.create(carro);

                        puesto.setIdCarros(carro);
                        puestoEJB.edit(puesto);

                        return Response.status(Response.Status.OK)
                                .entity(gson.toJson("Carro ingresado exitosamente."))
                                .build();
                    } else {
                        Carros carro = carroEJB.findCarrosByPlaca(placa);
                        if (puestoEJB.findPuestoByIdCarro(carro.getId()) == null) {
                            carro.setHoraLlegada(date);
                            carro.setHoraSalida(null);
                            carroEJB.edit(carro);

                            puesto.setIdCarros(carro);
                            puestoEJB.edit(puesto);

                            return Response.status(Response.Status.OK)
                                    .entity(gson.toJson("Carro ingresado exitosamente."))
                                    .build();
                        } else {
                            return Response.status(Response.Status.BAD_REQUEST)
                                    .entity(gson.toJson("El carro ya se encuentra estacionado en un puesto."))
                                    .build();
                        }
                    }
                } else {
                    return Response.status(Response.Status.BAD_REQUEST)
                            .entity(gson.toJson("No hay puestos disponibles."))
                            .build();
                }
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(gson.toJson("El parqueadero se encuentra cerrado." + " " + calendar.get(Calendar.HOUR_OF_DAY) + " " + horario.getHoraApertura()))
                        .build();
            }
        } catch (Exception e) {
            System.out.println("Err" + e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Error al registrar el nuevo carro."))
                    .build();
        }
    }
}
