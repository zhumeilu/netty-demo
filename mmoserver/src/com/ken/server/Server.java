package com.ken.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.ken.server.manager.ZoneManager;

public class Server {
	public static void main(String[] args) {
		init();
		setUpSocketServer();
	}

	private static void init() {
		ZoneManager.instance.setUpZone();
	}

	private static void setUpSocketServer() {
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors
						.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ServerPipelineFactory());
		bootstrap.bind(new InetSocketAddress(8080));
	}
}
