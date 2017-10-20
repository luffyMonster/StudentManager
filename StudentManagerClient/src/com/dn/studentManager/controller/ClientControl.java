/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.controller;

import com.dn.studentManager.entity.Mark;
import com.dn.studentManager.entity.ParticalClass;
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
    private List<ParticalClass> classes;
    private List<Mark> marks;

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
                view.setVissible(view.getSaveButton(), false);
                showView("Danh sách môn học", "card2");
                view.getTableSubject().clearSelection();
                
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
                if (row != -1 && subjects != null && row < subjects.size()){
                    Request req = new Request("find-partical-class", subjects.get(row));
                    
                    
                    try {
                        classes = (List<ParticalClass>) sendRequest(req);
                        showView("Danh sách lớp học phần: " + subjects.get(row).getName(), "card3");
                        
                        DefaultTableModel model = (DefaultTableModel)view.getTableParticalClass().getModel();
                        model.getDataVector().removeAllElements();
                        
                        if (classes != null && classes.isEmpty()){
                            model.addRow(new Object[]{"Không tìm thấy!", null, null, null, null, null});
                        }
                        
                        for (ParticalClass p:classes){
                            model.addRow(new Object[]{p.getId(), p.getName(), p.getDayOfWeek(), p.getTime(), p.getRoom(), p.getStudentQty()});
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
        
        view.getTableParticalClass().getSelectionModel().addListSelectionListener((e)->{
            if (!e.getValueIsAdjusting()){
                JTable t = view.getTableParticalClass();
                int row = t.getSelectedRow();
                if (row != -1 && classes != null && row < classes.size()){
                    Request req = new Request("find-mark", classes.get(row));
                    
                    try {
                        marks = (List<Mark>)sendRequest(req);
                        view.setVissible(view.getSaveButton(), true);
                        showView("Danh sách sinh viên lớp " + classes.get(row).getName(), "card4");
                        DefaultTableModel model = (DefaultTableModel)view.getTableMark().getModel();
                        model.getDataVector().removeAllElements();
                        
                        if (classes != null && classes.isEmpty()){
                            model.addRow(new Object[]{"Không tìm thấy!", null, null, null, null, null, null});
                        }
                        
                        for (Mark m:marks){
                            model.addRow(new Object[]{m.getId(), m.getStudent().getName(), m.getStudent().getBirthday(),
                                m.getDeligenceMark(), m.getTestMark(), m.getPraceticeMark(), m.getFinalExamMark()
                            });
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        view.addActionListenerForSaveButton((e)->{
            if (view.getTableMark().getCellEditor() != null) view.getTableMark().getCellEditor().stopCellEditing();
            JTable table = view.getTableMark();
            int i = -1;
            for (Mark m:marks){
                m.setDeligenceMark((Float)table.getValueAt(++i, 3));
                m.setTestMark((Float)table.getValueAt(i, 4));
                m.setPraceticeMark((Float)table.getValueAt(i, 5));
                m.setFinalExamMark((Float)table.getValueAt(i, 6));
            }
            
            Request req = new Request("save-mark", marks);
            String res = null;
            try {
                res = (String)sendRequest(req);
            } catch (IOException ex) {
                res = "Error: " + ex.getMessage();
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            view.showMessage(res);
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
