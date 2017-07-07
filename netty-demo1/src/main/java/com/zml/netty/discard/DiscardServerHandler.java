package com.zml.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: zhumeilu
 * Date: 2017/7/7
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf)msg;
//        while (byteBuf.isReadable()){
//            System.out.println((char)byteBuf.readByte());
//            System.out.flush();
//        }
        ctx.write(msg);
        ctx.flush(); // (2)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
