package com.tencent.seventeenShow.backend.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liuquan on 2015/5/13 20:40
 */
public class GlobalPropertiesConfigurer extends PropertyPlaceholderConfigurer {
    public static Map<String, String> propertiesMap = new HashMap<String, String>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        for (Map.Entry<Object, Object> objectObjectEntry : props.entrySet()) {
            String key = (String) objectObjectEntry.getKey();
            String value = (String) objectObjectEntry.getValue();
            propertiesMap.put(key, value);
        }
    }


    public static String getProperties(String key) {
        return propertiesMap.get(key);
    }
}
