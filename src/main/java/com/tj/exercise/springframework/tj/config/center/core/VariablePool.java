package com.tj.exercise.springframework.tj.config.center.core;

import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Author: tj
 * @Date: 2022/10/29 19:21
 */
public class VariablePool {

    public static Map<String,Map<Class,String>> pool = new HashMap<>();
    private static final  String regex="^(\\$\\{)(.)+(\\})$";
    private static Pattern pattern;

    static {
        pattern = Pattern.compile(regex);
    }

    public static void add(Class clazz){
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            if(field.isAnnotationPresent(Value.class)){
                Value annotation = field.getAnnotation(Value.class);
                String annoValue = annotation.value();
                if(!pattern.matcher(annoValue).matches()){
                    continue;
                }

                annoValue = annoValue.replace("${","");
                annoValue = annoValue.substring(0,annoValue.length()-1);

                Map<Class,String> clazzMap = Optional.ofNullable(pool.get(annoValue))
                        .orElse(new HashMap<>());
                clazzMap.put(clazz,field.getName());
                pool.put(annoValue,clazzMap);
            }
        }
    }

    public static  Map<String,Map<Class,String>> getPool(){
        return  pool;
    }


}
