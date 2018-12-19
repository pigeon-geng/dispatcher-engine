package org.pigeon.dispatcher.strategy;

import com.google.common.collect.Maps;
import org.pigeon.dispatcher.Processor;
import org.pigeon.dispatcher.Strategy;

import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.3:17 PM
 */
public class LinearStrategy extends Strategy{

    @Override
    public Map<Object, Object> process(Map<Object, Object> sourceParams) {

        Map<Object, Object> pipeData = Maps.newHashMap();
        if(!end){
            for (Processor processor: processors) {
                processor.process(pipeData, sourceParams);
                if(processor.end){
                    break;
                }
            }
        }

        return pipeData;
    }
}
