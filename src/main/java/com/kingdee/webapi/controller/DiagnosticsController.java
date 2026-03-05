package com.kingdee.webapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 诊断控制器：用于查看当前注册的请求映射，帮助定位 Swagger 未显示接口的问题
 */
@RestController
public class DiagnosticsController {

    @Autowired
    @Qualifier("requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/internal/mappings")
    public List<Object> mappings() {
        List<Object> result = new ArrayList<>();
        Map<RequestMappingInfo, org.springframework.web.method.HandlerMethod> map = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, org.springframework.web.method.HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            org.springframework.web.method.HandlerMethod method = entry.getValue();
            Set<String> patterns = info.getPatternsCondition() != null ? info.getPatternsCondition().getPatterns() : null;
            result.add(new MappingInfo(patterns, info.getMethodsCondition().getMethods().toString(), method.getBeanType().getName() + "#" + method.getMethod().getName()));
        }
        return result;
    }

    static class MappingInfo {
        public Set<String> patterns;
        public String methods;
        public String handler;

        public MappingInfo(Set<String> patterns, String methods, String handler) {
            this.patterns = patterns;
            this.methods = methods;
            this.handler = handler;
        }
    }
}


