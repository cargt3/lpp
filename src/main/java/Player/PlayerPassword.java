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
public class PlayerPassword {
    protected final String nick;
    private final String password;  



    public PlayerPassword(String nick, String password)
    {
        this.nick = nick;
        this.password = password;
    }

    public String getNick()
    {
        return nick;
    }

    public String getPassword()
    {
        return password;
    }

//        public int getId()
//        {
//            return id;
//        }

    @Override
    public String toString()
    {
        return nick;
    }
    
    
}
