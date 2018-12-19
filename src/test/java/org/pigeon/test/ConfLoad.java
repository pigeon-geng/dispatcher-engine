package org.pigeon.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.pigeon.dispatcher.conf.StrategyConf;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by pigeongeng on 2018/12/1.11:54 AM
 */
public class ConfLoad {

    public static void main(String[] args) throws Exception{

        String conf = Joiner.on("").join(Files.readLines(new File("./config/strategy.json"), Charset.forName("UTF-8")));


        Map<String, StrategyConf> confMap = JSON.parseObject(conf, new TypeReference<Map<String, StrategyConf>>() {});
        System.out.println(confMap);

    }
}
