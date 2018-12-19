package org.pigeon.dispatcher.conf;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.11:50 AM
 */
public class ProcessorConf {

    String name;

    String className;

    Map<Object, Object> config = Maps.newHashMap();

    public ProcessorConf() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<Object, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<Object, Object> config) {
        this.config = config;
    }
}
