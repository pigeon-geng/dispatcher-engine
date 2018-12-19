package org.pigeon.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.pigeon.dispatcher.conf.ProcessorConf;
import org.pigeon.dispatcher.conf.StrategyConf;
import org.pigeon.dispatcher.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by pigeongeng on 2018/11/30.8:41 PM
 */
public class StrategyDispatcher {

    private static Map<String, Strategy> STRATEGIES = Maps.newConcurrentMap();
    private static Logger LOGGER = LoggerFactory.getLogger(StrategyDispatcher.class);
    private static Map<String, StrategyConf> STRATEGY_CONF_MAPPING = Maps.newHashMap();

    public Map<Object, Object> dispatch(Map<Object, Object> params) {

        Map<Object, Object> result = Maps.newHashMap();

        if(params.containsKey(Constant.CLIENT)) {
            String clientType = String.valueOf(params.get(Constant.CLIENT));
            Strategy strategy = findStrategies(clientType);
            if(null != strategy){
                try {
                    result.putAll(strategy.process(params));
                } catch (Exception e){
                    LOGGER.error("调用链路异常", e);
                }
            }else{
                LOGGER.error("调用链路标志异常，找不到 {}", clientType);

            }
        }else{
            LOGGER.error("调用链路异常，必须包含{}参数", Constant.CLIENT);
        }

        return result;
    }

    private Strategy findStrategies(String key) {
        return STRATEGIES.get(key);
    }

    private synchronized void reload(String configStr) {
        load(configStr);
    }

    public void load(String configStr) {
        STRATEGY_CONF_MAPPING = JSON.parseObject(configStr, new TypeReference<Map<String, StrategyConf>>() {});
        STRATEGY_CONF_MAPPING.entrySet().forEach((f) -> {
            try {
                createStrategy(f.getKey(), f.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *
     * 初始化策略
     *
     * */
    private Strategy createStrategy(String name, StrategyConf strategyConf) throws Exception {

        if (STRATEGIES.containsKey(name)) return null;

        Strategy strategy = Dispatcher.getInstance().getStrategyInstance(strategyConf);
        strategy.initialize(name, createRefs(strategyConf.getRef()), createProcessors(strategyConf.getProcessors()), strategyConf.getConfig());
        STRATEGIES.put(name, strategy);

        return strategy;
    }

    /**
     * 引用策略，若引用的策略未初始化，则进行初始化操作
     *
     * */
    private List<Strategy> createRefs(List<String> refs) throws Exception{

        List<Strategy> result = Lists.newArrayList();

        refs.forEach(ref -> {
            StrategyConf refStrategyConf = STRATEGY_CONF_MAPPING.get(ref);
            if (STRATEGIES.containsKey(ref)) {
                result.add(STRATEGIES.get(ref));
            } else {
                Strategy refStrategy = null ;
                try {
                    refStrategy = createStrategy(ref, refStrategyConf);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(null != refStrategy){
                    result.add(refStrategy);
                }
            }
        });

        return result;
    }

    /**
     * 初始化处理器列表，
     *
     * */
    private List<Processor> createProcessors(List<ProcessorConf> processorConfs) throws Exception{

        List<Processor> processors = Lists.newArrayList();

        processorConfs.forEach(processorConf -> {

            Processor processor;
            try {
                processor = Dispatcher.getInstance().getProcessorInstance(processorConf);
                processor.initialize(processorConf.getName(), processorConf.getConfig());
                processors.add(processor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return processors;
    }

}