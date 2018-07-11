package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther:biexiande@hisense.com
 * @date:2017/6/30 16:27
 * @des
 */
public class IpUtil {

    /**
     * 获取客户端ip
     * @param  request
     * @return String
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}