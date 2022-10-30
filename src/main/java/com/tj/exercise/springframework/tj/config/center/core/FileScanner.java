package com.tj.exercise.springframework.tj.config.center.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @Author: tj
 * @Date: 2022/10/29 0:17
 */
public class FileScanner {

    public static final String TYPE_CLASS = ".class";
    public static final String TYPE_YML =".yml";

    public static List<String> findFileByType(String rootPath, List<String> fileList, String fileType){
        if(fileList == null){
            fileList = new ArrayList<>();
        }


     //   rootPath = FileScanner.getRealRootPath(rootPath);

        File rootFile = new File(rootPath);
        boolean result = rootFile.isDirectory();
        if(!rootFile.isDirectory()) {
            addFile(rootFile.getPath(),fileList,fileType);
        }else{
            String[] subFileList = rootFile.list();
            for(String file : subFileList){
                String subFilePath = rootPath +"\\" + file;
                File subFile = new File(subFilePath);
                if(!subFile.isDirectory()){
                    addFile(subFile.getPath(),fileList,fileType);
                }else{
                    findFileByType(subFilePath,fileList,fileType);
                }
            }
        }
        return fileList;
    }

    private static void addFile(String path, List<String> fileList, String fileType) {
        if(path.endsWith(fileType)){
            fileList.add(path);
        }
    }

    public static String getRealRootPath(String rootPath){
        if(System.getProperty("os.name").startsWith("Windows")
             && rootPath.startsWith("/")){
            rootPath = rootPath.substring(1);
            rootPath = rootPath.replaceAll("/", Matcher.quoteReplacement(File.separator));
        }
        return rootPath;
    }
}
