/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Player;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Karol
 */
public class Player implements Serializable
    {
        protected  String nick;
        //private final String password;  
        protected  int id; //!!!!!!!!! TODO unikalny
        //private static int idNext = 0; 
        
        public Player()
        {
            
        }
        
        public Player(String nick, int id)
        {
            this.nick = nick;
            this.id = id;
            //this.password = password;
        }
        
        public String getNick()
        {
            return nick;
        }
        
//        public String getPassword()
//        {
//            return password;
//        }
        
        public int getId()
        {
            return id;
        }
        
        @Override
        public String toString()
        {
            return id + " " + nick;
        }
        
        @Override 
        public boolean equals(Object o)
        {
            if(o instanceof MyPlayer)
            {
                if(this.id == ((MyPlayer)o).id)
                    return true;
            }
            return false;
        }
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
