package com.kingdee.webapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * Diagnostic runner that logs request mappings and Springfox-related beans at startup.
 * This file is safe to keep in the project as a temporary diagnostic aid.
 */
@Component
public class SwaggerDiagnosticRunner implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SwaggerDiagnosticRunner.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            logger.info("=== SwaggerDiagnosticRunner START ===");

            // 1) Log Spring MVC handler mappings
            try {
                RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
                Map<RequestMappingInfo, org.springframework.web.method.HandlerMethod> map = mapping.getHandlerMethods();
                logger.info("RequestMappingHandlerMapping: total handlers = {}", map.size());
                for (Map.Entry<RequestMappingInfo, org.springframework.web.method.HandlerMethod> entry : map.entrySet()) {
                    RequestMappingInfo info = entry.getKey();
                    org.springframework.web.method.HandlerMethod method = entry.getValue();
                    Set<String> patterns = info.getPatternsCondition() != null ? info.getPatternsCondition().getPatterns() : null;
                    logger.info("  patterns={} methods={} handler={}", patterns, info.getMethodsCondition().getMethods(), method.getBeanType().getName() + "#" + method.getMethod().getName());
                }
            } catch (Exception e) {
                logger.warn("Failed to log RequestMappingHandlerMapping handlers", e);
            }

            // 2) Log beans whose class name contains RequestHandlerProvider (Springfox uses these)
            String[] beanNames = applicationContext.getBeanDefinitionNames();
            int foundProviders = 0;
            for (String beanName : beanNames) {
                try {
                    Object bean = applicationContext.getBean(beanName);
                    String className = bean.getClass().getName();
                    if (className.contains("RequestHandlerProvider")) {
                        foundProviders++;
                        logger.info("Found RequestHandlerProvider bean: name='{}' class='{}'", beanName, className);
                        // Try to call requestHandlers() if present
                        try {
                            Method requestHandlersMethod = bean.getClass().getMethod("requestHandlers");
                            Object handlers = requestHandlersMethod.invoke(bean);
                            logger.info("  requestHandlers() returned: {}", handlers == null ? "null" : handlers.getClass().getName());
                        } catch (NoSuchMethodException ignored) {
                            // Try to inspect handlerMappings field
                            try {
                                Field hmField = bean.getClass().getDeclaredField("handlerMappings");
                                hmField.setAccessible(true);
                                Object hm = hmField.get(bean);
                                logger.info("  handlerMappings field type: {}, value: {}", hm == null ? "null" : hm.getClass().getName(), hm);
                            } catch (NoSuchFieldException | IllegalAccessException ignored2) {
                                logger.info("  No requestHandlers() method or handlerMappings field available on bean {}", beanName);
                            }
                        } catch (Exception ex) {
                            logger.warn("  Exception while inspecting RequestHandlerProvider bean {}", beanName, ex);
                        }
                    }
                } catch (Exception ignore) {
                    // ignore failures to resolve particular beans
                }
            }
            logger.info("Total RequestHandlerProvider-like beans found: {}", foundProviders);

            // 3) Log whether documentationPluginsBootstrapper exists
            boolean hasDocBootstrapper = applicationContext.containsBean("documentationPluginsBootstrapper");
            logger.info("documentationPluginsBootstrapper bean present: {}", hasDocBootstrapper);

            logger.info("=== SwaggerDiagnosticRunner END ===");
        } catch (Throwable t) {
            logger.warn("Unexpected error in SwaggerDiagnosticRunner", t);
        }
    }
}


