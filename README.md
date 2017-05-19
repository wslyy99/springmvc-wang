# springmvc-wang
基于Springmvc多模块项目框架搭建

这是基于最近公司的一个项目我进行的缩减版，不涉及任何业务代码，只是一个微框架，只在于技术交流与分享，希望对一些正在做技术选型的同学有个参考作用。

该框架目前涉及一些技术选型我大概总结了下：

maven多项目结构
springmvc4（各种拦截器、过滤器、日志、异常处理等）
mybatis3
druid连接池
fastjson
log4j2(这个比较好用，我做过比较，我对日志输出进行了优化)
rocketmq 异步消息
swagger2（swagger的升级版）（springmvc+swagger2）
srping task 调度的使用


整个项目我在原项目中精简了很多，结构相当清晰。后续我会继续提炼一些新技术出来

预计会有：

项目结构会吧service提出来修改成dubbo的生产者
web端修改成getway编程消费者
会适时加入redis mongodb等nosql数据库
还有一些zookeeper的使用
springmvc rest的使用
另外springsession+redis分布式session
分布式ID的生成（redis）
分布式消息等

在使用dubbo的用到过的点有 dubbo的容错机制、哈希一致、结果缓存、多生产者配置等等

