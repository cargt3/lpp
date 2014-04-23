/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author Karol
 */
public class LoginPacket implements Serializable {
    private final String nick;
    private final String password;  
    
    public String getNick()
    {
        return nick;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public LoginPacket(String nick, String password)
    {
        this.nick = nick;
        this.password = password;
    }
    
    
    
    @Override
    public String toString()
    {
        return nick + " " + password;
    }
    
}
