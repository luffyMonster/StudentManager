/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller.jpaControl;

import com.dn.studentManager.entity.Subject;
import com.dn.studentManager.controller.jpaControl.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ict-sv-nghiatd
 */
public class SubjectJpaController implements Serializable {

    public SubjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subject subject) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subject);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subject subject) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            subject = em.merge(subject);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = subject.getId();
                if (findSubject(id) == null) {
                    throw new NonexistentEntityException("The subject with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subject subject;
            try {
                subject = em.getReference(Subject.class, id);
                subject.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subject with id " + id + " no longer exists.", enfe);
            }
            em.remove(subject);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subject> findSubjectByName(String name){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT s FROM Subject s WHERE s.name LIKE ?1", Subject.class)
                .setParameter(1, name)
                .getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Subject> findSubjectEntities() {
        return findSubjectEntities(true, -1, -1);
    }

    public List<Subject> findSubjectEntities(int maxResults, int firstResult) {
        return findSubjectEntities(false, maxResults, firstResult);
    }

    private List<Subject> findSubjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subject.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Subject findSubject(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subject.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subject> rt = cq.from(Subject.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}