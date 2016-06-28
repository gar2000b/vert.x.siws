package com.onlineinteract.thread;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDecrementer implements Runnable {

	private CountDownLatch latch;

	public CountDownLatchDecrementer(CountDownLatch latch) {
		this.latch = latch;
	}
	
	@Override
	public void run(){
		try {
			System.out.println("CountDownLatchDecrementer Thread sleeping for 5 secs.");
			Thread.sleep(5000);
			latch.countDown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
