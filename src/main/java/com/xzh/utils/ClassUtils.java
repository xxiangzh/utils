package com.xzh.utils;

import java.beans.PropertyDescriptor;
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
     * @param object
     * @param <T>
     * @return
     */
    public <T> Map toMap(T object) {
        Map map = new HashMap();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            Object value = getValue(object, name);
            map.put(name, value);
        }
        return map;
    }

    /**
     * 根据字段名设置属性
     * @param object
     * @param name
     * @param value
     */
    public void setValue(Object object, String name, Object value){
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, object.getClass());
            Method method = pd.getWriteMethod();
            method.invoke(object, value);
        } catch (Exception e) {
        }
    }

    /**
     * 根据字段名获取属性
     * @param object
     * @param name
     * @return
     */
    public Object getValue(Object object, String name){
        try {
            PropertyDescriptor pd = new PropertyDescriptor(name, object.getClass());
            Method method = pd.getReadMethod();
            Object value = method.invoke(object);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据字段名获取属性
     * @param object
     * @param name
     * @return
     */
    public Object getValueByName(Object object, String name){
        String firstLetter = name.substring(0,1).toUpperCase();
        String getter = "get"+firstLetter+name.substring(1);
        try {
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
