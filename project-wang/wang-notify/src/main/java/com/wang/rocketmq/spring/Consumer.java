package com.wang.rocketmq.spring;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者，装载监听实现后，启动监听。
 * 
 */
public class Consumer {
    private Logger logger= LoggerFactory.getLogger(getClass());
    private String topic;
    private String subExpression;
    private String consumerGroup;
    private String namesrvAddr;
    private RocketMqMessageListener rocketMqMessageListener;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public RocketMqMessageListener getRocketMqMessageListener() {
        return rocketMqMessageListener;
    }

    public void setRocketMqMessageListener(RocketMqMessageListener rocketMqMessageListener) {
        this.rocketMqMessageListener = rocketMqMessageListener;
    }

    public void init(){
        logger.info("启动RocketMq监听...{}",this);
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer();
        consumer.setConsumerGroup(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        //consumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        try {
            //订阅HomeActionTopic下Tag为push的消息
            consumer.subscribe(topic, subExpression);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            
            // 设置为集群消费(区别于广播消费)
            //consumer.setMessageModel(MessageModel.CLUSTERING);
            
            RocketMqMessageWrapper rocketMqMessageWrapper=new RocketMqMessageWrapper();
            if(this.rocketMqMessageListener==null){
                throw new RuntimeException("please define a rocketMqMessageListener for consumer!");
            }
            rocketMqMessageWrapper.setRocketMqMessageListener(this.rocketMqMessageListener);
            consumer.registerMessageListener(rocketMqMessageWrapper);
            consumer.start();
            logger.info("启动RocketMq监听成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "topic='" + topic + '\'' +
                ", subExpression='" + subExpression + '\'' +
                ", consumerGroup='" + consumerGroup + '\'' +
                ", namesrvAddr='" + namesrvAddr + '\'' +
                ", rocketMqMessageListener=" + rocketMqMessageListener +
                '}';
    }
}