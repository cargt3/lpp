/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Player.MainPlayer;
import Player.PlayersDatabase;
import Protocol.Packet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Karol
 */
public class NettyServerEngine {
    
    BlockingQueue<Pair> packetQueue = new ArrayBlockingQueue<>(10000); ///!!!!!!
    private final HashMap<Channel, MainPlayer> loged = new HashMap<>();
    private final List<ConnectedPlayer> connectedPlayers = new ArrayList<>();
    
    
    
    GameServer gameServer = new GameServer(loged, connectedPlayers);
    
//    public static void main(String args[]) throws Exception
//    {
//        new PlayersDatabase();
//        new NettyServerEngine(8000).run();
//    }
    
    private final int port;
    public NettyServerEngine(int port)
    {
        this.port = port;
    }
    public void run() throws InterruptedException
    {
        new Thread(new QueueThreat(packetQueue, gameServer)).start();
        
        
        EventLoopGroup   boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler( new ChatServerInitializer(packetQueue, loged, 
                                  connectedPlayers, gameServer));
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        }
        finally
        {
              boosGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    
}
