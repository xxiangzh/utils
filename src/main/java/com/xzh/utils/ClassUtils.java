package com.xzh.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author 向振华
 * @date 2018/11/17 13:52
 */
public class ClassUtils {

    /**
     * 获取指定包下面所有的class文件
     * @param packagePath 包路径
     * @return
     */
    public static File[] getResources(String packagePath){
        try {
            URL resource = ClassUtils.class.getClassLoader().getResource(packagePath.replace(".", "/"));
            if (resource == null){
                return null;
            }
            File file = new File(resource.toURI());
            return file.listFiles(pathName -> pathName.getName().endsWith(".class"));
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
