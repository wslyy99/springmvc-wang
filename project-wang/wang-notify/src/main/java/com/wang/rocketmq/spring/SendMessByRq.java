package com.wang.rocketmq.spring;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

@Service(value = "sendMessByRq")
public class SendMessByRq {
 
	private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
	
	@Resource
	private Producer rocketMqProducer;
	
    public boolean send(String topic, String tags, String keys, String body)
		{
			boolean flag=false;
			try {
	            Message msg = new Message(topic,
	            		tags,
	            		keys,
	                    body.getBytes("UTF8"));
	            logger.info("************************准备发送信息************************");
	            SendResult result = rocketMqProducer.send(msg);
	      // 当消息发送失败时如何处理
	            if (result == null || result.getSendStatus() != SendStatus.SEND_OK) {
	                // TODO
	        logger.info("************************发送失败,发送状态:"
	            + result.getSendStatus() + "************************");
	            }
	            else
	            {
	            	 logger.info("************************id:" + result.getMsgId() +" result:" + result.getSendStatus()+"************************");
	            	 flag=true;
	            }
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e1) {
	                e1.printStackTrace();
	            }
	        }finally{
	        	
	        }
			
			return flag;
		}
    
    public static void main(String[] args) {
		new SendMessByRq().send("HomeActionTopic", "sms", "1", "test");
	}
}
