/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientNetworkEngine;

import Protocol.Packet;
import Protocol.Protocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


/**
 *
 * @author Karol
 */
public class NettyEngine {
    private final String host;
    private final int port;
    public NettyEngine(String host, int port)
    {
        this.host = host;
        this.port = port;
    }
    
    BlockingQueue<Packet> queue = new ArrayBlockingQueue<>(10000); //?????!!!!!
    
    private Channel channel;
    EventLoopGroup group = new NioEventLoopGroup(); 
    
    public boolean connect() 
    {      
        try
        {
            Bootstrap bootstrap = new Bootstrap()
                                    .group(group)
                                    .channel(NioSocketChannel.class)
                                    .handler(new NettyInitializer(queue));
            
            channel = bootstrap.connect(host, port).sync().channel();     
            
            
        }
        catch(Exception e)
        {
            log(e.toString());
            return false;
        }
        Timer timer = new Timer();
        //timer.schedule(new Ping(), 0, 1000);
        return true;
    }
    
    class Ping extends TimerTask 
    {
        @Override
        public void run()
        {
           ping();
        }
    }
    
    public void ping()
    {
        channel.writeAndFlush(new Packet(0, Protocol.PacketType.PING, null));
    }
    
    public void disconnect()
    {
        channel.disconnect();
        group.shutdownGracefully();
    }
    
    private void log(String str)
    {
        System.out.println("[Network Engine Log]" + "[" + new Date() + "]" + str);
    }
    
    int MAX_WAIT_TIME = 5; //sec    ///!!!!!!!!!!!!!
    int seq = 1; //!!!!!!!!!!!!!!!!!

    private boolean syncWithServer = true; //?????
    
    public  Packet getPacket(int seq) 
    {
        long start = System.currentTimeMillis();
//        
//        Lock lock = new ReentrantLock();
//        try
//        {
//            lock.lock();
//            //Iterator iterator = queue.iterator();
//            for(Packet packet : queue)
//            //while(iterator.hasNext())////!!!!!!!!!!!!!!!!!
//            {
//                //Packet packet = (Packet)iterator.next();
//                if(packet.getSeq() == seq)
//                {
//                    queue.take();
//                    return packet;
//                }
//            }
//        }
//        catch (InterruptedException ex) {
//            Logger.getLogger(NettyEngine.class.getName()).log(Level.SEVERE, null, ex);
//        }        finally
//        {
//            lock.unlock();
//        }
//        
       int waitTime = MAX_WAIT_TIME;
       if(seq == 0)
           waitTime = 0;
       long temp = System.currentTimeMillis();
       while(start + waitTime * 1000 >= System.currentTimeMillis())
       {
            Packet packet;
            try {
                packet = queue.poll(1, TimeUnit.MILLISECONDS);
                //packet = queue.take();
                
            } catch (InterruptedException ex) {
                return null;
            }
            if(packet != null)
            {
                if( packet.getSeq() == seq)
                {
                    Serializable o = packet.getSubPacket();
                    Packet tempPacket = new Packet(packet.getSeq(), packet.getPacketType(), o);
                    return tempPacket;
                }
                if(packet.getSeq() == 0)
                    syncWithServer = false;
            }
       }
       return null;
    }
    
    public int  sendPacket(Protocol.PacketType packetType, Serializable o)
    {
        Packet packetToSend = new Packet(seq++, packetType, o);
        channel.writeAndFlush(packetToSend);
        return seq - 1;
    }
    
    public void sendPacket(Packet packet)
    {
        channel.writeAndFlush(packet);
    }
}
