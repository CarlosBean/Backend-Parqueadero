/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.rest.services;

import com.adsi.parqueadero.jpa.entities.Horarios;
import com.adsi.parqueadero.jpa.entities.Puestos;
import com.adsi.parqueadero.jpa.sessions.HorariosFacade;
import com.adsi.parqueadero.jpa.sessions.PuestosFacade;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET; 
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author carlos
 */
@Path("puestos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PuestosREST {

    @EJB
    private PuestosFacade puestoEJB;
    
    @EJB
    private HorariosFacade horarioEJB;


    @GET
    public List<Puestos> findAll() {
        return puestoEJB.findAll();
    }
    
    @GET
    @Path("horarios")
    public List<Horarios> findHorarios() {
        return horarioEJB.findAll();
    }

    @GET
    @Path("contarPuestos")
    @Produces(MediaType.TEXT_PLAIN)
    public String contarPuestosDisp() {
        return String.valueOf(puestoEJB.contarPuestosDisp());
    }

    @GET
    @Path("puestoDisponible")
    @Produces(MediaType.APPLICATION_JSON)
    public Puestos consultarPuestoDisp() {
        return puestoEJB.consultarPuestoDisp();
    }
    
    @GET
    @Path("mostrarHora")
    @Produces(MediaType.TEXT_PLAIN)
    public String mostrarHora(){
        Calendar calendar = Calendar.getInstance();
        Integer horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        return "Hora del parqueadero: " + horaActual + ":00";
    }
}
