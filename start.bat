@echo off
echo ========================================
echo    Kingdee Web API 启动脚本
echo ========================================
echo.

echo 正在启动应用程序...
echo.

set MAVEN_OPTS=-Djdk.tls.client.protocols=TLSv1,TLSv1.1,TLSv1.2,TLSv1.3
mvn spring-boot:run

pause 