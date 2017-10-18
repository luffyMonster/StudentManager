/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.server.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author duynghia
 */
public class JpaUtils {
    private static final EntityManagerFactory entityManagerFactory;
    private static final String PERSISTENCE_UNIT_NAME = "com.dn.studentManagerPU";
    
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    
    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }
    
    public static void shutdown(){
        if (entityManagerFactory != null) entityManagerFactory.close();
    }
}
