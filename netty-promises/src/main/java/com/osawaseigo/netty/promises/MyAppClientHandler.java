package com.osawaseigo.netty.promises;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyAppClientHandler extends SimpleChannelInboundHandler<String> {

	private ResponseFuture responseFuture;

	public void setResponseFuture(ResponseFuture future) {
		this.responseFuture = future;		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println("[client][in] " + msg);
		responseFuture.set(msg);
	}
}
