/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientNetworkEngine;

import Player.MainPlayer;
import Protocol.LoginPacket;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol.PacketType;
import java.util.Date;



/**
 *
 * @author Karol
 */
public class ClientNetworkEngine {
//    private final String host;
//    private final int port;
    private final NettyEngine netty;
    public ClientNetworkEngine(String host, int port)
    {
        netty = new NettyEngine(host, port);
        netty.connect(); ///!!!!!!!!!!!!
//        this.host = host;
//        this.port = port;
    }
    
    boolean loged = false;
    private MainPlayer mainPlayer;
    
    public void disconnect()
    {
        netty.disconnect();
    }
    

    
    private void log(String str)
    {
        System.out.println("[Network Engine Log]" + "[" + new Date() + "]" + str);
    }
     
    public Packet nextPacket()
    {
        return netty.getPacket(0);
    }
    
    
    public void SendMessageToAll(String message)
    {
        netty.sendPacket(PacketType.MESSAGE_TO_ALL, new MessagePacket(message, 0));
    }
    
    public void sendPacket(Packet packet)
    {
        netty.sendPacket(packet);
    }
    
    public Packet LogIn(String nick, String password)
    { 
 
        
        int seq = netty.sendPacket(PacketType.LOGIN_REQUEST,new LoginPacket(nick, password));
        
        Packet receivedPacket = netty.getPacket(seq);
        
        if(receivedPacket.getPacketType() == PacketType.LOGIN_SUCCESS)
        {
            loged = true;
            mainPlayer = (MainPlayer)receivedPacket.getSubPacket();
        }
        
        return receivedPacket;
//        if(receivedPacket != null)
//            return receivedPacket.getPacketType();
//        else
//            return PacketType.ERROR;
    }

    /**
     * @return the mainPlayer
     */
    public MainPlayer getMainPlayer() {
        return mainPlayer;
    }
}
