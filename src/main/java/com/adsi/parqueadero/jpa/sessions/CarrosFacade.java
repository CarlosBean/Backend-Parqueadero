/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.jpa.sessions;

import com.adsi.parqueadero.jpa.entities.Carros;
import com.adsi.parqueadero.jpa.entities.Carros_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author carlos
 */
@Stateless
public class CarrosFacade extends AbstractFacade<Carros> {

    @PersistenceContext(unitName = "com.adsi_parqueadero_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarrosFacade() {
        super(Carros.class);
    }
    
    public Carros findCarrosByPlaca(String placa){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Carros> cq = cb.createQuery(Carros.class);
        Root<Carros> carro = cq.from(Carros.class);
        cq.where(cb.equal(carro.get(Carros_.placa), placa));
        TypedQuery<Carros> q = getEntityManager().createQuery(cq);
        try {
            return (Carros) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
