/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Player.MyPlayer;
import io.netty.channel.Channel;
import java.util.Date;

/**
 *
 * @author Karol
 */
public class ConnectedPlayer
{
    private static int last_id = 0;
    private int id;
    private final Channel channel;
    private final MyPlayer player;
    private long lastLoginRequest;//!!!!!!!!!!!!!
    private int loginNumberAttemps = 0;
    
    public static final int MIN_WAIT_TIME = 10; //sec
    
    public ConnectedPlayer(Channel channel, MyPlayer player)
    {
        this.channel = channel;
        this.player = player;
        this.id = last_id++;
    }
    public boolean canTryLogin()
    {
        System.out.println(loginNumberAttemps);
        if(loginNumberAttemps < 3)
            return true;
        if(System.currentTimeMillis( ) >  lastLoginRequest + MIN_WAIT_TIME * 1000)
            return true;
        return false;
            
    }
    
    public void failedLogin()
    {
        loginNumberAttemps++;
        lastLoginRequest = System.currentTimeMillis();
    }
    
    @Override
    public boolean equals(Object o)
    {
        ConnectedPlayer connectedPlayer = (ConnectedPlayer)o;
        if(connectedPlayer.channel == this.channel)
            return true;

        return false;
    }
}
