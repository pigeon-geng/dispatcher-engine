package org.pigeon.dispatcher;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by pigeongeng on 2018/11/30.8:25 PM
 */
public abstract class Strategy {

    //唯一标志
    public String name = "";
    //链路是否终止标志
    public boolean end = false;
    //策略列表
    public List<Strategy> ref = Lists.newArrayList();
    //处理器列表
    public List<Processor> processors = Lists.newArrayList();
    //配置
    public Map<Object, Object> config = Maps.newHashMap();
    /**
     * 初始化
     * */
    public void initialize(String name, List<Strategy> ref, List<Processor> processors, Map<Object, Object> config) {
        this.name = name;
        this.ref = ref;
        this.processors = processors;
        this.config = config;
    }

    /**
     * 数据处理
     * @param sourceParams 原始参数
     * @return 生成一个管道数据，在整个链路中共享
     * */
    public abstract Map<Object, Object> process(Map<Object, Object> sourceParams);

}
