package com.xzh.utils.http;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取请求参数
 *
 * @author 向振华
 * @date 2018/12/10 17:15
 */
public class ResolverUtils {

    /**
     * 获取Json格式请求参数
     *
     * @param request
     * @return
     */
    public static String getRequestJson(HttpServletRequest request) {
        try {
            return getParameters(request);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取Map格式请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            String json = sb.toString();
            br.close();
            String data = java.net.URLDecoder.decode(json, "utf-8");
            if (data.isEmpty()) {
                return null;
            }
            Map<String, String> map = new HashMap<>();
            for (String string : data.split("&")) {
                String[] kv = string.split("=");
                map.put(kv[0], kv[1]);
            }
            return map;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取实体类型请求参数
     *
     * @param request
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getRequestModel(HttpServletRequest request, Class<T> clazz) {
        try {
            String json = getParameters(request);
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getParameters(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        return sb.toString();
    }
}
