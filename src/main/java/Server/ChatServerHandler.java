 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import Player.MainPlayer;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;



public class ChatServerHandler extends SimpleChannelInboundHandler//<String>
{

    private final HashMap<Channel, MainPlayer> loged;// = new HashMap<Channel, MainPlayer>() {};
    private final List<ConnectedPlayer> connectedPlayers;// = new ArrayList<>();
    private final GameServer gameServer;
    
    //GameServer gameServer = new GameServer(loged, connectedPlayers);
    BlockingQueue<Pair> packetQueue; 
    
    public ChatServerHandler(BlockingQueue<Pair> packetQueue, HashMap<Channel, MainPlayer> loged,
                            List<ConnectedPlayer> connectedPlayers, GameServer gameServer)
    {
        this.packetQueue = packetQueue;
        this.loged = loged;
        this.connectedPlayers = connectedPlayers;
        this.gameServer = gameServer;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                   Throwable cause) 
    {
       if(cause instanceof IOException)
       {
           synchronized(loged)
           {
                log("Error " + loged.get(ctx.channel()).toString() + " has been logout");               
           }
           log("Exception: " + cause.getMessage());
       }
       else
           log("Unknown excetion");
    }
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx)
    { 
       
        Channel incoming = ctx.channel();
        synchronized(connectedPlayers)
        { 
            connectedPlayers.add(new ConnectedPlayer(incoming, null));
        }
        log("Connected" + incoming.remoteAddress());
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws InterruptedException
    {
        Channel incoming = ctx.channel();
        synchronized(connectedPlayers)
        { 
            connectedPlayers.remove(new ConnectedPlayer(incoming, null));
        }
        if(gameServer.isLoged(incoming))
            //gameServer.forceLogoutPlayer(incoming, null);
            //gameServer.LogoutPlayer(incoming, null);
            packetQueue.put(new Pair(new Packet(-1, PacketType.LOGOUT_REQUEST, ""), ctx.channel()));
        log("Disconnected" + incoming.remoteAddress());
    }
   
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj)
             throws Exception 
    {

        
        Packet packet = (Packet)(obj);
        Channel channel = ctx.channel();
        
        packetQueue.put(new Pair(packet, channel));       
    }    
    
   
    private void log(String str)
    {
        System.out.println("[Handler log][" + new Date() + "]" + str);
    }
    
}
