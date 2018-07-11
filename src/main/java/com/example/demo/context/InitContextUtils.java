package com.example.demo.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by cy on 2017/8/11.
 */

public class InitContextUtils implements InitializingBean,ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("InitContextUtils.setApplicationContext is run");
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
