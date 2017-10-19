/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;

/**
 *
 * @author ict-sv-nghiatd
 */

public class ParticalClass implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    
    private String name;
    
    
    private Subject subject;
    
    
    private Time time;
    
    
    private DayOfWeek dayOfWeek;
    
   
    private String room;
    
    
    private int studentQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof ParticalClass)) {
            return false;
        }
        ParticalClass other = (ParticalClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.studentManager.server.entity.ParticalClass[ id=" + id + " ]";
    }
    
}
