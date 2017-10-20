/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ict-sv-nghiatd
 */
@Entity
@Table(name = "tbl_mark")
public class Mark implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "student_id")
    @ManyToOne()
    private Student student;
    
    @JoinColumn(name = "partical_class_id")
    @ManyToOne()
    private ParticalClass particalClass;
    
    @Column(name = "deligence_mark")
    private Float deligenceMark;
    
    @Column(name = "practice_mark")
    private Float praceticeMark;
    
    @Column(name = "test_mark")
    private Float testMark;
    
    @Column(name = "final_exam_mark")
    private Float finalExamMark;
    
    
    
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

    public Float getDeligenceMark() {
        return deligenceMark;
    }

    public void setDeligenceMark(Float deligenceMark) {
        this.deligenceMark = deligenceMark;
    }

    public Float getPraceticeMark() {
        return praceticeMark;
    }

    public void setPraceticeMark(Float praceticeMark) {
        this.praceticeMark = praceticeMark;
    }

    public Float getTestMark() {
        return testMark;
    }

    public void setTestMark(Float testMark) {
        this.testMark = testMark;
    }

    public Float getFinalExamMark() {
        return finalExamMark;
    }

    public void setFinalExamMark(Float finalExamMark) {
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
