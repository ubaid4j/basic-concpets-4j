package com.ubaid.forj.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Engine implements InitializingBean, DisposableBean {
    
    static Logger logger = LoggerFactory.getLogger(Engine.class);
    
    public void start() {
        logger.debug("starting engine");
    }

    @Override
    public void destroy() throws Exception {
        
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
