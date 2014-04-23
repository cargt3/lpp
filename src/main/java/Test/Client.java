/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;

import ClientNetworkEngine.ClientNetworkEngine;
import Protocol.Protocol.PacketType;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

/**
 *
 * @author Karol
 */
public class Client {
    public static void main(String args[]) throws Exception
    {
        ClientNetworkEngine chatClient = new ClientNetworkEngine("localhost",8000);
        //chatClient.run();
        
        String nick     = "aaa";
        String password = "1234";
        
        PacketType packetType = chatClient.LogIn(nick, password).getPacketType();
        
        
        clientLog(packetType.toString());
        
        if(packetType == PacketType.LOGIN_SUCCESS)
            System.out.println("User successfuly loged: " + nick);
        if(packetType == PacketType.LOGIN_FAIL)
            System.out.println("Wrong nick or password: " + nick + "****");//!!!!
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String temp1 = in.readLine();
            //channel.writeAndFlush(new Packet(1,Protocol.LOGIN_REQUEST,new LoginPacket("dfdfsdf","sdkjfskdf")));
            if(temp1.equals("end")) {
                break;
            } 
        }  
    }
    private static void clientLog(String str)
    {
        System.out.println("[Client Log]" + "[" + new Date() + "]" + str);
    }
}
