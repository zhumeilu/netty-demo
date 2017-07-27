package com.ken.server.handler;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.ken.server.codec.ActionScriptObject;
import com.ken.server.domain.User;
import com.ken.server.extension.Extension;
import com.ken.server.manager.ChannelManager;
import com.ken.server.manager.ExtensionManager;

import flex.messaging.io.amf.ASObject;

public class ExtensionHandler extends SimpleChannelHandler {
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		ActionScriptObject ao = new ActionScriptObject((ASObject)e.getMessage());
		String cmd = ao.getString("cmd");
		String roomName = ao.getString("r");
		String exName = ao.getString("ex");
		ActionScriptObject param = ao.getObject("p");

		User user = ChannelManager.instance.getUser(e.getChannel());
		if(user != null){
			Extension extension = ExtensionManager.instance.getExtension(exName);
			extension.request(cmd,param,user,roomName);
		}
	}
}
