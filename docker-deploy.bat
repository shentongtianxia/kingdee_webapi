@echo off
echo ========================================
echo Kingdee WebAPI Docker 部署脚本
echo ========================================

echo.
echo 1. 停止并删除现有容器...
docker-compose down

echo.
echo 2. 删除现有镜像...
docker rmi kingdee-webapi:latest 2>nul

echo.
echo 3. 构建新镜像...
docker-compose build --no-cache

echo.
echo 4. 启动容器...
docker-compose up -d

echo.
echo 5. 查看容器状态...
docker-compose ps

echo.
echo 6. 查看日志...
echo 等待应用启动...
timeout /t 10 /nobreak >nul
docker-compose logs --tail=20

echo.
echo ========================================
echo 部署完成！
echo 应用访问地址: http://192.168.1.14:8080
echo 健康检查地址: http://192.168.1.14:8080/actuator/health
echo ========================================
echo.
echo 常用命令:
echo - 查看日志: docker-compose logs -f
echo - 停止服务: docker-compose down
echo - 重启服务: docker-compose restart
echo - 进入容器: docker exec -it kingdee-webapi sh 