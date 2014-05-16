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
public class PlayerInfo extends Player implements Serializable //extends Player 
{
    //protected  String nick;
        //private final String password;  
    //protected  int id; //!!!!!!!!! TODO unikalny
    
//    public int getId()
//    {
//        return id;
//    }
    public PlayerInfo()
    {
        super();
    }
    
    public PlayerInfo(Player player)
    {
        this.nick = player.getNick();
        this.id = player.getId();
        //super(player.getNick(), player.getId());
    }
    
    public PlayerInfo(String nick, int id)
    {
        //super(nick, id);
        this.nick = nick;
        this.id = id;
//            this.nick = nick;
//            id = idNext++;
        //this.password = password;
    }
    @Override
    public String toString()
    {
        return id + " " + nick;
    }
}
