/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

import java.io.Serializable;

/**
 *
 * @author Karol
 */
public class MessagePacket implements Serializable {
    private final String message;
    private final int playerId;
    
    public MessagePacket(String message, int playerId)
    {
        this.message = message;
        this.playerId = playerId;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public int getPlayerId()
    {
        return playerId;
    }
    
}
