package com.wang.rocketmq.spring;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 监听wrapper，用于处理共通业务，并转发消息到业务监听中。
 * 
 */
public class RocketMqMessageWrapper implements MessageListenerConcurrently {
    private RocketMqMessageListener rocketMqMessageListener;

    public RocketMqMessageListener getRocketMqMessageListener() {
        return rocketMqMessageListener;
    }

    public void setRocketMqMessageListener(RocketMqMessageListener rocketMqMessageListener) {
        this.rocketMqMessageListener = rocketMqMessageListener;
    }

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        //TODO 实现共通的业务
        if(rocketMqMessageListener.onMessage(list,consumeConcurrentlyContext)){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }else{
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
