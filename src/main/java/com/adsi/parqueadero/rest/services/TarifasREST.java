/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.rest.services;

import com.adsi.parqueadero.jpa.entities.Tarifas;
import com.adsi.parqueadero.jpa.sessions.TarifasFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
@Path("tarifas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TarifasREST {

    @EJB
    private TarifasFacade tarifaEJB;

    @GET
    public List<Tarifas> findAll() {
        return tarifaEJB.findAll();
    }
    
    @GET
    @Path("{id}")
    public Tarifas findById(
            @PathParam("id") Integer id) {
        return tarifaEJB.find(id);
    }

    @PUT
    public Response edit(@QueryParam("valor") int valor) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        try {
            if (valor > 0) {
                Tarifas tarifa = tarifaEJB.find(1);
                tarifa.setValor(valor);
                tarifaEJB.edit(tarifa);
                
                return Response.status(Response.Status.OK)
                        .entity(gson.toJson("La tarifa se ha actualizado correctamente."))
                        .build();
            }else{
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(gson.toJson("El valor de la nueva tarifa debe ser superior a cero."))
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(gson.toJson("Error al actualizar el valor de la tarifa." + e))
                    .build();
        }
    }
}
