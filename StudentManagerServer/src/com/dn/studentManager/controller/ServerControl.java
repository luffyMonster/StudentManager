/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller;

import com.dn.studentManager.controller.jpaControl.MarkJpaController;
import com.dn.studentManager.controller.jpaControl.ParticalClassJpaController;
import com.dn.studentManager.controller.jpaControl.StudentJpaController;
import com.dn.studentManager.controller.jpaControl.SubjectJpaController;
import com.dn.studentManager.entity.Mark;
import com.dn.studentManager.entity.ParticalClass;
import com.dn.studentManager.entity.Student;
import com.dn.studentManager.entity.Subject;
import com.dn.studentManager.shared.Request;
import com.dn.studentManager.view.ServerView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ict-sv-nghiatd
 */
public class ServerControl {

    private ServerSocket server;
    private ServerView view;

    static {
        new JpaUtils();
    }

    public ServerControl(ServerView view) {
        this.view = view;
    }

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        view.printMessage("Server is running on port " + port);
        while (true) {
            Socket socket = server.accept();
            view.printMessage("A client " + socket.getInetAddress().getHostAddress() + " connected ");
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
            SubjectJpaController sjc = new SubjectJpaController(emf);
            ParticalClassJpaController pcjc = new ParticalClassJpaController(emf);
            MarkJpaController mjc = new MarkJpaController(emf);
            StudentJpaController sjcc = new StudentJpaController(emf);

            handlelabel:
            while ((data = fromClient.readObject()) != null) {
                if (data instanceof Request) {
                    Request req = (Request) data;
                    view.printMessage("Request to " + req.getAction());
                    try {
                        switch (req.getAction()) {
                            case "get-all-subject": 
                                toClient.writeObject(sjc.findSubjectEntities());
                                break;
                            case "create-student":
                                toClient.writeObject(createStudent(sjcc, (Student) req.getData()));
                                break;
                            case "create-subject":
                                toClient.writeObject(createSubject(sjc, (Subject) req.getData()));
                                break;
                            case "create-partical-class":
                                toClient.writeObject(createPaticalClass(pcjc, (ParticalClass) req.getData()));
                                break;
                            case "find-subject":
                                toClient.writeObject(sjc.findSubjectByName((String) req.getData()));
                                break;
                            case "find-partical-class":
                                toClient.writeObject(pcjc.findParticalClasses((Subject) req.getData()));
                                break;
                            case "find-mark":
                                toClient.writeObject(mjc.findAllMarkByParticalClass((ParticalClass) req.getData()));
                                break;
                            case "save-mark":
                                toClient.writeObject(saveMark(mjc, (List<Mark>) req.getData()));
                            case ".":
                                toClient.writeObject(".");
                                break handlelabel;
                            default:
                                toClient.writeObject("No action");
                                break;
                        };
                    } catch (ClassCastException e) {
                        Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, e);
                    }

                } else {
                    throw new IllegalArgumentException("Invalid data");
                }
            }

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
                    view.printMessage("Client " + socket.getInetAddress().getHostName() + " disconected");
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

    public String createStudent(StudentJpaController sjcc, Student s) {
        try {
            sjcc.create(s);
            return "Thành công!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String createSubject(SubjectJpaController sjcc, Subject s) {
        try {
            sjcc.create(s);
            return "Thành công!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String createPaticalClass(ParticalClassJpaController sjcc, ParticalClass s) {
        try {
            sjcc.create(s);
            return "Thành công!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String saveMark(MarkJpaController mjc, List<Mark> list) {
        try {
            mjc.edit(list);
            return "Success";
        } catch (Exception e) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, e);
            return e.getMessage();
        }
    }
}
