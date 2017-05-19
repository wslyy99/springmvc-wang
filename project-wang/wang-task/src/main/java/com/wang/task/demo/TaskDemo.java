package com.wang.task.demo;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;

/**
 * @ClassName: RewardsEventQueue
 * @Description: 奖励活动执行引擎消息队列
 * @author administrator
 * @date 2016年6月23日 下午3:31:06
 *
 */
public class TaskDemo {
   
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;// 任务执行对象
    
    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    
    /**
     * @Title: RewardsEventProcess
     * @Description: 奖励活动执行引擎
     * @author administrator 2016年6月25日 下午3:38:05
     * @throws
     */
    public void RewardsEventProcess()
    {
        try
        {
            taskExecutor.execute(new Runnable()
            {
                public void run()
                {
                    
                    logger.info("*****************************BEGIN—任务引擎开始");
                 
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
