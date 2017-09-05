package _3_2_protobuf;

import com.zml.tank.domain.LoginRequest;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhumeilu on 17/9/5.
 */
public class ProtobufServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LoginRequest.LoginCommand command = (LoginRequest.LoginCommand) msg;
        if("zml".equals(command.getUsername())){
            System.out.println("服务器接收到的消息："+command.toString());
            ctx.writeAndFlush(resp(command.getUsername()));
        }
    }

    private LoginRequest.LoginCommand resp(String username) {
        LoginRequest.LoginCommand.Builder builder = LoginRequest.LoginCommand.newBuilder();

        builder.setUsername("hello zml");
        return builder.build();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registed----");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active----");
        super.channelActive(ctx);
    }
}
