package com.osawaseigo.netty.promises;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ResponseFuture implements Future<String> {

	private volatile State state = State.WAITING;
	ArrayBlockingQueue<String> blockingResponse = new ArrayBlockingQueue<String>(1);

	private enum State {
		WAITING,
		DONE
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Should return true when:
	 *  - Future completed normally
	 *  - Future generated an exception
	 *  - Future got cancelled
	 * 
	 */
	@Override
	public boolean isDone() {
		return state == State.DONE;
	}

	@Override
	public String get() throws InterruptedException, ExecutionException {
		return blockingResponse.take();
	}

	@Override
	public String get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		final String responseAfterWait = blockingResponse.poll(timeout, unit);
		if (responseAfterWait == null) {
			throw new TimeoutException();
		}
		return responseAfterWait;
	}

	@Override
	public boolean isSuccess() {
		return state == State.DONE;
	}

	@Override
	public boolean isCancellable() {
		return false;
	}

	@Override
	public Throwable cause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> addListener(
			GenericFutureListener<? extends Future<? super String>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> addListeners(
			GenericFutureListener<? extends Future<? super String>>... listeners) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> removeListener(
			GenericFutureListener<? extends Future<? super String>> listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> removeListeners(
			GenericFutureListener<? extends Future<? super String>>... listeners) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> sync() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> syncUninterruptibly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> await() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<String> awaitUninterruptibly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean await(long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean await(long timeoutMillis) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean awaitUninterruptibly(long timeoutMillis) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNow() {
		return blockingResponse.poll();
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	public void set(String msg) {
		try {
			blockingResponse.put(msg);
			state = State.DONE;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

}
