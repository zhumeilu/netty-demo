package _3_2_protobuf;

import com.zml.tank.domain.LoginRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhumeilu on 17/9/5.
 */
public class ProtobufClientHandler extends ChannelHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("收到服务端的返回："+msg);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("第一次连接");
        for(int i = 0;i<10;i++){
            ctx.writeAndFlush(subReq(i));
        }
        ctx.flush();

    }

    private LoginRequest.LoginCommand subReq(int i) {

        LoginRequest.LoginCommand.Builder builder = LoginRequest.LoginCommand.newBuilder();
        builder.setUsername("zml");
        return builder.build();

    }
}
