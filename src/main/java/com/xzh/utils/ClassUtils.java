package com.xzh.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 将实体转Map
     * @param obj
     * @param <T>
     * @return
     */
    public <T> Map toMap(T obj) {
        Map map = new HashMap();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            String value = getValueByName(name, obj);
            map.put(name, value);
        }
        return map;
    }

    /**
     * 根据字段名获取属性
     * @param name
     * @param obj
     * @return
     */
    public String getValueByName(String name, Object obj){
        String firstLetter = name.substring(0,1).toUpperCase();
        String getter = "get"+firstLetter+name.substring(1);
        try {
            Method method = obj.getClass().getMethod(getter);
            Object value = method.invoke(obj);
            return String.valueOf(value).trim();
        } catch (Exception e) {
            return "";
        }
    }
}
