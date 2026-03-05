# Kingdee Web API

一个轻量级的Spring Boot Web API项目，专门用于返回JSON数据。

## 项目特性

- 🚀 基于Spring Boot 2.7.14
- 📦 轻量级设计，无需数据库
- 🔄 完整的RESTful API
- 📊 统一的JSON响应格式
- 🌐 支持跨域请求
- 📝 详细的API文档

## 技术栈

- **框架**: Spring Boot 2.7.14
- **Java版本**: Java 8+
- **构建工具**: Maven
- **JSON处理**: Jackson
- **开发工具**: Spring Boot DevTools

## 快速开始

### 环境要求

- JDK 8 或更高版本
- Maven 3.6 或更高版本

### 运行项目

1. 克隆项目到本地
```bash
git clone <repository-url>
cd kingdee_webapi
```

2. 编译项目
```bash
mvn clean compile
```

3. 运行项目
```bash
mvn spring-boot:run
```

4. 访问应用
```
http://192.168.1.14:8091
```

## API接口文档

### 系统接口

#### 健康检查
- **GET** `/api/system/health`
- **描述**: 检查系统运行状态
- **响应示例**:
```json
{
  "code": 200,
  "message": "健康检查成功",
  "data": {
    "status": "UP",
    "message": "系统运行正常",
    "timestamp": 1703123456789
  },
  "timestamp": 1703123456789
}
```

#### 系统信息
- **GET** `/api/system/info`
- **描述**: 获取系统详细信息

#### 版本信息
- **GET** `/api/system/version`
- **描述**: 获取API版本信息

### 用户管理接口

#### 获取所有用户
- **GET** `/api/users`
- **描述**: 获取所有用户列表

#### 根据ID获取用户
- **GET** `/api/users/{id}`
- **描述**: 根据用户ID获取用户信息

#### 创建用户
- **POST** `/api/users`
- **描述**: 创建新用户
- **请求体示例**:
```json
{
  "username": "新用户",
  "email": "newuser@example.com",
  "phone": "13800138004",
  "age": 26,
  "address": "深圳市南山区"
}
```

#### 更新用户
- **PUT** `/api/users/{id}`
- **描述**: 更新指定用户信息

#### 删除用户
- **DELETE** `/api/users/{id}`
- **描述**: 删除指定用户

#### 搜索用户
- **GET** `/api/users/search?username={username}`
- **描述**: 根据用户名搜索用户

#### 用户统计
- **GET** `/api/users/stats`
- **描述**: 获取用户统计信息

### 金蝶数据集接口

#### 科目余额表
- **GET** `/kingdeedataset/getkmyedata`
- **描述**: 获取科目余额表数据
- **响应示例**:
```json
{
  "list": [
    {
      "科目编码": "1001",
      "科目名称": "库存现金",
      "科目类别": "资产",
      "会计要素": "流动资产",
      "币别": "人民币",
      "年初余额-原币（借）": "10000.00",
      "年初余额-本位币（借）": "10000.00",
      "年初余额-原币（贷）": "0.00",
      "年初余额-本位币（贷）": "0.00",
      "期初余额-原币（借）": "10000.00",
      "期初余额-本位币（借）": "10000.00",
      "期初余额-原币（贷）": "0.00",
      "期初余额-本位币（贷）": "0.00",
      "本期发生-原币（借）": "5000.00",
      "本期发生-本位币（借）": "5000.00",
      "本期发生-原币（贷）": "2000.00",
      "本期发生-本位币（贷）": "2000.00",
      "本年累计-原币（借）": "50000.00",
      "本年累计-本位币（借）": "50000.00",
      "本年累计-原币（贷）": "30000.00",
      "本年累计-本位币（贷）": "30000.00",
      "期末余额-原币（借）": "13000.00",
      "期末余额-本位币（借）": "13000.00",
      "期末余额-原币（贷）": "0.00",
      "期末余额-本位币（贷）": "0.00",
      "方向": "借",
      "本期实际损益发生额-原币": "0.00",
      "本期实际损益发生额-本位币": "0.00",
      "本年累计实际损益发生额-原币": "0.00",
      "本年累计实际损益发生额-本位币": "0.00",
      "年份": "2024",
      "月份": "12"
    }
  ]
}
```

## 响应格式

所有API接口都使用统一的响应格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": 1703123456789
}
```

### 响应字段说明

- `code`: 状态码（200成功，其他失败）
- `message`: 响应消息
- `data`: 响应数据
- `timestamp`: 响应时间戳

## 项目结构

```
kingdee_webapi/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/kingdee/webapi/
│   │   │       ├── KingdeeWebApiApplication.java    # 主启动类
│   │   │       ├── common/
│   │   │       │   └── ApiResponse.java             # 统一响应类
│   │   │       ├── controller/
│   │   │       │   ├── UserController.java          # 用户控制器
│   │   │       │   ├── SystemController.java        # 系统控制器
│   │   │       │   └── KingdeeDataSetController.java # 金蝶数据集控制器
│   │   │       ├── model/
│   │   │       │   ├── User.java                    # 用户模型
│   │   │       │   ├── FinancialData.java           # 科目余额表数据模型
│   │   │       │   └── Financial.java               # 科目余额表包装类
│   │   │       └── service/
│   │   │           ├── CacheService.java            # 缓存服务
│   │   │           ├── KingdeeDataSetService.java   # 金蝶数据集服务接口
│   │   │           └── impl/
│   │   │               └── KingdeeDataSetServiceImpl.java # 金蝶数据集服务实现
│   │   └── resources/
│   │       └── application.yml                      # 配置文件
│   └── test/                                        # 测试目录
├── pom.xml                                          # Maven配置
└── README.md                                        # 项目说明
```

## 开发说明

### 添加新的API接口

1. 在`controller`包下创建新的控制器类
2. 使用`@RestController`注解标记控制器
3. 使用`@RequestMapping`定义请求路径
4. 返回`ApiResponse<T>`类型的响应

### 添加新的数据模型

1. 在`model`包下创建新的模型类
2. 使用Jackson注解配置JSON序列化
3. 实现getter/setter方法

## 测试

### 使用curl测试

```bash
# 获取所有用户
curl -X GET http://192.168.1.14:8091/api/users

# 创建用户
curl -X POST http://192.168.1.14:8091/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"测试用户","email":"test@example.com","phone":"13800138005","age":25,"address":"测试地址"}'

# 获取系统信息
curl -X GET http://192.168.1.14:8091/api/system/info

# 获取科目余额表数据
curl -X GET http://192.168.1.14:8091/kingdeedataset/getkmyedata
```

### 使用Postman测试

1. 导入项目中的API集合
2. 设置环境变量
3. 运行测试用例

## 部署

### 打包

```bash
mvn clean package
```

### 运行jar包

```bash
java -jar target/kingdee-webapi-1.0.0.jar
```

## 许可证

MIT License

## 联系方式

如有问题或建议，请联系开发团队。 