package com.zml.netty.echo;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: zhumeilu
 * Date: 2017/7/7
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(ctx.channel().remoteAddress()+"->Server :"+ msg.toString());
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
