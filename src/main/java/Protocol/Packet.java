/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Protocol;

//import static Protocol.Protocol.seqToString;
import Protocol.Protocol.PacketType;
import java.io.Serializable;

/**
 *
 * @author Karol
 */
public class Packet implements Serializable{
    //private String packet;
    private static final long serialVersionUID = 1111; //////////!!!!!!!!!!!!
    private final int seq;
    private final PacketType packetType;
    //private final String tail;
    private final Serializable subPacket;
    
    public PacketType getPacketType()
    {
        return packetType;
    }
    
    public int getSeq()
    {
        return seq;
    }
    
    public Object getSubPacket()
    {
        return subPacket;
    }

    public Packet(int seq, Protocol.PacketType packetType, Serializable subPacket)////!!!!!!!
    {
        this.seq  = seq;
        this.packetType = packetType;///!!!!!!!!
        //this.tail = tail;
        this.subPacket = subPacket;//!!!!!!!!!!!!!
        
    }
    
    @Override
    public boolean equals(Object o)
    {
        Packet packet = (Packet)o;
        if(packet.seq == this.seq) //////!!!!!!!!!!!!!!!!!
            return true;
        return false;
    }
    
    
    @Override
    public String toString()
    {
        if(subPacket != null)
            return Integer.toString(seq) + packetType + subPacket.toString();
        else
            return Integer.toString(seq) + packetType;
    }
    
    
}
