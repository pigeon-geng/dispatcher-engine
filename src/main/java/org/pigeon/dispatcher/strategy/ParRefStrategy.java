package org.pigeon.dispatcher.strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.pigeon.dispatcher.Processor;
import org.pigeon.dispatcher.Strategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by pigeongeng on 2018/12/3.10:52 AM
 */
public class ParRefStrategy extends Strategy {


    ThreadPoolExecutor executor = null;

    @Override
    public void initialize(String name, List<Strategy> ref, List<Processor> processors, Map<Object, Object> config) {
        super.initialize(name, ref, processors, config);

        int corePoolSize = Integer.parseInt(String.valueOf(config.getOrDefault("corePoolSize", 3)));
        int maximumPoolSize = Integer.parseInt(String.valueOf(config.getOrDefault("maximumPoolSize", 10)));
        int keepAliveTime = Integer.parseInt(String.valueOf(config.getOrDefault("keepAliveTime", 2)));
        TimeUnit timeUnit = TimeUnit.valueOf(String.valueOf(config.getOrDefault("timeUnit", "SECONDS")));
        executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, new SynchronousQueue<>());

    }

    @Override
    public Map<Object, Object> process(Map<Object, Object> sourceParams){

        Map<Object, Object> pipeData = Maps.newHashMap();

        if(!end) {

            List<RefTask> tasks = Lists.newArrayList();

            ref.forEach(refTask -> tasks.add(new RefTask(refTask, sourceParams)));

            try {
                List<Future<RefProcessResult>> futures = executor.invokeAll(tasks, 5000, TimeUnit.MILLISECONDS);

                futures.forEach(future -> {
                    try {
                        pipeData.put(future.get().name, future.get().processResult);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Processor processor : this.processors){
                processor.process(pipeData, sourceParams);
                if (processor.end) {
                    break;
                }
            }

        }

        return pipeData;
    }

}

class RefTask implements Callable<RefProcessResult> {

    Strategy strategy;
    Map<Object, Object> sourceParams;

    public RefTask(Strategy strategy, Map<Object, Object> sourceParams){

        this.strategy = strategy;
        this.sourceParams = sourceParams;
    }

    @Override
    public RefProcessResult call() throws Exception {
        return new RefProcessResult(strategy.name, strategy.process(sourceParams));
    }
}

class RefProcessResult{

    String name;
    Map<Object, Object> processResult;

    public RefProcessResult(String name, Map<Object, Object> processResult) {
        this.name = name;
        this.processResult = processResult;
    }
}
