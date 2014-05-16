/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import ClientDataBase.PlayersDataBase;
import ClientNetworkEngine.ClientNetworkEngine;
import Player.PlayerInfo;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import io.netty.util.TimerTask;
import java.io.Serializable;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author Karol
 */
public class NewJPanel extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    ClientNetworkEngine clientNetworkEngine;
    
    public NewJPanel(ClientNetworkEngine clientNetworkEngine) {
        this.clientNetworkEngine = clientNetworkEngine;
        
        
        
        initComponents();
        Timer timer = new Timer();
        timer.schedule(new Loop(), 0, 100);
    }
    
    PlayersDataBase playersDatabase = new PlayersDataBase();
    
    private void log(String str)
    {
        System.out.println("[Main]" + "[" + new Date() + "]" + str);
    }
    
    private class Loop extends java.util.TimerTask 
    {

        @Override
        public void run() {
            Packet packet;
            
            while((packet = clientNetworkEngine.nextPacket()) != null)
            {

                switch(packet.getPacketType())
                {
                    case MESSAGE_TO_ALL : 
                        MessagePacket message = (MessagePacket)packet.getSubPacket();
                        jTextArea1.append("[" + playersDatabase.getPlayer(message.getPlayerId()).getNick()
                                + "]" + message.getMessage() + "\r\n");
                        break;
                    case PLAYER_LOGIN :

//                            Serializable o = (PlayerInfo)packet.getSubPacket();
//                            //log(o.toString());
//                            PlayerInfo playerInfo;

                        PlayerInfo playerInfo = (PlayerInfo)packet.getSubPacket();
                        playersDatabase.add(playerInfo);
                        jTextArea1.append(playerInfo.getNick() + " has been loged" + "\r\n");    
                        break;
                    default:
                        break;                   
                }
            }
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!jTextField1.getText().equals(""))
            clientNetworkEngine.SendMessageToAll(jTextField1.getText());
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
