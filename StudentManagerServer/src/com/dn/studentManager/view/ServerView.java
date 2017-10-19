/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dn.studentManager.view;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author duynghia
 */
public class ServerView {
    private OutputStream out;
    private DateTimeFormatter dtf;

    public ServerView() {
        out = System.out;
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public ServerView(OutputStream out) {
        this.out = out;
    }
    
    public void printMessage(String msg) throws IOException{
        out.write(LocalDateTime.now().format(dtf).getBytes());
        out.write(( "   " + msg).getBytes());
        out.write("\n".getBytes());
    }
    
}
