/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.entity;


import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * @author ict-sv-nghiatd
 */

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Long id;
    
    
    private String name;
    
   
    private LocalDate birthday;
    
   
    private Integer year;
    
   
    private String adress;

    public Student(Long id, String name, LocalDate birthday, Integer year, String adress) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.year = year;
        this.adress = adress;
    }
    
    

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.studentManager.server.domain.Subject[ id=" + id + " ]";
    }
    
}
