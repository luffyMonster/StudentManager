/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.entity;

import java.io.Serializable;


/**
 *
 * @author ict-sv-nghiatd
 */
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    private Student student;
    
    private ParticalClass particalClass;
    
    private float deligenceMark;
    
    private float praceticeMark;
    
    private float testMark;
    
    private float finalExamMark;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ParticalClass getParticalClass() {
        return particalClass;
    }

    public void setParticalClass(ParticalClass particalClass) {
        this.particalClass = particalClass;
    }

    public float getDeligenceMark() {
        return deligenceMark;
    }

    public void setDeligenceMark(float deligenceMark) {
        this.deligenceMark = deligenceMark;
    }

    public float getPraceticeMark() {
        return praceticeMark;
    }

    public void setPraceticeMark(float praceticeMark) {
        this.praceticeMark = praceticeMark;
    }

    public float getTestMark() {
        return testMark;
    }

    public void setTestMark(float testMark) {
        this.testMark = testMark;
    }

    public float getFinalExamMark() {
        return finalExamMark;
    }

    public void setFinalExamMark(float finalExamMark) {
        this.finalExamMark = finalExamMark;
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
        if (!(object instanceof Mark)) {
            return false;
        }
        Mark other = (Mark) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dn.studentManager.server.entity.Point[ id=" + id + " ]";
    }
    
}
