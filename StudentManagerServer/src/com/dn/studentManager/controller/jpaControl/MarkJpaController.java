/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller.jpaControl;

import com.dn.studentManager.entity.Mark;
import com.dn.studentManager.controller.jpaControl.exceptions.NonexistentEntityException;
import com.dn.studentManager.entity.ParticalClass;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MarkJpaController implements Serializable {

    public MarkJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mark mark) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(mark);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mark mark) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            mark = em.merge(mark);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mark.getId();
                if (findMark(id) == null) {
                    throw new NonexistentEntityException("The mark with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(List<Mark> marks){
        final EntityManager em = getEntityManager();
        
        try {
            em.getTransaction().begin();
//            int batchSize = 500;
            for(Mark m: marks){
                em.merge(m);
            };
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction() != null && em.getTransaction().isActive()){
             em.getTransaction().rollback();
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
            Mark mark;
            try {
                mark = em.getReference(Mark.class, id);
                mark.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mark with id " + id + " no longer exists.", enfe);
            }
            em.remove(mark);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mark> findAllMarkByParticalClass(ParticalClass pc) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Mark m WHERE m.particalClass.id = ?1", Mark.class)
                    .setParameter(1, pc.getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Mark> findMarkEntities() {
        return findMarkEntities(true, -1, -1);
    }

    public List<Mark> findMarkEntities(int maxResults, int firstResult) {
        return findMarkEntities(false, maxResults, firstResult);
    }

    private List<Mark> findMarkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mark.class));
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

    public Mark findMark(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mark.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mark> rt = cq.from(Mark.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
