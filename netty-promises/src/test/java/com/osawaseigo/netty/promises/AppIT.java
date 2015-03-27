package com.osawaseigo.netty.promises;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;

import io.netty.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AppIT {
	
	private static final String serverIpAddress = "127.0.0.1";
	private static final int serverPort = 1337;
	private IoServer server;
	
	@Before
	public void setup() {
		server = new IoServer(serverPort);
		new Thread(server).start();
	}
	
	@Test
	public void shouldSendToFutureChannel() {
		IoClient client = new IoClient(serverIpAddress, serverPort);
		ResponseFuture future = client.send("Test1 msg to server");
		assertNotNull(future);
		// No NullPointerException for trying to use future channel!
		client.close();
	}
	
	@Test
	public void shouldBeAbleToDoOtherStuffAndRetrieveResponseLater() throws InterruptedException, ExecutionException {
		IoClient client = new IoClient(serverIpAddress, serverPort);
		System.out.println("Sending message..");
		ResponseFuture futureResponse = client.send(IoClient.MESSAGE_LONG_DELAY);
		long startingWaitTime = System.currentTimeMillis();
		assertNotNull(futureResponse);
		System.out.println("Doing other stuff..");
		System.out.println("Let's see if server replied..");
		String reply = futureResponse.get();
		long totalWaitTime = System.currentTimeMillis() - startingWaitTime;
		assertEquals("Response to " + IoClient.MESSAGE_LONG_DELAY, reply);
		assertTrue(totalWaitTime >= 3000);
		System.out.println("Received reply after "+ totalWaitTime +" millis: " + reply);
		client.close();
	}
	
	@Test
	public void shouldReceiveResponseNormallyIfServerRespondedFasterThanExpected() throws InterruptedException, ExecutionException {
		IoClient client = new IoClient(serverIpAddress, serverPort);
		System.out.println("Sending message..");
		ResponseFuture futureResponse = client.send(IoClient.MESSAGE_SHORT_DELAY);
		assertNotNull(futureResponse);
		System.out.println("Doing other stuff that takes a while..");
		Thread.sleep(1000);
		System.out.println("Let's see if server replied..");
		long startingWaitTime = System.currentTimeMillis();
		String reply = futureResponse.get();
		assertEquals("Response to " + IoClient.MESSAGE_SHORT_DELAY, reply);
		long totalWaitTime = System.currentTimeMillis() - startingWaitTime;
		assertTrue(totalWaitTime < 10);
		System.out.println("Received reply after "+ totalWaitTime +" millis: " + reply);
		client.close();
	}
	
	@Test
	public void shouldBeAbleToDoOtherStuffWhileResponseHasntArrived() throws Exception {
		IoClient client = new IoClient(serverIpAddress, serverPort);
		System.out.println("Sending message..");
		ResponseFuture futureResponse = client.send(IoClient.MESSAGE_LONG_DELAY);
		assertNotNull(futureResponse);
		System.out.println("Doing other stuff while reply doesn't arrive..");
		int count = 1;
		while (!futureResponse.isDone()) {
			System.out.println("Doing stuff ("+count+")..");
			Thread.sleep(300);
			count++;
		}
		long startingWaitTime = System.currentTimeMillis();
		String reply = futureResponse.get();
		assertEquals("Response to " + IoClient.MESSAGE_LONG_DELAY, reply);
		long totalWaitTime = System.currentTimeMillis() - startingWaitTime;
		assertTrue(totalWaitTime < 10);
		System.out.println("Received reply after "+ totalWaitTime +" millis: " + reply);
		client.close();
	}
}
