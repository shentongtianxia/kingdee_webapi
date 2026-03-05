package com.kingdee.webapi.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 修复 Springfox 与 Spring Boot HandlerMappings 兼容性问题
 */
@Configuration
public class SwaggerHandlerProviderConfig {

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                // 处理 Springfox 的 HandlerProvider（WebMvcRequestHandlerProvider / WebFluxRequestHandlerProvider）
                if (bean != null && bean.getClass().getName().contains("RequestHandlerProvider")) {
                    Logger logger = LoggerFactory.getLogger(bean.getClass());
                    logger.debug("Processing RequestHandlerProvider bean: {}", beanName);
                    try {
                        Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                        if (field != null) {
                            field.setAccessible(true);
                            Object mappingsObj = field.get(bean);
                            if (mappingsObj instanceof List) {
                                @SuppressWarnings("unchecked")
                                List<Object> mappings = (List<Object>) mappingsObj;
                                int before = mappings.size();
                                StringBuilder removedInfo = new StringBuilder();
                                mappings.removeIf(mapping -> {
                                    try {
                                        // Defensive checks: remove mapping entries that will break Springfox
                                        // 1) If mapping has getPatternParser() and it returns non-null -> incompatible
                                        try {
                                            Method getPatternParserMethod = mapping.getClass().getMethod("getPatternParser");
                                            Object patternParser = getPatternParserMethod.invoke(mapping);
                                            if (patternParser != null) {
                                                removedInfo.append("[patternParser]!=null:").append(mapping.getClass().getSimpleName()).append(";");
                                                return true;
                                            }
                                        } catch (NoSuchMethodException ignored) {
                                            // method not present - fine
                                        }

                                        // 2) If mapping has getPatternsCondition() and it returns null -> will cause NPE in Springfox
                                        try {
                                            Method getPatternsConditionMethod = mapping.getClass().getMethod("getPatternsCondition");
                                            Object patternsCondition = getPatternsConditionMethod.invoke(mapping);
                                            if (patternsCondition == null) {
                                                removedInfo.append("[patternsCondition]==null:").append(mapping.getClass().getSimpleName()).append(";");
                                                return true;
                                            }
                                        } catch (NoSuchMethodException ignored) {
                                            // method not present - fine
                                        }

                                        return false;
                                    } catch (Exception e) {
                                        // On unexpected exception, keep the mapping to avoid removing valid handlers
                                        return false;
                                    }
                                });
                                int after = mappings.size();
                                logger.debug("RequestHandlerProvider '{}': mappings before={}, after={}, removed={}", beanName, before, after, removedInfo.toString());
                            } else {
                                logger.debug("handlerMappings field is not a List for bean {}", beanName);
                            }
                        } else {
                            logger.debug("No handlerMappings field found in bean {}", beanName);
                        }
                    } catch (Exception ex) {
                        // 忽略异常，避免阻塞应用启动
                        LoggerFactory.getLogger(SwaggerHandlerProviderConfig.class).warn("Exception while processing RequestHandlerProvider bean {}", beanName, ex);
                    }
                }
                return bean;
            }
        };
    }
}


