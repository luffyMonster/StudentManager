/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller.jpaControl;

import com.dn.studentManager.controller.JpaUtils;
import com.dn.studentManager.entity.ParticalClass;
import com.dn.studentManager.controller.jpaControl.exceptions.NonexistentEntityException;
import com.dn.studentManager.entity.Subject;
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
public class ParticalClassJpaController implements Serializable {

    public ParticalClassJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParticalClass particalClass) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(particalClass);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParticalClass particalClass) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            particalClass = em.merge(particalClass);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = particalClass.getId();
                if (findParticalClass(id) == null) {
                    throw new NonexistentEntityException("The particalClass with id " + id + " no longer exists.");
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
            ParticalClass particalClass;
            try {
                particalClass = em.getReference(ParticalClass.class, id);
                particalClass.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The particalClass with id " + id + " no longer exists.", enfe);
            }
            em.remove(particalClass);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParticalClass> findParticalClasses(Subject subject) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM ParticalClass p WHERE p.subject.id = ?1", ParticalClass.class)
                    .setParameter(1, subject.getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<ParticalClass> findParticalClassEntities() {
        return findParticalClassEntities(true, -1, -1);
    }

    public List<ParticalClass> findParticalClassEntities(int maxResults, int firstResult) {
        return findParticalClassEntities(false, maxResults, firstResult);
    }

    private List<ParticalClass> findParticalClassEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParticalClass.class));
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

    public ParticalClass findParticalClass(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParticalClass.class, id);
        } finally {
            em.close();
        }
    }

    public int getParticalClassCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParticalClass> rt = cq.from(ParticalClass.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
