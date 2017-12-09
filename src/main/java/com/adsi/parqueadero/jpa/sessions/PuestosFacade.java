/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.jpa.sessions;

import com.adsi.parqueadero.jpa.entities.Carros;
import com.adsi.parqueadero.jpa.entities.Puestos;
import com.adsi.parqueadero.jpa.entities.Puestos_;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author carlos
 */
@Stateless
public class PuestosFacade extends AbstractFacade<Puestos> {

    @PersistenceContext(unitName = "com.adsi_parqueadero_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PuestosFacade() {
        super(Puestos.class);
    }
    
    public Puestos consultarPuestoDisp(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Puestos> cq = cb.createQuery(Puestos.class);
        Root<Puestos> puesto = cq.from(Puestos.class);
        cq.select(puesto);
        cq.where(cb.isNull(puesto.get(Puestos_.idCarros)));
        cq.orderBy(cb.asc(puesto.get(Puestos_.id)));
        TypedQuery<Puestos> tq = getEntityManager().createQuery(cq);
        try {
            return (Puestos) tq.setMaxResults(1).getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public Long contarPuestosDisp(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Puestos> puesto = cq.from(Puestos.class);
        cq.select(cb.count(puesto));
        cq.where(cb.isNull(puesto.get(Puestos_.idCarros)));
        TypedQuery<Long> q = getEntityManager().createQuery(cq);
        try {
            return q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public Puestos findPuestoByIdCarro(Integer idCarro){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Puestos> cq = cb.createQuery(Puestos.class);
        Root<Puestos> puesto = cq.from(Puestos.class);
        cq.where(cb.equal(puesto.get(Puestos_.idCarros), new Carros(idCarro)));
        TypedQuery<Puestos> q = getEntityManager().createQuery(cq);
        try {
            return (Puestos) q.getSingleResult();
        } catch (NonUniqueResultException ex) {
            throw ex;
        } catch (NoResultException ex) {
            return null;
        }
    }
}
