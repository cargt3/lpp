/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Protocol.Packet;
import io.netty.channel.Channel;

/**
 *
 * @author Karol
 */
public class Pair {
    
    private final Packet packet;
    private final Channel channel;
    
    public Pair(Packet packet, Channel channel)
    {
        this.packet = packet;
        this.channel = channel;
    }
    
    public Packet getPacket()
    {
        return packet;
    }
    
    public Channel getChannel()
    {
        return channel;
    }
    
    
    
}
