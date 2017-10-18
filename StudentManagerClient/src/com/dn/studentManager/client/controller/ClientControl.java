/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.client.controller;

import com.dn.studentManager.shared.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ict-sv-nghiatd
 */
public class ClientControl {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        
        
        ObjectOutputStream toServer = null;
        ObjectInputStream fromServer = null;
        
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
        Object data;
        toServer.writeObject(new Request<>("xxx", "xx"));
        toServer.writeObject(new Request<>(".", "xxx"));
        
    }
   
}
