/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 *
 * @author ict-sv-nghiatd
 */

public class ParticalClass implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    
    private String name;
    
    
    private Subject subject;
    
    
    private LocalDateTime time;
    
   
    private String room;
    
    
    private Integer studentQty;

    public ParticalClass(Long id, String name, Subject subject, LocalDateTime time, String room, Integer studentQty) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.time = time;
        this.room = room;
        this.studentQty = studentQty;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }



    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getStudentQty() {
        return studentQty;
    }

    public void setStudentQty(Integer studentQty) {
        this.studentQty = studentQty;
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
