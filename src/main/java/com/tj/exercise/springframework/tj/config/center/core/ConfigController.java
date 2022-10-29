package com.tj.exercise.springframework.tj.config.center.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: tj
 * @Date: 2022/10/29 22:24
 */
@RestController
@RequestMapping("config")
public class ConfigController {
    @PostMapping("save")
    public String getUser(@RequestBody Map<String,Object> newValue){
        String ymlContent = (String) newValue.get("yml");
        PropertyTrigger.change(ymlContent);
        return "successs";
    }

    @GetMapping("get")
    public String get(){
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        String yamlContent = null;
        try{
            Map<String,Object> envMap = EnvInitializer.getEnvMap();
            Map<String,Object> map = YamlConverter.monoToMultiLayer(envMap,null);
            yamlContent = objectMapper.writeValueAsString(map);
            System.out.println(yamlContent);
        } catch (Exception e){
            e.printStackTrace();
        }
        return yamlContent;
    }
}
