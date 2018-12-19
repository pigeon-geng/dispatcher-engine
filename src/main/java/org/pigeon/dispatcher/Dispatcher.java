package org.pigeon.dispatcher;


import com.google.common.collect.Maps;
import org.pigeon.dispatcher.conf.ProcessorConf;
import org.pigeon.dispatcher.conf.StrategyConf;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by pigeongeng on 2018/11/30.8:33 PM
 */
public class Dispatcher {

    private static Dispatcher ourInstance = new Dispatcher();

    private static Map<String, String> NAME_CLASS_MAPPING;

    private transient final AtomicReference<StrategyDispatcher> lastInstantiatedContext = new AtomicReference<>();

    public static Dispatcher getInstance() {
        return ourInstance;
    }

    private Dispatcher() {
        NAME_CLASS_MAPPING = Maps.newHashMap();
        defaultNameClass();
    }

    private void defaultNameClass() {
        NAME_CLASS_MAPPING.put("Linear", "org.pigeon.dispatcher.strategy.LinearStrategy");
        NAME_CLASS_MAPPING.put("Parallel", "org.pigeon.dispatcher.strategy.ParRefStrategy");
    }

    public Strategy getStrategyInstance(StrategyConf strategyConf) throws Exception{

        String name = String.valueOf(strategyConf.getStrategy());
        String className = name;

        if(NAME_CLASS_MAPPING.containsKey(name)){
            className = NAME_CLASS_MAPPING.get(name);
        }

        return (Strategy)Class.forName(className).newInstance();
    }

    public Processor getProcessorInstance(ProcessorConf processorConf) throws Exception{

        String className = processorConf.getClassName();
        if(NAME_CLASS_MAPPING.containsKey(processorConf.getName())){
            className = NAME_CLASS_MAPPING.get(processorConf.getName());
        }

        return (Processor)Class.forName(className).newInstance();
    }

    public void putNameClass(String name, String classPath) {
        NAME_CLASS_MAPPING.put(name, classPath);
    }

    public synchronized StrategyDispatcher getOrCreate(String configFile) {

        if (lastInstantiatedContext.get() == null) {

            StrategyDispatcher strategyDispatcher = new StrategyDispatcher();
            strategyDispatcher.load(configFile);
            setLastInstantiatedContext(strategyDispatcher);
        }
        return lastInstantiatedContext.get();
    }

    public void clear() {
        lastInstantiatedContext.set(null);
    }

    private synchronized void setLastInstantiatedContext(StrategyDispatcher strategyDispatcher) {
        lastInstantiatedContext.set(strategyDispatcher);
    }

}
