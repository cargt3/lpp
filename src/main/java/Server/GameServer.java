/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Player.Coordinates;
import Player.MainPlayer;
import Player.PlayerInfo;
import Player.PlayersDatabase;
import Protocol.LoginPacket;
import Protocol.MessagePacket;
import Protocol.Packet;
import Protocol.Protocol;
import Protocol.Protocol.PacketType;
import Protocol.SyncPacket;
import io.netty.channel.Channel;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
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
    private final HashMap<Channel, MainPlayer> loged;
    private final List<ConnectedPlayer> connectedPlayers;
    
    public GameServer(HashMap<Channel, MainPlayer> loged, List<ConnectedPlayer> connectedPlayers)
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
        MainPlayer player = loged.get(channel);
        if(canPlayerBeLogout(player))
        {
            loged.remove(channel);
            channel.writeAndFlush(new Packet(0, Protocol.PacketType.LOGOUT_SUCCES, null));
            //sendToAll(PacketType.PLAYER_LOGOUT, loged.get(channel).getId(), channel);
            for(Channel channelToInform : loged.keySet())
            {
                if(channelToInform != channel)
                {
                    //MainPlayer player = loged.get(channel);
                    channelToInform.writeAndFlush(new Packet(0, PacketType.PLAYER_LOGOUT, player.getId()));
                }
            }
            
        }
        else
        {
            channel.writeAndFlush(new Packet(0, Protocol.PacketType.LOGOUT_FAIL, null));
        }
    }
    
    final int FORCE_LOGOUT_TIME = 2;//sec
    
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
//        private final MainPlayer player;
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
            gameServer.sendToAll(PacketType.PLAYER_LOGOUT, loged.get(channel).getId(), channel);
        }
        
    }
    
    
    private boolean canPlayerBeLogout(MainPlayer player)
    {
        return true; ///!!! TODO
    }
    
    private void syncWithPlayer(Channel channel)
    {
        int seqSync = 0; //TODO
        for (Map.Entry entry : loged.entrySet()) 
        {
           
            channel.writeAndFlush(new Packet(seqSync, PacketType.PLAYER_LOGIN, new PlayerInfo((MainPlayer)entry.getValue())));
            //System.out.println(entry.getKey() + "," + entry.getValue());
        }
        
    }
    
    private void sendToAll(PacketType packetType, Serializable subPacket, Channel exceptionChannal)
    {
        for(Channel channel : loged.keySet())
        {
            if(channel != exceptionChannal)
                channel.writeAndFlush(new Packet(0, packetType, subPacket));
        }
    }
    
    private void informPlayers(PlayerInfo playerInfo, Channel exceptionChannal)
    {
        for(Channel channel : loged.keySet())
        {
            if(channel != exceptionChannal)
                channel.writeAndFlush(new Packet(0, PacketType.PLAYER_LOGIN, playerInfo));
        }
    }
    
    private Coordinates move(MainPlayer mainPlayer, Coordinates point)
    {
        int BOARD_WIDTH = 600;
        int BOARD_HIGHT = 400;
        
        if(mainPlayer.getX() + mainPlayer.getVectorX() + mainPlayer.getR()  > BOARD_WIDTH)
            mainPlayer.setVectorX(mainPlayer.getVectorX() * -1);
        if(mainPlayer.getX() + mainPlayer.getVectorX() - mainPlayer.getR()  < 0)
            mainPlayer.setVectorX(mainPlayer.getVectorX() * -1);
        if(mainPlayer.getY() + mainPlayer.getVectorY() + mainPlayer.getR()  > BOARD_HIGHT)
            mainPlayer.setVectorY(mainPlayer.getVectorY() * -1);
        if(mainPlayer.getY() + mainPlayer.getVectorY() - mainPlayer.getR()  < 0)
            mainPlayer.setVectorY(mainPlayer.getVectorY() * -1);
        mainPlayer.setX(mainPlayer.getX() + mainPlayer.getVectorX());
        mainPlayer.setY(mainPlayer.getY() + mainPlayer.getVectorY());
        
        Coordinates coordinate = new Coordinates(mainPlayer.getX(), mainPlayer.getY());
        
        return coordinate;
    }
    
    public void moveRequest(Channel channel, Packet packet)
    {
        MainPlayer mainPlayer = loged.get(channel);
        Coordinates coordinate = move(mainPlayer, (Coordinates)packet.getSubPacket());
        for (Channel channelToSync : loged.keySet()) 
        {
           
            channelToSync.writeAndFlush(new Packet(0, PacketType.SYNC_PLAYER, 
                                  new SyncPacket(coordinate, mainPlayer.getId(), SyncPacket.PacketId.COORDINATES)));
        }
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

        MainPlayer player = PlayersDatabase.find(loginPacket.getNick(), loginPacket.getPassword());
        if(player != null)
        {
            if(loged.containsValue(player))
            {
                Channel channelToLogout = getKeyByValue(loged,player);
                channelToLogout.writeAndFlush(new Packet(0, Protocol.PacketType.DISCONNECTED, "Another client Loged" ));
                loged.remove(channelToLogout);

            }
            loged.put(channel, player);
            channel.writeAndFlush((new Packet(packet.getSeq(), Protocol.PacketType.LOGIN_SUCCESS, player)));
            channel.writeAndFlush(new Packet(0,PacketType.BEGIN_SYNC,null));
            //channel.writeAndFlush(new Packet(0,PacketType.SYNC_PLAYER, player));
            syncWithPlayer(channel);
            channel.writeAndFlush(new Packet(0,PacketType.END_SYNC,null));
            informPlayers(new PlayerInfo(player), channel);
//            for(Channel logedChannel : loged.keySet())
//            {
//                PlayerInfo playerinfo = new PlayerInfo(player.getNick(), player.getId());
//                logedChannel.writeAndFlush(new Packet(0, PacketType.PLAYER_LOGIN,playerinfo));
//            }
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
