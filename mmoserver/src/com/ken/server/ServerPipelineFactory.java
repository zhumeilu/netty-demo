package com.ken.server;


import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

import com.ken.server.codec.Amf3Decoder;
import com.ken.server.codec.Amf3Encoder;
import com.ken.server.handler.ExtensionHandler;
import com.ken.server.handler.SystemHandler;

public class ServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new Amf3Decoder());
		pipeline.addLast("encoder", new Amf3Encoder());
		
		pipeline.addLast("sys", new SystemHandler());
		pipeline.addLast("ex", new ExtensionHandler());
		
		return pipeline;
	}

}
