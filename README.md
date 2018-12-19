## DispatcherEngine

模块化开发，是一个强大的模块的引擎。
copy from https://github.com/allwefantasy/ServiceframeworkDispatcher


使用代码如下：


其中 dispatcher 为单例。
params 是 一个 Map[String,String],里面有个必填的key,叫做\_client\_,该\_client\_代表 
调用哪个策略。


## dispatcher-engine 核心概念
 
* Strategy  策略，对应一个完整的请求。该策略决定 Processor ,ref 是如何被组合调用的
* Processor  处理器，是数据的处理单元
* ref  对其他的策略的引用


所有的模块都需要实现  Strategy / Processor 两个个中的某个接口



