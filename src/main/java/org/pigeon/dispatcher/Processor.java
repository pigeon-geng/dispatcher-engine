package org.pigeon.dispatcher;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by pigeongeng on 2018/11/30.8:05 PM
 */
public abstract class Processor {

    //唯一标志
    public String name = "";
    //实现类
    public String className = "";
    //链路是否终止标志
    public boolean end = false;
    //配置
    public Map<Object, Object> config = Maps.newHashMap();

    //初始化
    public void initialize(String name, Map<Object, Object> config){
        this.name = name;
        this.config = config;
    }

    /**
     * 数据处理
     * @param pipeData 管道数据，在整个链路中共享
     * @param sourceParams 原始参数
     * */
    public abstract void process(Map<Object, Object> pipeData, Map<Object, Object> sourceParams);

}