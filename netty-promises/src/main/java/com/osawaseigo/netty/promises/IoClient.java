package com.osawaseigo.netty.promises;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class IoClient {
	private ChannelFuture channelFuture = null;

	public IoClient(String host, int port) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new MyAppClientHandler());
				}
			});
		
		channelFuture  = b.connect();
	}

	public ResponseFuture send(String msg) {
		ResponseFuture future = new ResponseFuture();
		channelFuture.channel().pipeline().get(MyAppClientHandler.class);
		channelFuture.channel().writeAndFlush(msg);
		return null;
	}
}
