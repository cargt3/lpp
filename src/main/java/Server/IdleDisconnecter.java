/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.Date;

/**
 *
 * @author Karol
 */
public class IdleDisconnecter extends ChannelDuplexHandler {

    public IdleDisconnecter(){
        super();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
                ctx.close();
                log(ctx.channel().remoteAddress() + " has been disconnected due to idle time");
            }
        }
    }
    
    private void log(String str)
    {
        System.out.println("[IdleDisconnector][" + new Date() + "]" + str);
    }
}
