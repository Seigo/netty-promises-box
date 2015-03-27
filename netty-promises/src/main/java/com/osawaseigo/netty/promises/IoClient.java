package com.osawaseigo.netty.promises;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;

public class IoClient {
	public static final String MESSAGE_LONG_DELAY = "Message that takes long to be replied to";
	public static final String MESSAGE_SHORT_DELAY = "Message that gets replied to instantly";
	private ChannelFuture channelFuture = null;

	public IoClient(String host, int port) {
		System.out.println("Initializing client and connecting to server..");
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new StringDecoder());
					channel.pipeline().addLast(new StringEncoder());
					channel.pipeline().addLast(new MyAppClientHandler());
				}
			});
		
		channelFuture = b.connect(host, port);
	}

	public ResponseFuture send(final String msg) {
		final ResponseFuture responseFuture = new ResponseFuture();

		channelFuture.addListener(new GenericFutureListener<ChannelFuture>() {
			@Override
			public void operationComplete(ChannelFuture future)
					throws Exception {
				channelFuture.channel().pipeline().get(MyAppClientHandler.class).setResponseFuture(responseFuture);
				channelFuture.channel().writeAndFlush(msg);								
			}
		});
		
		return responseFuture;
	}

	public void close() {
		channelFuture.channel().close();		
	}
}
