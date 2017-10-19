/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller;

import com.dn.studentManager.entity.Subject;
import com.dn.studentManager.shared.Request;
import com.dn.studentManager.view.TypeMarkView;
import java.awt.CardLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ict-sv-nghiatd
 */
public class ClientControl {

    private Socket socket;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    private TypeMarkView view;
    private List<Subject> subjects;

    public ClientControl(TypeMarkView view) {
        this.view = view;
        view.setVisible(true);
    }

    public void handle() {
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                try {
                    System.out.println("close");
                    stopConnection();
                } catch (IOException ex) {
                    Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        view.addActionListenerForSearchButton((ActionEvent e) -> {
            Request req = new Request("find-subject", view.getSubjectName().getText().trim());
            try {
                subjects = (List<Subject>) sendRequest(req);
                
                showView("Danh sách môn học", "card2");
                
                DefaultTableModel modelSubject = (DefaultTableModel) view.getTableSubject().getModel();
                modelSubject.getDataVector().removeAllElements();
                
                if (subjects.isEmpty()){
                    modelSubject.addRow(new Object[]{"Không tìm thấy!", null, null, null});
                }

                for (Subject s : subjects) {
                    modelSubject.addRow(new Object[]{s.getId(), s.getName(), s.getCreditQty(), s.getDiscription()});
                }
            } catch (IOException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        view.getTableSubject().getSelectionModel().addListSelectionListener((e)->{
            if (!e.getValueIsAdjusting()){
                JTable t = view.getTableSubject();
                int row = t.getSelectedRow();
                if (subjects != null && row < subjects.size()){
                    
                    //send
                    System.out.println(subjects.get(row).getName());
                    showView("Danh sách lớp học phần", "card3");
                }
            }
            
        });
        
        
    }
    
    public void showView(String label, String viewname){
        view.getLabelResult().setText(label);
        ((CardLayout)view.getCardLayout().getLayout()).show(view.getCardLayout(), viewname);
    }

    public Serializable sendRequest(Request req) throws IOException, ClassNotFoundException {
        toServer.writeObject(req);
        return (Serializable) fromServer.readObject();
    }

    public void startConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        toServer = new ObjectOutputStream(socket.getOutputStream());
        fromServer = new ObjectInputStream(socket.getInputStream());
    }

    public void stopConnection() throws IOException {
        toServer.writeObject(new Request<>(".", null));
        toServer.close();
        fromServer.close();
        socket.close();
    }

}
