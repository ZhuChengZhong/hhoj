package com.hhoj.judger.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhoj.judger.entity.Submit;
import com.hhoj.judger.service.SubmitService;
/**
 * 提交 消息接收监听器
 * @author zhu
 *
 */

public class SubmitMessageListener implements MessageListener{
	private static Logger logger = LoggerFactory.getLogger(SubmitMessageListener.class);
	@Autowired
	private SubmitService submitService;
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage=(ObjectMessage)message;
		try {
			Object o=objectMessage.getObject();
			if(o instanceof Submit) {
				Submit submit=(Submit)o;
				submitService.updateSubmit(submit);
				logger.info("接收到判题机发回的判断结果并更新至数据库, submit id:"+submit.getSid());
			}
		} catch (JMSException e) {
			logger.error("消息接收异常",e);
		}
	}

}
