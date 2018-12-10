package com.xzh.http;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 向振华
 * @date 2018/12/10 17:15
 */
public class ResolverUtils {

    public static Map<String, String> getRequestMap(HttpServletRequest request) throws Exception{
        Map<String, String> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        StringBuffer sb = new StringBuffer();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        String data = java.net.URLDecoder.decode(sb.toString(), "utf-8");
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
}
