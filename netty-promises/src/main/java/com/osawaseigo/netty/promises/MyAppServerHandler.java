package com.osawaseigo.netty.promises;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyAppServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println("MyAppServerHandler received: " + msg);
		
		System.out.println("Doing stuff that delays response..");
		
		ctx.writeAndFlush(msg);
	}
}
