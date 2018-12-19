package org.pigeon.test;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.pigeon.dispatcher.Dispatcher;
import org.pigeon.dispatcher.StrategyDispatcher;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.9:52 AM
 */
public class Test {

    public static void main(String[] args) throws Exception{

        Map<Object, Object> params = Maps.newHashMap();
        params.put("_client_", "test");

        Dispatcher dispatcher = Dispatcher.getInstance();

        dispatcher.putNameClass("TestProcessor1", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor2", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor3", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_11", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_12", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_13", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_21", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_22", "org.pigeon.test.TestProcessor");
        dispatcher.putNameClass("TestProcessor_23", "org.pigeon.test.TestProcessor");

        String conf = Joiner.on("").join(Files.readLines(new File("./config/strategy.json"), Charset.forName("UTF-8")));
        StrategyDispatcher strategyDispatcher = dispatcher.getOrCreate(conf);

        System.out.println(strategyDispatcher.dispatch(params));
    }
}
