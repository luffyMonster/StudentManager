package com.dn.studentManager.entity;

import java.io.Serializable;


/**
 *
 * @author ict-sv-nghiatd
 */

public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long id;
    
    
    private String name;
    
   
    private int creditQty;
    
    
    private String discription;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreditQty() {
        return creditQty;
    }

    public void setCreditQty(int creditQty) {
        this.creditQty = creditQty;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
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
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.studentManager.domain.Student[ id=" + id + " ]";
    }
    
}
