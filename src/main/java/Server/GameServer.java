/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Player.Player;
import Player.PlayersDatabase;
import Protocol.LoginPacket;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import io.netty.channel.Channel;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karol
 */
public class GameServer {
    private final HashMap<Channel, Player> loged;
    private final List<ConnectedPlayer> connectedPlayers;
    
    public GameServer(HashMap<Channel, Player> loged, List<ConnectedPlayer> connectedPlayers)
    {
        this.loged = loged;
        this.connectedPlayers = connectedPlayers;
    }
    
    public void SendMessageToAll(Channel incoming,  Packet packet)
    {
        for(Channel channel : loged.keySet())//!!!!!!!!!!
        {
             //channel.writeAndFlush(packet);
            channel.writeAndFlush(new Packet(0, PacketType.MESSAGE_TO_ALL, (MessagePacket)packet.getSubPacket()));
                
        }
    }
    private static <T, E> T getKeyByValue(Map<T, E> map, E value)
    {
        for (Map.Entry<T, E> entry : map.entrySet())
        {
            if (value.equals(entry.getValue())) 
            {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public void LogoutPlayer(Channel channel, Packet packet)
    {
        Player player = loged.get(channel);
        if(canPlayerBeLogout(player))
        {
            loged.remove(channel);
            channel.writeAndFlush(new Packet(packet.getSeq(), Protocol.PacketType.LOGOUT_SUCCES, null));
        }
        else
        {
            channel.writeAndFlush(new Packet(packet.getSeq(), Protocol.PacketType.LOGOUT_FAIL, null));
        }
    }
    
    final int FORCE_LOGOUT_TIME = 10;//sec
    
    public void forceLogoutPlayer(Channel channel, Packet packet)
    {
        new Thread(new forceLogoutPlayerThread(channel,this )).start();
    }
    
    public boolean isLoged(Channel channel)
    {
        if(loged.containsKey(channel))
            return true;
        return false;
    }
    
    private class forceLogoutPlayerThread implements Runnable
    {
        private final Channel channel;
//        private final Player player;
        private final GameServer gameServer;
        
        public forceLogoutPlayerThread(Channel channel, GameServer gameServer)
        {
            this.channel = channel;
//            this.player = player;
            this.gameServer = gameServer;
        }
        
        @Override
        public void run() {
            try {
                sleep(FORCE_LOGOUT_TIME * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            gameServer.loged.remove(channel);
        }
        
    }
    
    
    private boolean canPlayerBeLogout(Player player)
    {
        return true; ///!!! TODO
    }
    
    public void LoginPlayer(Channel channel, Packet packet)
    {
        int i = connectedPlayers.indexOf(new ConnectedPlayer(channel, null));
        if(i == -1)
        {
            log("Error");
            return;
        }
        ConnectedPlayer connectedPlayer = connectedPlayers.get(i);
        if(!connectedPlayer.canTryLogin())
        {
            channel.writeAndFlush(new Packet(packet.getSeq(), PacketType.LOGIN_FAIL, 
                    "To many fail attemps. Wait " + ConnectedPlayer.MIN_WAIT_TIME + " sec. and try again."));
            return;
        }           
        
        LoginPacket loginPacket = (LoginPacket)packet.getSubPacket();

        Player player = PlayersDatabase.find(loginPacket.getNick(), loginPacket.getPassword());
        if(player != null)
        {
            if(loged.containsValue(player))
            {
                Channel channelToLogout = getKeyByValue(loged,player);
                channelToLogout.writeAndFlush(new Packet(0, Protocol.PacketType.DISCONNECTED, "Another client Loged" ));
                loged.remove(channelToLogout);

            }
            loged.put(channel, player);
            channel.writeAndFlush((new Packet(packet.getSeq(), Protocol.PacketType.LOGIN_SUCCESS, "")));
            log("User successfully loged: " + player.getNick());

        }        
        else
        {
            connectedPlayer.failedLogin();         
            channel.writeAndFlush(new Packet(packet.getSeq(), Protocol.PacketType.LOGIN_FAIL, "Wrong login or password"));
            log("Wrong login or password: " + loginPacket.getNick());
        }


    }
    
    
    private void log(String str)
    {
        System.out.println("[Game Server log][" + new Date() + "]" + str);
    }
}
