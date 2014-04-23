/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

import java.util.Arrays;

/**
 *
 * @author Karol
 */
public class Protocol {
 
    
    public enum PacketType {
        ERROR, SUCCES, MESSAGE_TO_ALL, LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAIL,
        PING, PING_REQUEST, PING_REPLY, DISCONNECTED, LOGOUT_REQUEST, LOGOUT, 
        LOGOUT_SUCCES, LOGOUT_FAIL
        ;
        
        @Override
        public String toString()
        {
            switch(this)
            {
                case ERROR :
                    return "ERROR";
                case SUCCES :
                    return "SUCCES";
                case MESSAGE_TO_ALL :
                    return "MESSAGE_TO_ALL";
                case LOGIN_REQUEST :
                    return "LOGIN_REQUEST";
                case LOGIN_SUCCESS :
                    return "LOGIN_SUCCES";
                case LOGIN_FAIL :
                    return "LOGIN_FAIL";
                case PING :
                    return "PING";
                case PING_REQUEST :
                    return "PING_REQUEST";
                case PING_REPLY :
                    return "PING_REPLY";
                case DISCONNECTED :
                    return "DISCONNECTED";
                case LOGOUT_REQUEST :
                    return "LOGOUT_REQUEST";
                case LOGOUT :    
                    return "LOGOUT";
                case LOGOUT_SUCCES :    
                    return "LOGOUT_SUCCES";
                case LOGOUT_FAIL :    
                    return "LOGOUT_FAIL";
                default :
                    return "TODO PacketType toString";
                    
            }
        }
        
    }
 
}
