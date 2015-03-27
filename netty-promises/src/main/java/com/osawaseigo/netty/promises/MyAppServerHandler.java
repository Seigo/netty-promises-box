package com.osawaseigo.netty.promises;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyAppServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println("[server][in] " + msg);
		
		if (IoClient.MESSAGE_LONG_DELAY.equals(msg)) {
			Thread.sleep(3000);
		}

		String answer = "Response to " + msg;
		System.out.println("[server][out] " + answer);
		ctx.writeAndFlush(answer);
	}
}
