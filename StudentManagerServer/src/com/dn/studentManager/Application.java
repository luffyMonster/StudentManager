package com.dn.studentManager;

import com.dn.studentManager.controller.ServerControl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author duynghia
 */
public class Application {
    
    public static void main(String[] args) {
        ServerControl serverControl = new ServerControl();
        try {
            serverControl.start(8888);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
