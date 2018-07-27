package com.example.demo.aop;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.Page;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Aspect : 标记为切面类
 * @Pointcut : 指定匹配切点集合
 * @Before : 指定前置通知，value中指定切入点匹配
 * @AfterReturning ：后置通知，具有可以指定返回值
 * @AfterThrowing ：异常通知
 * @Around 环绕通知 环绕通知的方法中一定要有ProceedingJoinPoint 参数,与Filter中的  doFilter方法类似
 * 注意：前置/后置/异常通知的函数都没有返回值，只有环绕通知有返回值
 * Created by user on 2018/6/2.
 * @author
 */
//@Aspect
@Configuration
public class InfoInterceptor {

    private static Logger logger = LoggerFactory.getLogger(InfoInterceptor.class);

    //controller包下任意方法
    private static final String execution_str_01
            = "execution(* com.example.demo.controller.*.*(..))";
    //controller包或子包下任意方法
    private static final String execution_str_02
            = "execution(* com.example.demo.controller..*.*(..))";
    //带RequestMapping注解的方法
    private static final String execution_str_03
            = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";
    //controller包或子包下任意方法
    private static final String execution_str_04
            = "execution(* com.example.demo.controller..*(..))";

    @Autowired
    private UserService userService;

    @Pointcut(execution_str_04)
    private void controllerCut() {
    }

    /**
     * 第二个调用，在doBasicProfiling中调用
     * @param joinPoint
     */
    @Before(value = "controllerCut()")
    public void cutBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        System.out.println("AOP==>" + request.getRequestURL());
        System.out.println("AOP==>" + joinPoint.getSignature());

        String beanName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String uri = request.getRequestURI();
        String remoteAddr = getIpAddr(request);
        String sessionId = request.getSession().getId();
        String user = (String) request.getSession().getAttribute("user");
        String method = request.getMethod();
        String params = "";
        if ("POST".equals(method)) {
            Object[] paramsArray = joinPoint.getArgs();
            params = argsArrayToString(paramsArray);
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            params = paramsMap.toString();
        }
        logger.debug("uri=" + uri + "; beanName=" + beanName + "; remoteAddr=" + remoteAddr + "; user=" + user
                + "; methodName=" + methodName + "; params=" + params);
//        OperatorLog optLog = new OperatorLog();
//        optLog.setBeanName(beanName);
//        optLog.setCurUser(user);
//        optLog.setMethodName(methodName);
//        optLog.setParams(params != null ? params.toString() : "");
//        optLog.setRemoteAddr(remoteAddr);
//        optLog.setSessionId(sessionId);
//        optLog.setUri(uri);
//        optLog.setRequestTime(beginTime);
//        tlocal.set(optLog);
        Page<User> users = userService.getUserInfo(2, 2);

    }

    @AfterReturning(value="controllerCut()",returning="result")
    public void doAfter(JoinPoint jp, String result) {
        System.out.println("后置通知");
        Page<User> users = userService.getUserInfo(2, 2);
    }

    /**
     * 第三个调用
     */
    @After("controllerCut()")
    public void after() {
        System.out.println("最终通知");
        Page<User> users = userService.getUserInfo(2, 2);
    }

    @AfterThrowing(value=execution_str_02,throwing="e")
    public void doAfterThrow(JoinPoint joinPoint, Throwable e) {
        System.out.println("异常通知");
        Page<User> users = userService.getUserInfo(2, 2);
    }

    /**
     * 第一个调用
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(execution_str_03)
    public Object doBasicProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("进入环绕通知");
        System.out.println("目标类名称："+joinPoint.getTarget().getClass().getName());
        System.out.println("方法名称："+joinPoint.getSignature().getName());
        System.out.println("方法参数："+joinPoint.getArgs());

        //保存所有请求参数，用于输出到日志中
        Object result = null;
        Set<Object> allParams = new LinkedHashSet<>();
        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            if (arg instanceof Map<?, ?>) {
                Map<String, Object> map = (Map<String, Object>) arg;
                allParams.add(map);
            }else if(arg instanceof HttpServletRequest){
                HttpServletRequest request = (HttpServletRequest) arg;
//                if(isLoginRequired(method)){
//                    if(!isLogin(request)){
//                        result = new JsonResult(ResultCode.NOT_LOGIN, "该操作需要登录！去登录吗？\n\n（不知道登录账号？请联系老许。）", null);
//                    }
//                }
                //获取query string 或 posted form data参数
                Map<String, String[]> paramMap = request.getParameterMap();
                if(paramMap!=null && paramMap.size()>0){
                    allParams.add(paramMap);
                }
            }else if(arg instanceof HttpServletResponse){

            }else {

            }
        }

        System.out.println("staticPart:"+ joinPoint.getStaticPart().toShortString());
        System.out.println("kind:"+joinPoint.getKind());
        System.out.println("sourceLocation:"+joinPoint.getSourceLocation());
        // 执行该方法
        Object object = joinPoint.proceed();
        System.out.println("退出方法");
        Page<User> users = userService.getUserInfo(2, 2);

        return object;
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

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < 1; i++) {
                Object jsonObj = JSON.toJSON(paramsArray[i]);
                params += jsonObj.toString() + " ";
            }
        }
        return params.trim();
    }
}
