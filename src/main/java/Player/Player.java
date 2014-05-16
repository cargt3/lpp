/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Player;

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
    }
