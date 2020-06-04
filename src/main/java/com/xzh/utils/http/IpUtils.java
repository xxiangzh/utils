package com.xzh.utils.http;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取真实ip
 *
 * @author 向振华
 * @since 2020-05-09
 */
public class IpUtils {
    
    public static final String UNKNOWN= "unknown";
    public static final String LOCALHOST= "127.0.0.1";
    public static final String LOCAL= "0:0:0:0:0:0:0:1";
    public static final String POINT = ",";

    public static String getIp() {
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null){
            return null;
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 获取IP
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(LOCALHOST.equals(ip) || LOCAL.equals(ip)){
                try {
                    //根据网卡取本机配置的IP
                    ip= InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        //多个代理的情况取第一个为真实IP
        if (ip.contains(POINT)) {
            ip = ip.split(POINT)[0];
        }
        return ip;
    }
}