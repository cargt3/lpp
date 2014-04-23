/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Karol
 */
public class PlayersDatabase {
    //private static final ArrayList<PlayerPassword> database = new ArrayList<>();
    
    private static final HashMap<Player, String > database = new HashMap<>();
    
    public PlayersDatabase()
    {
        //database.add(new PlayerPassword("aaa" , "1234"));
        //database.add(new PlayerPassword("bb"  , "1234"));
        Player player1 = new Player("aaa");
        database.put(player1, "1234");
        Player player2 = new Player("bb");
        database.put(player2, "1234");
    }
    
    public static Player find(String nick, String password)
    {
        Iterator it = database.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry)it.next();
            Player player = (Player)pairs.getKey();
            if(player.getNick().equals(nick))
            {
                String pass = (String)pairs.getValue();
                if(pass.equals(password))
                    return player;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }
    
}
