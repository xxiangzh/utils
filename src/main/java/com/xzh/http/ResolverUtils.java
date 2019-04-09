package com.xzh.http;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 向振华
 * @date 2018/12/10 17:15
 */
public class ResolverUtils {

    public static String getRequestJson(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        String json = sb.toString();
        return json;
    }

    public static Map<String, String> getRequestMap(HttpServletRequest request) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        String json = sb.toString();
        br.close();
        String data = java.net.URLDecoder.decode(json, "utf-8");
        Map<String, String> map = new HashMap<>();
        if (data.isEmpty()) {
            return map;
        }
        String[] split = data.split("&");
        for (String string : split) {
            String[] kv = string.split("=");
            map.put(kv[0], kv[1]);
        }
        return map;
    }

    public static <T> T getRequestModel(HttpServletRequest request, Class<T> clazz) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        String json = sb.toString();
        return JSON.parseObject(json, clazz);
    }
}
