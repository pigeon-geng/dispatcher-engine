package org.pigeon.test;

import org.pigeon.dispatcher.Processor;

import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.9:52 AM
 */
public class TestProcessor extends Processor {

    @Override
    public void process(Map<Object, Object> pipeData, Map<Object, Object> sourceParams) {

        pipeData.put(name, config);

        if(config.containsKey("a2")){
            end = true;
        }
    }
}
