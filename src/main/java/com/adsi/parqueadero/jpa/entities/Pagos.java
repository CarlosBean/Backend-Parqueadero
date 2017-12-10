/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adsi.parqueadero.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author carlos
 */
@Entity
@Table(name = "pagos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagos.findAll", query = "SELECT p FROM Pagos p")})
public class Pagos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_actual")
    @Temporal(TemporalType.TIMESTAMP)
    private Date horaActual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_precio")
    private int totalPrecio;
    @JoinColumn(name = "id_carros", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Carros idCarros;
    @JoinColumn(name = "id_tarifas", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tarifas idTarifas;

    public Pagos() {
    }

    public Pagos(Integer id) {
        this.id = id;
    }

    public Pagos(Integer id, Date horaActual, int totalPrecio) {
        this.id = id;
        this.horaActual = horaActual;
        this.totalPrecio = totalPrecio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(Date horaActual) {
        this.horaActual = horaActual;
    }

    public int getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(int totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public Carros getIdCarros() {
        return idCarros;
    }

    public void setIdCarros(Carros idCarros) {
        this.idCarros = idCarros;
    }

    public Tarifas getIdTarifas() {
        return idTarifas;
    }

    public void setIdTarifas(Tarifas idTarifas) {
        this.idTarifas = idTarifas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagos)) {
            return false;
        }
        Pagos other = (Pagos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.adsi.parqueadero.entities.Pagos[ id=" + id + " ]";
    }
    
}
