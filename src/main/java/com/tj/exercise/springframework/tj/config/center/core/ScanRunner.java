package com.tj.exercise.springframework.tj.config.center.core;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @Author: tj
 * @Date: 2022/10/29 22:15
 */
@Component
public class ScanRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
       doScanComponent();
    }

    private void doScanComponent() {
        String rootPath = this.getClass().getResource("/").getPath();
        List<String> fileList = FileScanner.findFileByType(rootPath,null,FileScanner.TYPE_CLASS);
        doFilter(rootPath,fileList);
        EnvInitializer.init();
    }

    private void doFilter(String rootPath, List<String> fileList) {
        rootPath = FileScanner.getRealRootPath(rootPath);
        for(String fullPath : fileList){
            String shortName = fullPath.replace(rootPath,"")
                    .replace(FileScanner.TYPE_CLASS,"");
            String packageFileName = shortName.replaceAll(Matcher.quoteReplacement(File.separator),"\\.");

            try{
                Class clazz = Class.forName(packageFileName);
                if(clazz.isAnnotationPresent(Component.class)
                       || clazz.isAnnotationPresent(Controller.class)
                       || clazz.isAnnotationPresent(Service.class)){
                    VariablePool.add(clazz);
                }
            } catch (ClassNotFoundException e){
                e.printStackTrace();
            }

        }
    }


}
