/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Player;

/**
 *
 * @author Karol
 */
public class MyPlayer extends Player
    {
//        protected final String nick;
//        //private final String password;  
//        protected final int id; //!!!!!!!!! TODO unikalny
//        private static int idNext = 0; 
//        
        
        public Player getPlayerInfo()
        {
            return new Player(nick,id);
        }
    
        public MyPlayer(String nick, int id)
        {
            super(nick, id);
            //this.nick = nick;
//            this.nick = nick;
//            id = idNext++;
            //this.password = password;
        }
        
//        public String getNick()
//        {
//            return nick;
//        }
        
//        public String getPassword()
//        {
//            return password;
//        }
        
//        public int getId()
//        {
//            return id;
//        }
        
//        @Override
//        public String toString()
//        {
//            return nick;
//        }
        
//        @Override 
//        public boolean equals(Object o)
//        {
//            if(o instanceof MyPlayer)
//            {
//                if(this.id == ((MyPlayer)o).id)
//                    return true;
//            }
//            return false;
//        }
    }
