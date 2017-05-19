package com.wang.rocketmq;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.wang.rocketmq.spring.RocketMqMessageListener;
/**
 * 业务监听实现
 * 
 */
public class SimpleRocketMqListener implements RocketMqMessageListener {
	
	
	private Logger logger= LoggerFactory.getLogger(getClass());
	
	String json_str="";
	
    public boolean onMessage(List<MessageExt> messages, ConsumeConcurrentlyContext Context) {
    	logger.info("&&&&&&&&&&&&&&&接收到消息,"+messages.toString()+"&&&&&&&&&&&&&&&&&&&");
        
        for (int i = 0; i < messages.size(); i++)
        {
        	Message msg = messages.get(i);
        	if (msg.getTopic().equals("HomeActionTopic")) {
                // TODO 执行Topic的消费逻辑-推送消息
                if (msg.getTags() != null && msg.getTags().equals("pushMessage")) {
                  
                }
            }
            
        }
        return true;
    }
}
