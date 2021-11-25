package com.xmu.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext =null;

    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext=applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> type){
        assertContextInjected();
        return applicationContext.getBean(type);
    }

    private static void assertContextInjected(){
        if(applicationContext==null){
            throw new IllegalStateException("application context 未注入");
        }
    }

    private static void clearHolder(){
        applicationContext=null;
    }
}
