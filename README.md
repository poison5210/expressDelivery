# Express Delivery Management System (快递管理系统)

一个基于 Spring Boot + Vue.js 的快递管理系统，提供用户管理、快递费用计算等核心功能。

## 系统架构

### 后端技术栈
- **Spring Boot 2.6.13** - 主框架
- **MyBatis** - 数据持久层
- **MySQL** - 数据库
- **Druid** - 数据库连接池
- **Lombok** - 简化代码
- **Commons Codec** - 加密工具

### 前端技术栈
- **Vue.js 2.6.10** - 前端框架
- **Element UI 2.13.2** - UI组件库
- **Vue Router** - 路由管理
- **Vuex** - 状态管理
- **Axios** - HTTP客户端

## 核心功能

### 1. 用户管理模块
- **用户认证**: 基于MD5加密的密码验证，确保账户安全
- **用户注册**: 支持新用户注册，自动生成UUID作为唯一标识
- **角色管理**: 支持多角色权限控制（管理员、普通用户、快递员等）
- **用户查询**: 根据用户ID和角色查询用户信息，支持条件筛选
- **密码管理**: 密码MD5加密存储，支持密码重置功能
- **用户状态**: 支持用户启用/禁用状态管理

#### 支持的用户角色
- **admin**: 系统管理员，拥有所有权限
- **user**: 普通用户，可以寄件和查询
- **courier**: 快递员，负责取件和派送
- **operator**: 操作员，负责订单处理

### 2. 快递费用计算模块
- **智能计费**: 根据实际重量和体积重量取较大值计费
- **体积重量计算**: 采用国际标准长×宽×高÷6000算法
- **阶梯计费**: 首重1kg基础价格，续重按单位递增
- **保价服务**: 按保价金额的1%收取保价费，最低1元
- **距离计费**: 支持按配送距离调整费用（预留功能）
- **时效服务**: 支持标准件、加急件等不同时效选择

#### 详细计费规则
- **首重**: 1kg以内 - 10元
- **续重**: 每增加1kg或不足1kg - 2元
- **体积重量**: 长(cm) × 宽(cm) × 高(cm) ÷ 6000
- **计费重量**: max(实际重量, 体积重量)
- **保价费**: 保价金额 × 1%，最低收费1元
- **燃油附加费**: 根据市场情况动态调整（可配置）

#### 费用计算示例
```
包裹信息：
- 实际重量：2.3kg
- 尺寸：30cm × 20cm × 15cm
- 保价金额：100元

计算过程：
1. 体积重量 = 30 × 20 × 15 ÷ 6000 = 1.5kg
2. 计费重量 = max(2.3kg, 1.5kg) = 2.3kg
3. 运费 = 首重10元 + 续重1.3kg按2个单位计算 = 10 + 4 = 14元
4. 保价费 = 100 × 1% = 1元
5. 总费用 = 14 + 1 = 15元
```

### 3. 系统管理
- **Dashboard**: 系统概览和数据统计，实时监控业务指标
- **表单管理**: 快递单据录入和管理，支持批量操作
- **权限控制**: 基于角色的访问控制，细粒度权限管理
- **系统配置**: 支持计费规则、系统参数等配置管理
- **日志管理**: 完整的操作日志记录和审计功能

### 4. 业务流程管理
- **订单创建**: 支持在线下单，自动生成订单号
- **订单跟踪**: 实时更新订单状态，提供查询接口
- **状态管理**: 订单状态流转（待取件→已取件→运输中→派送中→已签收）
- **异常处理**: 支持订单异常、退件、拒收等特殊情况处理

### 5. 数据统计与报表
- **业务统计**: 订单量、收入、客户等关键指标统计
- **图表展示**: 使用ECharts展示业务数据趋势
- **报表导出**: 支持Excel、PDF格式报表导出
- **实时监控**: Dashboard实时显示系统运行状态

## 数据库设计

### 核心数据表

#### userinfo 表 - 用户信息表
```sql
CREATE TABLE userinfo (
    userid VARCHAR(50) PRIMARY KEY COMMENT '用户ID，UUID格式',
    username VARCHAR(100) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT 'MD5加密密码',
    createtime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    role VARCHAR(20) NOT NULL COMMENT '用户角色：admin/user/courier/operator',
    status TINYINT DEFAULT 1 COMMENT '用户状态：1-启用，0-禁用',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱地址',
    last_login DATETIME COMMENT '最后登录时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';
```

#### express_order 表 - 快递订单表（扩展功能）
```sql
CREATE TABLE express_order (
    order_id VARCHAR(50) PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(30) UNIQUE NOT NULL COMMENT '订单号',
    sender_name VARCHAR(50) NOT NULL COMMENT '寄件人姓名',
    sender_phone VARCHAR(20) NOT NULL COMMENT '寄件人电话',
    sender_address TEXT NOT NULL COMMENT '寄件人地址',
    receiver_name VARCHAR(50) NOT NULL COMMENT '收件人姓名',
    receiver_phone VARCHAR(20) NOT NULL COMMENT '收件人电话',
    receiver_address TEXT NOT NULL COMMENT '收件人地址',
    weight DECIMAL(8,2) NOT NULL COMMENT '实际重量(kg)',
    length INT COMMENT '长度(cm)',
    width INT COMMENT '宽度(cm)',
    height INT COMMENT '高度(cm)',
    volume_weight DECIMAL(8,2) COMMENT '体积重量(kg)',
    charge_weight DECIMAL(8,2) COMMENT '计费重量(kg)',
    insured_amount DECIMAL(10,2) DEFAULT 0 COMMENT '保价金额',
    freight DECIMAL(10,2) NOT NULL COMMENT '运费',
    insurance_fee DECIMAL(10,2) DEFAULT 0 COMMENT '保价费',
    total_fee DECIMAL(10,2) NOT NULL COMMENT '总费用',
    status VARCHAR(20) DEFAULT 'pending' COMMENT '订单状态',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator_id VARCHAR(50) COMMENT '创建人ID',
    remark TEXT COMMENT '备注信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快递订单表';
```

#### order_status_log 表 - 订单状态日志表（扩展功能）
```sql
CREATE TABLE order_status_log (
    log_id VARCHAR(50) PRIMARY KEY COMMENT '日志ID',
    order_id VARCHAR(50) NOT NULL COMMENT '订单ID',
    status VARCHAR(20) NOT NULL COMMENT '状态',
    operator_id VARCHAR(50) COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    location VARCHAR(200) COMMENT '操作地点',
    remark TEXT COMMENT '备注',
    FOREIGN KEY (order_id) REFERENCES express_order(order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单状态日志表';
```

#### system_config 表 - 系统配置表（扩展功能）
```sql
CREATE TABLE system_config (
    config_id VARCHAR(50) PRIMARY KEY COMMENT '配置ID',
    config_key VARCHAR(100) UNIQUE NOT NULL COMMENT '配置键',
    config_value TEXT NOT NULL COMMENT '配置值',
    config_desc VARCHAR(200) COMMENT '配置描述',
    config_type VARCHAR(20) DEFAULT 'string' COMMENT '配置类型：string/number/boolean/json',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';
```

### 初始化数据
```sql
-- 插入默认管理员账户
INSERT INTO userinfo (userid, username, password, role, status) VALUES 
('admin-001', 'admin', MD5('admin123'), 'admin', 1);

-- 插入系统配置
INSERT INTO system_config (config_id, config_key, config_value, config_desc, config_type) VALUES 
('cfg-001', 'first_weight', '1.0', '首重重量(kg)', 'number'),
('cfg-002', 'first_price', '10.0', '首重价格(元)', 'number'),
('cfg-003', 'follow_price', '2.0', '续重价格(元/kg)', 'number'),
('cfg-004', 'volume_coefficient', '6000', '体积系数', 'number'),
('cfg-005', 'insurance_rate', '0.01', '保价费率', 'number'),
('cfg-006', 'min_insurance_fee', '1.0', '最低保价费(元)', 'number');
```

## API接口文档

### 用户管理接口

#### 1. 用户登录验证
```http
GET /user/queryUser
```
**参数说明:**
- `userid` (string, required): 用户ID
- `password` (string, required): 用户密码
- `role` (string, optional): 用户角色筛选

**响应示例:**
```json
{
    "status": true,
    "data": {
        "userid": "admin-001",
        "username": "管理员",
        "role": "admin",
        "createtime": "2024-01-01 10:00:00",
        "status": 1
    }
}
```

#### 2. 用户信息管理
```http
POST /user/mangeUser
```
**请求体:**
```json
{
    "username": "张三",
    "password": "123456",
    "role": "user",
    "phone": "13800138000",
    "email": "zhangsan@example.com"
}
```

**响应示例:**
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {
        "userid": "uuid-generated-id"
    }
}
```

#### 3. 快递费用计算
```http
GET /user/getPrices
```
**参数说明:**
- `weight` (double, required): 实际重量(kg)
- `length` (int, required): 长度(cm)
- `width` (int, required): 宽度(cm)
- `height` (int, required): 高度(cm)
- `insuredAmount` (double, optional): 保价金额，默认0

**响应示例:**
```json
{
    "code": 200,
    "data": {
        "actualWeight": 2.3,
        "volumeWeight": 1.5,
        "chargeWeight": 2.3,
        "freight": 14.0,
        "insuranceFee": 1.0,
        "totalFee": 15.0,
        "calculation": {
            "firstWeightFee": 10.0,
            "followWeightFee": 4.0,
            "followWeightUnits": 2
        }
    }
}
```

### 扩展API接口（规划中）

#### 订单管理接口
```http
POST /order/create          # 创建订单
GET /order/query            # 查询订单
PUT /order/update           # 更新订单
DELETE /order/cancel        # 取消订单
GET /order/track/{orderNo}  # 订单跟踪
```

#### 系统管理接口
```http
GET /system/config          # 获取系统配置
PUT /system/config          # 更新系统配置
GET /system/stats           # 获取系统统计
GET /system/logs            # 获取操作日志
```

### 错误码说明
| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未授权访问 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 项目结构

```
expressDelivery/
├── expressDelivery-back/          # Spring Boot 后端
│   ├── src/main/java/
│   │   └── com/example/expressdeliveryback/
│   │       ├── user/              # 用户模块
│   │       │   ├── controller/    # 控制器
│   │       │   └── servise/       # 服务层
│   │       └── tools/             # 工具类
│   └── src/main/resources/
│       ├── mapper/                # MyBatis映射文件
│       └── application.properties # 配置文件
│
└── expressDelivery-front/         # Vue.js 前端
    ├── src/
    │   ├── api/                   # API接口
    │   ├── components/            # 公共组件
    │   ├── layout/                # 布局组件
    │   ├── router/                # 路由配置
    │   ├── store/                 # Vuex状态管理
    │   ├── utils/                 # 工具函数
    │   └── views/                 # 页面组件
    └── public/                    # 静态资源
```

## 快速开始

### 环境要求
- Java 8+
- Node.js 8.9+
- MySQL 5.7+
- Maven 3.6+

### 后端启动
```bash
cd expressDelivery-back
mvn clean install
mvn spring-boot:run
```
后端服务将在 http://localhost:8080 启动

### 前端启动
```bash
cd expressDelivery-front
npm install
npm run dev
```
前端应用将在 http://localhost:9528 启动

### 数据库配置
1. 创建数据库 `expressdelivery`
2. 修改 `application.properties` 中的数据库连接信息
3. 创建用户表并初始化数据

## 功能特色

### 🔒 安全性
- **密码安全**: MD5密码加密存储，防止明文泄露
- **权限控制**: 基于角色的细粒度权限管理
- **SQL安全**: 使用MyBatis参数化查询，防止SQL注入
- **会话管理**: 支持用户会话超时和单点登录控制
- **操作审计**: 完整的用户操作日志记录

### 🎨 用户体验
- **响应式设计**: 支持PC、平板、手机等多设备访问
- **现代化UI**: Element UI提供优雅的界面体验
- **实时反馈**: 费用计算、表单验证等实时响应
- **国际化**: 支持中英文界面切换（规划中）
- **主题定制**: 支持深色/浅色主题切换

### ⚡ 性能优化
- **数据库连接池**: 使用Druid连接池，提高数据库访问效率
- **前端优化**: Vue.js组件化开发，支持懒加载
- **缓存机制**: 支持Redis缓存热点数据（规划中）
- **CDN加速**: 静态资源CDN分发（规划中）

### 🔧 扩展性
- **模块化架构**: 清晰的分层架构，易于维护和扩展
- **配置化管理**: 计费规则、系统参数等支持动态配置
- **插件机制**: 支持第三方快递公司API集成
- **微服务就绪**: 架构设计支持微服务拆分
- **多数据库支持**: 支持MySQL、PostgreSQL等多种数据库

### 📊 业务智能
- **数据分析**: 提供丰富的业务数据统计和分析
- **可视化图表**: 使用ECharts展示业务趋势
- **报表系统**: 支持多种格式的报表导出
- **预警机制**: 异常订单、系统故障等智能预警
- **决策支持**: 为业务决策提供数据支撑

### 🌐 集成能力
- **第三方API**: 支持顺丰、圆通、中通等快递公司API
- **支付集成**: 支持支付宝、微信支付等在线支付
- **短信通知**: 集成短信服务，订单状态实时通知
- **地图服务**: 集成高德、百度地图，提供路径规划
- **电子面单**: 支持电子面单打印和管理

## 开发路线图

### 🚀 第一阶段 - 核心功能完善 (v1.0)
- [x] 用户管理系统
- [x] 费用计算引擎
- [x] 基础权限控制
- [ ] 订单管理模块
- [ ] 订单状态跟踪
- [ ] 基础报表功能

### 📈 第二阶段 - 业务扩展 (v1.5)
- [ ] 快递单据管理
- [ ] 批量订单处理
- [ ] 客户管理系统
- [ ] 财务结算模块
- [ ] 库存管理功能
- [ ] 移动端H5页面

### 🔗 第三阶段 - 系统集成 (v2.0)
- [ ] 第三方快递API集成
- [ ] 电子面单打印
- [ ] 短信/邮件通知
- [ ] 在线支付集成
- [ ] 地图路径规划
- [ ] 实时物流跟踪

### 🎯 第四阶段 - 智能化升级 (v2.5)
- [ ] 智能路径优化
- [ ] 价格预测算法
- [ ] 客户行为分析
- [ ] 异常订单预警
- [ ] 自动化客服机器人
- [ ] 数据挖掘与分析

### 🌟 第五阶段 - 生态建设 (v3.0)
- [ ] 开放API平台
- [ ] 第三方应用市场
- [ ] 多租户SaaS模式
- [ ] 国际化支持
- [ ] 区块链溯源
- [ ] AI智能客服

## 技术演进计划

### 架构升级
- **微服务化**: 逐步拆分为用户服务、订单服务、计费服务等
- **容器化部署**: 支持Docker和Kubernetes部署
- **服务网格**: 引入Istio进行服务治理
- **事件驱动**: 采用消息队列实现异步处理

### 技术栈升级
- **后端升级**: Spring Boot 3.x + Java 17
- **前端升级**: Vue 3.x + TypeScript + Vite
- **数据库**: 引入Redis缓存、MongoDB文档存储
- **搜索引擎**: 集成Elasticsearch提供全文搜索

### 性能优化
- **缓存策略**: 多级缓存架构设计
- **数据库优化**: 读写分离、分库分表
- **CDN加速**: 静态资源全球分发
- **负载均衡**: 支持高并发访问

## 贡献指南

### 开发环境搭建
1. Fork 项目到个人仓库
2. 克隆项目到本地
3. 安装开发依赖
4. 配置开发环境
5. 运行测试确保环境正常

### 代码规范
- **Java**: 遵循阿里巴巴Java开发规范
- **JavaScript**: 使用ESLint + Prettier格式化
- **Git**: 使用Conventional Commits规范
- **文档**: 使用Markdown编写技术文档

### 提交流程
1. 创建功能分支
2. 编写代码和测试
3. 提交代码并推送
4. 创建Pull Request
5. 代码审查和合并

## 社区支持

### 获取帮助
- 📖 [在线文档](https://docs.example.com)
- 💬 [社区论坛](https://forum.example.com)
- 🐛 [问题反馈](https://github.com/example/issues)
- 📧 [邮件支持](mailto:support@example.com)

### 参与贡献
- 🌟 给项目点星支持
- 🐛 报告Bug和问题
- 💡 提出新功能建议
- 📝 完善项目文档
- 🔧 提交代码贡献

## 部署指南

### 开发环境部署

#### 1. 环境准备
```bash
# 检查Java版本
java -version  # 需要Java 8+

# 检查Node.js版本
node -v        # 需要Node.js 8.9+

# 检查Maven版本
mvn -version   # 需要Maven 3.6+

# 检查MySQL版本
mysql --version # 需要MySQL 5.7+
```

#### 2. 数据库初始化
```sql
-- 创建数据库
CREATE DATABASE expressdelivery CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户并授权
CREATE USER 'express_user'@'localhost' IDENTIFIED BY 'express_password';
GRANT ALL PRIVILEGES ON expressdelivery.* TO 'express_user'@'localhost';
FLUSH PRIVILEGES;

-- 导入初始化脚本
USE expressdelivery;
SOURCE /path/to/init.sql;
```

#### 3. 后端部署
```bash
cd expressDelivery-back

# 修改配置文件
vim src/main/resources/application.properties

# 编译打包
mvn clean package -DskipTests

# 运行应用
java -jar target/expressDelivery-back-0.0.1-SNAPSHOT.jar

# 或者使用Maven直接运行
mvn spring-boot:run
```

#### 4. 前端部署
```bash
cd expressDelivery-front

# 安装依赖
npm install

# 开发模式运行
npm run dev

# 生产环境构建
npm run build:prod
```

### 生产环境部署

#### 1. Docker部署
```dockerfile
# Dockerfile for backend
FROM openjdk:8-jre-alpine
COPY target/expressDelivery-back-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```dockerfile
# Dockerfile for frontend
FROM node:14-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build:prod

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
```

#### 2. Docker Compose部署
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: expressdelivery
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  backend:
    build: ./expressDelivery-back
    ports:
      - "8080:8080"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/expressdelivery

  frontend:
    build: ./expressDelivery-front
    ports:
      - "80:80"
    depends_on:
      - backend

volumes:
  mysql_data:
```

#### 3. Kubernetes部署
```yaml
# k8s-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: express-delivery-backend
spec:
  replicas: 3
  selector:
    matchLabels:
      app: express-delivery-backend
  template:
    metadata:
      labels:
        app: express-delivery-backend
    spec:
      containers:
      - name: backend
        image: express-delivery-backend:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
```

## 监控与运维

### 应用监控
- **Spring Boot Actuator**: 应用健康检查和指标监控
- **Micrometer**: 应用指标收集
- **Prometheus**: 指标存储和查询
- **Grafana**: 可视化监控面板

### 日志管理
- **Logback**: 应用日志框架
- **ELK Stack**: 日志收集、存储和分析
- **日志级别**: 支持动态调整日志级别
- **日志轮转**: 自动日志文件轮转和清理

### 性能调优
- **JVM调优**: 堆内存、垃圾回收器优化
- **数据库调优**: 索引优化、查询优化
- **缓存策略**: Redis缓存热点数据
- **连接池**: 数据库连接池参数调优

---

⭐ **如果这个项目对你有帮助，请给一个Star！** ⭐

![GitHub stars](https://img.shields.io/github/stars/example/expressdelivery?style=social)
![GitHub forks](https://img.shields.io/github/forks/example/expressdelivery?style=social)
![GitHub issues](https://img.shields.io/github/issues/example/expressdelivery)
![GitHub license](https://img.shields.io/github/license/example/expressdelivery)
