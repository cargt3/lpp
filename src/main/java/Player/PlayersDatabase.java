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
import java.util.Random;

/**
 *
 * @author Karol
 */
public class PlayersDatabase {
    //private static final ArrayList<PlayerPassword> database = new ArrayList<>();
    
    private static final HashMap<MainPlayer, String > database = new HashMap<>();
    
    public PlayersDatabase()
    {
        //database.add(new PlayerPassword("aaa" , "1234"));
        //database.add(new PlayerPassword("bb"  , "1234"));
        int id = 0;
//        MainPlayer player1 = new MainPlayer("aaa", id++);
//        player1.setX(50);
//        player1.setY(50);
//        database.put(player1, "1234");
//        MainPlayer player2 = new MainPlayer("bb", id++);
//        player2.setX(100);
//        player2.setY(100);
//        database.put(player2, "1234");
//        MainPlayer player3 = new MainPlayer("a", id++);
//        player2.setX(100);
//        player2.setY(100);
//        database.put(player2, "1234");
        database.put(createPlayer("aaa"), "1234");
        database.put(createPlayer("bb"), "1234");
        database.put(createPlayer("a"), "1234");
        database.put(createPlayer("aa"), "1234");
        
    }
    
    private int id = 0;
    
    private MainPlayer createPlayer(String nick)
    {
        int BOARD_WIDTH = 600;
        int BOARD_HIGHT = 400;
        {
            Random random = new Random();
            double x = (double)(random.nextInt(BOARD_WIDTH - 2 * Player.R) + Player.R);
            double y = (double)(random.nextInt(BOARD_HIGHT - 2 * Player.R) + Player.R);
            MainPlayer player = new MainPlayer(nick, id++);//, 
            //               (double)(random.nextInt(BOARD_HIGHT - 2*Ball.R ) 
            //) + Ball.R), Color.BLUE);
            player.setX(x);
            player.setY(y);
            int r1 = random.nextInt(10);
            int r2 = random.nextInt(10);
            int r3 = 1;
            int r4 = 1;
            if(r1 % 2 == 1)
                r3 = -1;
            if(r2 % 2 == 1)
                r4 = -1;
            
            player.setVectorX((4.0 + (double)(r1) / 10.0) * r3);
            player.setVectorY((4.0 + (double)(r2) / 10.0) * r4);
            return player;
            //balls.add(player);
            //n--;
        }
    }
    
    public static MainPlayer find(String nick, String password)
    {
        Iterator it = database.entrySet().iterator();
        while (it.hasNext()) 
        {
            Map.Entry pairs = (Map.Entry)it.next();
            MainPlayer player = (MainPlayer)pairs.getKey();
            if(player.getNick().equals(nick))
            {
                String pass = (String)pairs.getValue();
                if(pass.equals(password))
                    return player;
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }
    
}
