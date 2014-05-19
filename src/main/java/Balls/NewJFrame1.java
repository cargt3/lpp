/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Balls;

import ClientDataBase.PlayersDataBase;
import ClientNetworkEngine.ClientNetworkEngine;
import Player.Coordinates;
import Player.MyPlayer;
import Player.Player;
import Player.PlayerInfo;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Karol
 */
public class NewJFrame1 extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame1
     */
    int GAME_SPEED = 1;
    static final int BOARD_WIDTH = 600;
    static final int BOARD_HIGHT = 400;
    static final int WINDOW_WIDTH = BOARD_WIDTH + 100;
    static final int WINDOW_HIGHT = BOARD_HIGHT + 50;
    
    static ClientNetworkEngine clientNetworkEngine = new ClientNetworkEngine("localhost",8000);
    
    List<Player> balls = new ArrayList<>();
    
    JPanel panel = new game(balls);
    
    public NewJFrame1() {
        super("Rysowanie");
        
 
        add(panel);
        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HIGHT));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        clientNetworkEngine.LogIn("aaa", "1234");
        //setSize(BOARD_WIDTH, BOARD_HIGHT);
        setVisible(true);
        initComponents();
        
        //createBalls(5);
        
        //balls.add(new Ball(random.nextInt(BOARD_WIDTH - Ball.r), 30, Color.RED));
        
        Timer timer = new Timer();
        timer.schedule(new Loop(), 0, GAME_SPEED);
    }
    
    private void createBalls(int n)
    {
        while(n != 0)
        {
            Random random = new Random();
            double x = (double)(random.nextInt(BOARD_WIDTH - 2 * Ball.R) + Ball.R);
            double y = (double)(random.nextInt(BOARD_HIGHT - 2  *Ball.R) + Ball.R);
            Ball ball = new Ball();//, 
            //               (double)(random.nextInt(BOARD_HIGHT - 2*Ball.R ) 
            //) + Ball.R), Color.BLUE);
            ball.setX(x);
            ball.setY(y);
            int r1 = random.nextInt(10);
            int r2 = random.nextInt(10);
            int r3 = 1;
            int r4 = 1;
            if(r1 % 2 == 1)
                r3 = -1;
            if(r2 % 2 == 1)
                r4 = -1;
            
            ball.setVectorX((1.0 + (double)(r1) / 10.0) * r3);
            ball.setVectorY((1.0 + (double)(r2) / 10.0) * r4);
            //balls.add(ball);
            n--;
        }
    }
    PlayersDataBase playersDatabase = new PlayersDataBase();
    MyPlayer myPlayer;
    private class Loop extends java.util.TimerTask 
    {

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
                        //jTextArea1.append("[" + playersDatabase.getPlayer(message.getPlayerId()).getNick()
                        //        + "]" + message.getMessage() + "\r\n");
                        break;
                    case PLAYER_LOGIN :

//                            Serializable o = (PlayerInfo)packet.getSubPacket();
//                            //log(o.toString());
//                            PlayerInfo playerInfo;

                        PlayerInfo playerInfo = (PlayerInfo)packet.getSubPacket();
                        playersDatabase.add(playerInfo);
                        //jTextArea1.append(playerInfo.getNick() + " has been loged" + "\r\n");    
                        break;
                    case SYNC_PLAYER:
                        myPlayer = (MyPlayer)packet.getSubPacket();
                        balls.add(myPlayer);
                        break;
                    case MOVE_REPLY:
                        Coordinates coordinates = (Coordinates)packet.getSubPacket();
                        myPlayer.setX(coordinates.getX());
                        myPlayer.setY(coordinates.getY());
                        
                        break;
                    default:
                        break;                   
                }
            }
            clientNetworkEngine.sendPacket(new Packet(0, PacketType.MOVE_REQUEST, new Coordinates(1,1)));
            panel.validate();
            panel.repaint();
        }
//            for(Ball ball : balls)
//            {
//                move(ball);
//            }
            
        
        
    }
//    void move(Ball ball)
//    {
//        if(ball.getX() + ball.getVectorX() + ball.getR()  > BOARD_WIDTH)
//            ball.setVectorX(ball.getVectorX() * -1);
//        if(ball.getX() + ball.getVectorX() - ball.getR()  < 0)
//            ball.setVectorX(ball.getVectorX() * -1);
//        if(ball.getY() + ball.getVectorY() + ball.getR()  > BOARD_HIGHT)
//            ball.setVectorY(ball.getVectorY() * -1);
//        if(ball.getY() + ball.getVectorY() - ball.getR()  < 0)
//            ball.setVectorY(ball.getVectorY() * -1);
//        ball.setX(ball.getX() + ball.getVectorX());
//        ball.setY(ball.getY() + ball.getVectorY());
//        
//        for(Ball ball1 : balls)
//        {
//            boolean sameHight = false, sameWidth = false;
//            if(ball == ball1)
//                continue;
//            if(ball.getX() == ball1.getX() || 
//             ((ball.getX() - ball.getR() < ball1.getX() + ball.getR()) && (ball.getX() - ball.getR() > ball1.getX() - ball.getR())) || 
//             ((ball.getX() + ball.getR() > ball1.getX() - ball.getR()) && (ball.getX() + ball.getR() < ball1.getX() + ball.getR())))
//            
//                sameWidth = true;
//            if(ball.getY() == ball1.getY() || 
//             ((ball.getY() - ball.getR() < ball1.getY() + ball.getR()) && (ball.getY() - ball.getR() > ball1.getY() - ball.getR())) || 
//             ((ball.getY() + ball.getR() > ball1.getY() - ball.getR()) && (ball.getY() + ball.getR() < ball1.getY() + ball.getR())))
//            
//                sameHight = true;
//            if(sameHight && sameWidth)
//                ball.setColor(Color.GRAY);
//        }
//        
//        
//        
//    }
    public class Ball
    {

        /**
         * @return the r
         */
        public int getR() {
            return r;
        }

        /**
         * @param aR the r to set
         */
        public void setR(int aR) {
            r = aR;
        }
        private double x;
        private double y;
        private double vectorX = 1;
        private double vectorY = 1;
        private int r = 10;
        private Color color;
        public static final int R = 10;
//        public Ball(double x, double y, Color color)
//        {
//            this.x = x;
//            this.y = y;
//            this.color =  color;
//        }

        /**
         * @return the x
         */
        public double getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(double x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public double getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(double y) {
            this.y = y;
        }

        /**
         * @return the vectorX
         */
        public double getVectorX() {
            return vectorX;
        }

        /**
         * @param vectorX the vectorX to set
         */
        public void setVectorX(double vectorX) {
            this.vectorX = vectorX;
        }

        /**
         * @return the vectorY
         */
        public double getVectorY() {
            return vectorY;
        }

        /**
         * @param vectorY the vectorY to set
         */
        public void setVectorY(double vectorY) {
            this.vectorY = vectorY;
        }

        /**
         * @return the color
         */
        public Color getColor() {
            return color;
        }

        /**
         * @param color the color to set
         */
        public void setColor(Color color) {
            this.color = color;
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
