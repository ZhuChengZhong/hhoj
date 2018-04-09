package com.hhoj.judger.mq;

import java.util.concurrent.TimeUnit;

import com.hhoj.judger.entity.Submit;

public class SubmitSenderTest {
	public static void main(String[] args) throws InterruptedException {
		SubmitSender sender=new SubmitSender();
		for(int i=0;i<20;i++) {
			Submit s=new Submit();
			s.setSid(1);
			sender.sendSubmit(s.getSid());
			TimeUnit.SECONDS.sleep(1);
		}
		sender.stop();
	}
}
