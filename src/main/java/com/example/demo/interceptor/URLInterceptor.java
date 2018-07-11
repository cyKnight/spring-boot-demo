package com.example.demo.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by user on 2018/6/2.
 */
public class URLInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(URLInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {

        String ip = getIpAddr(httpServletRequest);
        // 获取可以访问系统的白名单
        String ipStr = "10.13.13.13";
        String[] ipArr = ipStr.split("\\|");
        List<String> ipList = java.util.Arrays.asList(ipArr);

        if (ipList.contains(ip)) {
            logger.info("the request ip is : " + ip);
            return true;
        } else {
            logger.error(ip + " is not contains ipWhiteList .......");
            //向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8
            httpServletResponse.setHeader("Content-type","text/html;charset=UTF-8");
            String data = "您好，ip为" + ip + ",暂时没有访问权限，请联系管理员开通访问权限。";
            OutputStream stream = httpServletResponse.getOutputStream();
            stream.write(data.getBytes("UTF-8"));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }

    /**
     * 获取访问的ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
