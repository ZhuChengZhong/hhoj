package com.hhoj.judger.mq;

import java.util.concurrent.BlockingQueue;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提交 消息接收监听器
 * @author zhu
 *
 */

public class SubmitMessageListener implements MessageListener{
	
	private static Logger logger = LoggerFactory.getLogger(SubmitMessageListener.class);

	
	private BlockingQueue<Integer>queue;
	
	public SubmitMessageListener(BlockingQueue<Integer>queue) {
		this.queue=queue;
	}
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage=(MapMessage)message;
			Integer submitId=mapMessage.getInt("submitId");
			if(submitId!=null) {
				queue.put(submitId);
			}
			mapMessage.acknowledge();
			logger.info("成功从消息队列中获取submitId:"+submitId);
		}catch(Exception e) {
			logger.error("从mapMessage获取submitId失败",e);
			
		}
	}
	
}
