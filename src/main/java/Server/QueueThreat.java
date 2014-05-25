/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import io.netty.channel.Channel;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karol
 */
public class QueueThreat implements Runnable {

    private final BlockingQueue<Pair> packetQueue;
    private final GameServer gameServer;
    
    public QueueThreat(BlockingQueue<Pair> packetQueue, GameServer gameServer)
    {
        this.packetQueue = packetQueue;
        this.gameServer = gameServer;
    }
    
    @Override
    public void run() {
        
        while(true)
        {
            //Packet packet = (Packet)(obj);
            Pair pair;
            try {
                pair = packetQueue.take();
            } 
            catch (InterruptedException ex) 
            {
                log("Error in take from queue: " + ex);
                continue;
            }
            
            Packet packet = pair.getPacket();
            Channel channel = pair.getChannel();
            
            //Channel incoming = ctx.channel();
            if(packet.getPacketType() != PacketType.MOVE_REQUEST)
                log(channel.remoteAddress() + ": " + packet.toString());
            
            if(!gameServer.isLoged(channel) )
            {
                if(!(packet.getPacketType() == PacketType.LOGIN_REQUEST || 
                   packet.getPacketType() == PacketType.PING          ))
                {
                    channel.writeAndFlush(new Packet(0, PacketType.ERROR, "Client not loged"));
                    continue;
                }
            }

            switch(packet.getPacketType())
            {
                case MESSAGE_TO_ALL : 
                    gameServer.SendMessageToAll(channel, packet);
                    break;
                case LOGIN_REQUEST :
                    gameServer.LoginPlayer(channel, packet);
                    break;
                case LOGOUT_REQUEST :
                    gameServer.LogoutPlayer(channel, packet);
                    break;
                case PING :
                    log("Ping");
                    break;
                case MOVE_REQUEST:
                    gameServer.moveRequest(channel, packet);
                    break;    
                default:
                    //TODO
                    log("Unknown packet");
                    break;
            }
        }
    }
    
    private void log(String str)
    {
        System.out.println("[Server][" + new Date() + "]" + str);
    }
    
}
