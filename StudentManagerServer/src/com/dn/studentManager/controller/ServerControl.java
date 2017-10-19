/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller;

import com.dn.studentManager.controller.jpaControl.SubjectJpaController;
import com.dn.studentManager.shared.Request;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ict-sv-nghiatd
 */
public class ServerControl {

    private ServerSocket server;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            Socket socket = server.accept();

            new Thread(() -> {
                handleRequestFromClient(socket);
            }).start();

        }
    }

    private void handleRequestFromClient(Socket socket) {
        ObjectOutputStream toClient = null;
        ObjectInputStream fromClient = null;

        try {
            toClient = new ObjectOutputStream(socket.getOutputStream());
            fromClient = new ObjectInputStream(socket.getInputStream());
            Object data;

            //jpa
            EntityManagerFactory emf = JpaUtils.getEntityManagerFactory();
            SubjectJpaController subjectJpaController = new SubjectJpaController(emf);

            handlelabel:
            while ((data = fromClient.readObject()) != null) {
                if (data instanceof Request) {
                    Request req = (Request) data;
                    switch (req.getAction()) {
                        case "find-subject":
                            toClient.writeObject(subjectJpaController.findSubjectByName((String) req.getData()));
                            break;
                        case ".":
                            toClient.writeObject("End");
                            break handlelabel;
                        default:
                            toClient.writeObject("No action");
                            break;
                    };
                } else {
                    throw new IllegalArgumentException("Invalid data");
                }
            }
            System.out.println("Client out");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fromClient != null) {
                    fromClient.close();
                }
                if (toClient != null) {
                    toClient.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void stop() throws IOException {
        if (server != null) {
            server.close();
        }
    }

    public void findSubject(String subjectName, ObjectOutputStream out) {

    }
}
