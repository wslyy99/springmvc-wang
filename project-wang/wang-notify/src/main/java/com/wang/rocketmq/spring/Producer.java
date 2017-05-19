package com.wang.rocketmq.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * 生产者，初始化MQProducer
 * 
 */
public class Producer {
	private Logger logger= LoggerFactory.getLogger(getClass());
    private String namesrvAddr;
    private String producerGroup;
    private DefaultMQProducer producer;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void init(){
    	producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
            logger.info("************生产者启动成功************");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        }
    }

    /**
     * 发送消息
     * @param message
     * @return
     */
    public SendResult send(Message message) throws
            InterruptedException, RemotingException, MQClientException, MQBrokerException {
        SendResult result = producer.send(message);
        return result;
    }
    
    /** 
     * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己 
     * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法 
     */  
    // producer.shutdown();  
//    InterruptedException {
//    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {  
//        public void run() {  
//            producer.shutdown();  
//        }  
//    }));
//    }
}