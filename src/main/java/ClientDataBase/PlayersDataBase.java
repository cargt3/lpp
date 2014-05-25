/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientDataBase;

import Player.MainPlayer;
import Player.PlayerInfo;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Karol
 */
public class PlayersDataBase implements Iterable //loged players
{
    //HashMap<Integer, String> players = new HashMap<>();
    List<PlayerInfo> players = new ArrayList<>();
    
    public void add(PlayerInfo player)
    {
       
        players.add(player);
    }
    
    public void remove(PlayerInfo player)
    {
        players.remove(player);
    }
    
  
    public PlayerInfo getPlayer(int id)
    {
        for(PlayerInfo playerInfo : players)
        {
            if(playerInfo.getId() == id)
                return playerInfo;
        }
        return null;
    }

    @Override
    public Iterator iterator() {
         return players.iterator();
    }
    
    
    
    
}
