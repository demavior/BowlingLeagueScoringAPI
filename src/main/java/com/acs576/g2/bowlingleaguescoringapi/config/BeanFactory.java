package com.acs576.g2.bowlingleaguescoringapi.config;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;

@Configuration
public class BeanFactory {

    @Bean
    public BeanUtilsBean beanUtils() {
        return new BeanUtilsBean();
    }

    @Configuration
    class NullAwareBeanUtilsBean extends BeanUtilsBean {

        @Override
        public void copyProperty(Object dest, String name, Object value) throws IllegalAccessException, InvocationTargetException {
            if (value != null) { // Checks if the value is not null
                super.copyProperty(dest, name, value);
            }
        }
    }
}
