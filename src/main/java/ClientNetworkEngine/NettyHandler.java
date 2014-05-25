/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientNetworkEngine;

import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class NettyHandler extends SimpleChannelInboundHandler//<String>
{
    BlockingQueue<Packet> queue;
    
    public NettyHandler(BlockingQueue<Packet> queue)
    {
        this.queue = queue;
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                   Throwable cause) throws NotLogedException
    {
       if(cause instanceof NotLogedException)
       {
           throw (NotLogedException)cause;
       }
       if(cause instanceof IOException)
       {
           log("Server offline");
           
           log("Exception: " + cause.getMessage());
           queue.clear();
           //TODO
       }
       
       else
           log("Unknown excetion");
    }
        
    @Override
    public synchronized void channelRead0(ChannelHandlerContext arg0, Object obj)
             throws Exception 
    {
        Packet packet = (Packet)obj;
        
        if(packet.getPacketType() == PacketType.DISCONNECTED)
        {
            if(packet.getSubPacket() != null)
            {
                queue.clear();
                throw new NotLogedException(packet.getSubPacket().toString());
            }
        }
        queue.put(packet);
        if(packet.getPacketType() != PacketType.SYNC_PLAYER)
            log(packet.toString());
    }    
    
    private void log(String str)
    {
        System.out.println("[Network Engine Handler Log]" + "[" + new Date() + "]" + str);
    }
    
}