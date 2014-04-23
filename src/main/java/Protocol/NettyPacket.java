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
public class NettyPacket implements Serializable {
    
    private final Packet packet;
    private final int seq;
    
    public NettyPacket(Packet packet, int seq)
    {
        this.packet = packet;
        this.seq = seq;
    }
    
    public Packet getPacket()
    {
        return packet;
    }
    
    public int getSeq()
    {
        return seq;
    }
    
    @Override
    public String toString()
    {
        return seq + packet.toString();
    }
    
}
