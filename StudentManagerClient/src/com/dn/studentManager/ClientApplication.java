/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager;

import com.dn.studentManager.controller.ClientControl;
import com.dn.studentManager.view.TypeMarkView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author duynghia
 */
public class ClientApplication {
    public static void main(String[] args) {
        try {
            TypeMarkView view = new TypeMarkView();
            ClientControl control = new ClientControl(view);
            control.startConnection("localhost", 8088);
            control.handle();
        } catch (IOException ex) {
            Logger.getLogger(ClientApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
