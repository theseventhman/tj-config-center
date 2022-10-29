package com.tj.exercise.springframework.tj.config.center.core;

import java.lang.reflect.Field;
import java.security.Key;
import java.security.cert.TrustAnchor;
import java.util.Map;

/**
 * @Author: tj
 * @Date: 2022/10/29 22:35
 */
public class PropertyTrigger {

    public static void change(String ymlContent){
        Map<String,Object> newMap = YamlConverter.convert(ymlContent);
        Map<String,Object> oldMap = EnvInitializer.getEnvMap();

        oldMap.keySet().stream()
                .filter(key->newMap.containsKey(key))
                .filter(key->!newMap.get(key).equals(oldMap.get(key)))
                .forEach(key->{
                    System.out.println(key);
                    Object newVal = newMap.get(key);
                    change(key,newVal);
                });
        EnvInitializer.setEnvMap(oldMap);
    }

    private static void change(String propertyName, Object newValue){
        System.out.println("newValue:"+newValue);
        Map<String,Map<Class,String>> pool = VariablePool.getPool();
        Map<Class,String> classProMap = pool.get(propertyName);

        classProMap.forEach((clazzName,realPropertyName)->{
          try{
              Object bean = SpringContextUtil.getBean(clazzName);
              Field field = clazzName.getDeclaredField(realPropertyName);
              field.setAccessible(true);
              field.set(bean,newValue);
          } catch(NoSuchFieldException | IllegalAccessException e){
              e.printStackTrace();
          }
        });
    }
}
