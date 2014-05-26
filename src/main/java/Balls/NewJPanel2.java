/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Balls;

//import static Balls.NewJFrame1.WINDOW_WIDTH;
import ClientDataBase.PlayersDataBase;
import ClientNetworkEngine.ClientNetworkEngine;
import Player.Coordinates;
import Player.MainPlayer;
import Player.Player;
import Player.PlayerInfo;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import Protocol.SyncPacket;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Karol
 */
public class NewJPanel2 extends javax.swing.JPanel {
    int GAME_SPEED = 10;
    static final int BOARD_WIDTH = 600;
    static final int BOARD_HIGHT = 400;
    static final int WINDOW_WIDTH = BOARD_WIDTH + 250;
    static final int WINDOW_HIGHT = BOARD_HIGHT;
    
    ClientNetworkEngine clientNetworkEngine;
    
    PlayersDataBase playersDatabase = new PlayersDataBase();
    MainPlayer mainPlayer;
    
    //JPanel panel = new game(playersDatabase);
    /**
     * Creates new form NewJPanel2
     */
    public NewJPanel2(ClientNetworkEngine clientNetworkEngine) {
        initComponents();
        this.clientNetworkEngine = clientNetworkEngine;
        //add(panel);
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HIGHT ));
        //pack();

        //setSize(BOARD_WIDTH, BOARD_HIGHT);
        
        
        mainPlayer = clientNetworkEngine.getMainPlayer();
        setVisible(true);
        this.setBackground(Color.white);
        
        Timer timer = new Timer();
        timer.schedule(new Loop(this), 0, GAME_SPEED);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);               
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawRect(0, 0, NewJPanel2.BOARD_WIDTH, NewJPanel2.BOARD_HIGHT);
        for(Object obj : playersDatabase)
        {
            PlayerInfo ball = (PlayerInfo)obj;
            
            //g2d.drawRect((int)(ball.getX() - (ball.getR() / 2)), (int)(ball.getY() - ball.getR() / 2), ball.getR() * 2 , ball.getR() * 2);
            g2d.setColor(ball.getColor());
            g2d.fillOval((int)(ball.getX() - (ball.getR() / 2)), (int)(ball.getY() - ball.getR() / 2), ball.getR() * 2 , ball.getR() * 2);
        }
    }
    
    
    private class Loop extends java.util.TimerTask 
    {
        NewJPanel2 panel;
        public Loop (NewJPanel2 panel)
        {
            this.panel = panel;
        }
//        @Override
//        public void run() {
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
                        //myPlayer = (MainPlayer)packet.getSubPacket();
                        //jTextArea1.append(playerInfo.getNick() + " has been loged" + "\r\n");    
                        break;
                    case SYNC_PLAYER:
                        SyncPacket syncPacket = (SyncPacket)packet.getSubPacket();
                        PlayerInfo playerInfo1 = playersDatabase.getPlayer(syncPacket.getPlayerId());
                        switch(syncPacket.getPacketId())
                        {
                            case COORDINATES :
                                Coordinates coordinates = (Coordinates)syncPacket.getSubPacket();                                
                                playerInfo1.setX(coordinates.getX());
                                playerInfo1.setY(coordinates.getY());
                                break;
                            case HIT_COUNT :
                                int hitCount = (int)syncPacket.getSubPacket();
                                playerInfo1.setHitCount(hitCount);
                                jLabel1.setText(Integer.toString(playerInfo1.getHitCount()));
                                break;
                        }
                        break;
                    case PLAYER_LOGOUT :
                        int id = (int)packet.getSubPacket();
                        playersDatabase.remove(playersDatabase.getPlayer(id));
                        //mainPlayer = (MainPlayer)packet.getSubPacket();
                        //balls.add(mainPlayer);
                        break;

                    default:
                        break;                   
                }
            }
            clientNetworkEngine.sendPacket(new Packet(0, Protocol.PacketType.MOVE_REQUEST, new Coordinates(1,1)));
            panel.validate();
            panel.repaint();
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
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setText("jTextField1");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(1057, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String s = jTextField1.getText();
        Packet packet = new Packet(0, PacketType.MESSAGE_TO_ALL, 
                new MessagePacket(s, mainPlayer.getId()));
        
        clientNetworkEngine.sendPacket(packet);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
