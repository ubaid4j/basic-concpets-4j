package com.ubaid.forj.annotation;

import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringBean implements InitializingBean, DisposableBean, BeanNameAware,
    BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware, BeanFactoryPostProcessor,
    BeanPostProcessor {

    private final String detail;

    public SpringBean() {
        detail = "Most Confidential: " + getClass().getSimpleName();
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.debug("class loader name: {}", classLoader.getName());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.debug("bean factory name: {}", beanFactory.toString());
    }

    @Override
    public void setBeanName(String name) {
        log.debug("bean name: {}", name);
    }

    @Override
    public void destroy() throws Exception {
        log.debug("destroying");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("after properties set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("application context name: {}", applicationContext.getDisplayName());
    }

    @Override
    public String toString() {
        return detail;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {

        log.debug("post process bean factory");
        print(beanFactory.getBeanNamesIterator());

    }

    private void print(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String name = iterator.next();
            log.debug("{}", name);
        }
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
        log.debug("#start-------------postProcessBeforeInitialization--------------#");
        log.debug("Bean: {}", bean);
        log.debug("beanName: {}", beanName);
        log.debug("#-------------postProcessBeforeInitialization--------------end#");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
        log.debug("#start-------------postProcessAfterInitialization--------------#");
        log.debug("Bean: {}", bean);
        log.debug("beanName: {}", beanName);
        log.debug("#-------------postProcessAfterInitialization--------------end#");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    @Autowired
    @Qualifier("name")
    String name;
}
