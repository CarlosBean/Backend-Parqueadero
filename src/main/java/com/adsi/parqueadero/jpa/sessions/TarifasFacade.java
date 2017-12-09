/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.jpa.sessions;

import com.adsi.parqueadero.jpa.entities.Tarifas;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author carlos
 */
@Stateless
public class TarifasFacade extends AbstractFacade<Tarifas> {

    @PersistenceContext(unitName = "com.adsi_parqueadero_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TarifasFacade() {
        super(Tarifas.class);
    }
    
}
