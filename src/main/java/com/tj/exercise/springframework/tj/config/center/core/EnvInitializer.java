package com.tj.exercise.springframework.tj.config.center.core;

import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.boot.web.embedded.netty.NettyWebServer;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: tj
 * @Date: 2022/10/29 21:48
 */
public class EnvInitializer {

    private static Map<String,Object> envMap = new HashMap<>();

    public static void init(){
        String rootPath = EnvInitializer.class.getResource("/").getPath();
        List<String> fileList = FileScanner.findFileByType(rootPath,null,FileScanner.TYPE_CLASS);
        for(String ymlFilePath : fileList){
            rootPath = FileScanner.getRealRootPath(rootPath);
            ymlFilePath = ymlFilePath.replace(rootPath,"");
            YamlMapFactoryBean yamlMapFb = new YamlMapFactoryBean();
            yamlMapFb.setResources(new ClassPathResource(ymlFilePath));
            Map<String,Object> map = yamlMapFb.getObject();
            YamlConverter.doConvert(map,null,envMap);
        }
    }

    public static  void setEnvMap(Map<String,Object> envMap){
        EnvInitializer.envMap = envMap;
    }

    public static Map<String,Object> getEnvMap(){
        return  envMap;
    }
}
