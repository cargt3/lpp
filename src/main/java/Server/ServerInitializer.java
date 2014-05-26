/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Player.MainPlayer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;


/**
 *
 * @author Karol
 */
class ServerInitializer extends ChannelInitializer<SocketChannel> {
    
    int MAX_IDLE_TIME = 5;
    BlockingQueue<Pair> packetQueue; 
    
    private final HashMap<Channel, MainPlayer> loged;// = new HashMap<Channel, MainPlayer>() {};
    private final List<ConnectedPlayer> connectedPlayers;// = new ArrayList<>();
    private final GameServer gameServer;
    
    public ServerInitializer(BlockingQueue<Pair> packetQueue, HashMap<Channel, MainPlayer> loged,
                                List<ConnectedPlayer> connectedPlayers, GameServer gameServer)
    {
        this.packetQueue = packetQueue; 
        this.loged = loged;
        this.connectedPlayers = connectedPlayers;
        this.gameServer = gameServer;
    }
    
    @Override
    protected void initChannel(SocketChannel arg0) throws Exception
    {
        ChannelPipeline pipeline = arg0.pipeline();
        //pipeline.addLast("IdleChecker", new IdleStateHandler(MAX_IDLE_TIME,0,0));
        //pipeline.addLast("IdleDisconnecter", new IdleDisconnecter());
        
        pipeline.addLast("decoder", (new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader()))));  
        pipeline.addLast("encoder", new ObjectEncoder());  
        pipeline.addLast("handler", new ServerHandler(packetQueue, loged, connectedPlayers, gameServer));
    }
    
}
