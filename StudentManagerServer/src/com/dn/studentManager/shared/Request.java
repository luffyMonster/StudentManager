/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.shared;

import java.io.Serializable;

/**
 *
 * @author duynghia
 */
public class Request<T extends Serializable> implements Serializable {

        private static final long serialVersionUID = 1L;

        private String action;

        private T data;

        public Request(String action, T data) {
            this.action = action;
            this.data = data;
        }
        
        

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
        
        
    }
