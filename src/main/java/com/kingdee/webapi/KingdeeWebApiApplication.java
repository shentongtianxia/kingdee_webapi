package com.kingdee.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Spring Boot 主启动类
 * 
 * @author Kingdee
 * @version 1.0.0
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class KingdeeWebApiApplication {

    public static void main(String[] args) {
        // 设置TLS协议版本，允许TLS 1.0
        System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,TLSv1.3");
        
        SpringApplication.run(KingdeeWebApiApplication.class, args);
        System.out.println("=== Kingdee Web API 启动成功 ===");
        System.out.println("=== 访问地址: http://192.168.1.14:8091 ===");
        System.out.println("Knife4j文档：http://192.168.1.14:8091/doc.html");
    }
} 