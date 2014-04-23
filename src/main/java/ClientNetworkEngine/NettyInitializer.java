/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientNetworkEngine;


import Protocol.Packet;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Karol
 */
class NettyInitializer extends ChannelInitializer<SocketChannel>{

    

    BlockingQueue<Packet> queue = new ArrayBlockingQueue<>(10000);
    
    public NettyInitializer(BlockingQueue<Packet> tempList)
    {
        this.queue = tempList;
    }
    
    @Override
    public void initChannel(SocketChannel arg0) throws Exception
    {
        ChannelPipeline pipeline = arg0.pipeline();
        
        pipeline.addLast("decoder", (new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader()))));   
        pipeline.addLast("encoder", new ObjectEncoder());  
        
        pipeline.addLast("handler", new NettyHandler(queue));

        
    }
    
}
