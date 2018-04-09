package com.hhoj.judger.mq;

import java.util.concurrent.TimeUnit;

public class SubmitReceiverTest {
	public static void main(String[] args) throws InterruptedException {
		SubmitReceiver receiver=new SubmitReceiver();
		receiver.receiveSubmit(new SubmitMessageListener());
		TimeUnit.SECONDS.sleep(10);
		receiver.commit();
		receiver.close();
	}
}
