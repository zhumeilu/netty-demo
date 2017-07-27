package com.ken.server.handler;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.ken.server.codec.ActionScriptObject;
import com.ken.server.domain.User;
import com.ken.server.manager.ChannelManager;
import com.ken.server.manager.UserManager;

import flex.messaging.io.amf.ASObject;

public class SystemHandler extends SimpleChannelHandler{

	public void messageReceived(ChannelHandlerContext ctx,MessageEvent e)throws Exception{
		ActionScriptObject ao = new ActionScriptObject((ASObject)e.getMessage());
		String cmd = ao.getString("cmd");
		if(cmd.equals("Login")){
			firstLogin(ao,ctx,e);
		}else{
			super.messageReceived(ctx,e);
		}
	}

	private void firstLogin(ActionScriptObject ao,ChannelHandlerContext ctx,MessageEvent e){
		String userName = ao.getString("n");
		String zoneName = ao.getString("z");

		User user = UserManager.instance.joinZone(userName,zoneName,e.getChannel());
		ChannelManager.instance.addUser(e.getChannel(),user);

		ActionScriptObject res = new ActionScriptObject();
		res.putString("cmd", "Login");
		res.putBool("res",true);
		e.getChannel().write(res.getObject());
	}

	public void channelDisconnected(ChannelHandlerContext ctx,ChannelStateEvent e)throws Exception{
		closeChannel(e.getChannel());
		super.channelDisconnected(ctx,e);
	}

	private void closeChannel(Channel channel){
		User user = ChannelManager.instance.getUser(channel);
		if(user!=null){
			UserManager.instance.lostCon(user);
		}
	}
}
