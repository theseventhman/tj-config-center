package com.tj.exercise.springframework.tj.config.center.core;

import com.sun.javafx.collections.MappingChange;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.yaml.snakeyaml.Yaml;
import sun.awt.SunHints;

import java.security.Key;
import java.util.*;

/**
 * @Author: tj
 * @Date: 2022/10/29 21:17
 */
public class YamlConverter {

    public static Map<String,Object> convert(String ymlContent){
        Map<String,Object> propertiesMap = new HashMap<>();
        Yaml yaml = new Yaml();
        Iterable<Object> objects = yaml.loadAll(ymlContent);
        for(Object object : objects){
            doConvert((Map)object,null,propertiesMap);
        }
        return propertiesMap;
    }

    public static void doConvert(Map<String,Object> map, Object parentKey, Map<String, Object> propertiesMap) {
        String prefix = (Objects.isNull(parentKey))?"":parentKey+".";
        map.forEach((key,value)->{
            if(value instanceof Map){
                doConvert((Map) value,prefix+key,propertiesMap);
            }else{
                propertiesMap.put(prefix+key,value);
            }
        });
    }

    public static Map<String,Object> monoToMultiLayer(Map<String,Object> envMap, Map<String,Object> multiMap){
        if(multiMap ==null){
            multiMap = new HashMap<>();
        }
        for(String key : envMap.keySet()){
            if(!key.contains(".")){
                multiMap.put(key,envMap.get(key));
            }else{
                int index = key.indexOf(".");
                String outKey = key.substring(0,index);
                String innerKey = key.substring(index+1);

                Object obj = multiMap.get(outKey);
                Optional<Object> mapOpt = Optional.ofNullable(obj);
                Map<String,Object> innerMap = mapOpt.isPresent()?(Map)mapOpt.get():new HashMap<>();

                if(!innerKey.contains("."))
                    innerMap.put(innerKey,envMap.get(key));
                else{
                    int index2 = innerKey.indexOf(".");
                    String tempKey = innerKey.substring(0,index2);
                    String tempInnerKey = innerKey.substring(index2+1);

                    Map<String,Object> tempMap = new HashMap<>();
                    tempMap.put(tempInnerKey,envMap.get(key));

                    Optional<Object> opt = Optional.ofNullable(innerMap.get(tempKey));
                    innerMap.put(tempKey,monoToMultiLayer(tempMap,
                            opt.isPresent()?(Map<String, Object>) opt.get():new HashMap<>()));
                }
                multiMap.put(outKey,innerMap);
            }
        }
        return multiMap;

    }
}
