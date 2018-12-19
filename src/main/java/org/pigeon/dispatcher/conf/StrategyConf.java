package org.pigeon.dispatcher.conf;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.11:50 AM
 */
public class StrategyConf {

    String strategy;

    List<String> ref = Lists.newArrayList();

    Map<Object, Object> config = Maps.newHashMap();

    List<ProcessorConf> processors = Lists.newArrayList();

    public StrategyConf() {

    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public List<String> getRef() {
        return ref;
    }

    public void setRef(List<String> ref) {
        this.ref = ref;
    }

    public Map<Object, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<Object, Object> config) {
        this.config = config;
    }

    public List<ProcessorConf> getProcessors() {
        return processors;
    }

    public void setProcessors(List<ProcessorConf> processors) {
        this.processors = processors;
    }
}