# BookShelfDemo：JdbcTemplate 基础练习题

## 1. 项目目标

使用 **Spring Boot + JdbcTemplate + MySQL** 完成一个简单的图书管理练习项目。

当前阶段的目标不是做完整 Web 项目，而是专注练习：

- 数据库表设计
- `JdbcTemplate` 的基础用法
- SQL 增删改查
- `RowMapper` 结果映射
- 在单元测试中验证 DAO 方法

因此，本项目**不需要 controller 和 service 层**，只保留：

- `entity`
- `dao`
- 测试类

---

## 2. 你要完成的功能

项目包含两部分：

### 2.1 分类管理 `category`

需要完成：

- 新增分类
- 根据 id 查询分类
- 查询全部分类
- 修改分类
- 删除分类

### 2.2 图书管理 `book`

需要完成：

- 新增图书
- 根据 id 查询图书
- 查询全部图书
- 修改图书
- 删除图书

---

## 3. 数据库设计

数据库名建议：

```sql
jdbc_template_demo
```

### 3.1 分类表 `category`

```sql
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 3.2 图书表 `book`

```sql
CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    category_id BIGINT NOT NULL,
    status TINYINT NOT NULL DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 3.3 字段说明

#### `category`

- `id`：主键，自增
- `name`：分类名，唯一
- `description`：分类描述
- `created_at`：创建时间，数据库自动生成

#### `book`

- `id`：主键，自增
- `title`：书名
- `author`：作者
- `price`：价格
- `stock`：库存
- `category_id`：所属分类 id
- `status`：状态，建议 `1=上架`，`0=下架`
- `created_at`：创建时间，数据库自动生成

> 当前阶段可以先**不加外键约束**，避免练习初期因为约束问题频繁报错。

---

## 4. 初始化测试数据

```sql
INSERT INTO category(name, description) VALUES
('Java', 'Java相关图书'),
('Database', '数据库相关图书'),
('Spring', 'Spring框架相关图书');

INSERT INTO book(title, author, price, stock, category_id, status) VALUES
('Java核心技术', 'Cay', 99.00, 10, 1, 1),
('MySQL必知必会', 'Ben Forta', 58.00, 20, 2, 1),
('Spring实战', 'Craig Walls', 88.00, 15, 3, 1);
```

---

## 5. 项目结构

当前阶段使用精简版结构：

```text
com.example.bookshelfdemo
├─ entity
│  ├─ Category
│  └─ Book
├─ dao
│  ├─ CategoryDao
│  └─ BookDao
└─ BookShelfDemoApplication
```

测试代码：

```text
src/test/java
    com.example.bookshelfdemo
        JdbcTemplateDemoApplicationTests.java
    
```

---

## 6. 依赖

如果是 Maven 项目，建议依赖如下：

```xml
<dependencies>
    <!-- Web依赖可选；如果完全不写接口，可以先不加 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- JdbcTemplate -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <!-- MySQL驱动 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- 测试 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Lombok，可选 -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

> 如果你现在完全不写 Web 接口，也可以先去掉 `spring-boot-starter-web`。

---

## 7. application.properties 配置

```properties
spring.application.name=bookshelfdemo

spring.datasource.url=jdbc:mysql://localhost:3306/jdbc_template_demo?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=你的数据库密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

调试时可以额外打开 JDBC 日志：

```properties
logging.level.org.springframework.jdbc.core=DEBUG
logging.level.org.springframework.jdbc.core.StatementCreatorUtils=TRACE
```

---

## 8. 实体类设计建议

### 8.1 `Category`

字段建议：

- `Long id`
- `String name`
- `String description`
- `LocalDateTime createdAt`

### 8.2 `Book`

字段建议：

- `Long id`
- `String title`
- `String author`
- `BigDecimal price`
- `Integer stock`
- `Long categoryId`
- `Integer status`
- `LocalDateTime createdAt`

### 8.3 构造函数建议

对于 `Book`，建议至少保留三种：

1. 无参构造函数
2. 不包含 `id` 和 `createdAt` 的构造函数（用于插入前对象）
3. 全参构造函数（用于数据库查询后的完整对象）

例如：

```java
public Book() {
}

public Book(String title, String author, BigDecimal price,
            Integer stock, Long categoryId, Integer status) {
    this.title = title;
    this.author = author;
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.status = status;
}

public Book(Long id, String title, String author, BigDecimal price,
            Integer stock, Long categoryId, Integer status, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.price = price;
    this.stock = stock;
    this.categoryId = categoryId;
    this.status = status;
    this.createdAt = createdAt;
}
```

> `id` 和 `createdAt` 是数据库生成的，因此插入前不必要求传入。

---

## 9. DAO 层需要完成的方法

### 9.1 `CategoryDao`

建议函数名：

- `insert`
- `findById`
- `findAll`
- `update`
- `deleteById`

### 9.2 `BookDao`

建议函数名：

- `insert`
- `findById`
- `findAll`
- `update`
- `deleteById`

> 这里使用 `insert` 而不是 `save`，更符合你当前阶段对 JDBC 基础的理解。

---

## 10. JdbcTemplate 练习重点

你需要重点练习这些 API：

### 10.1 `update(...)`

用于执行：

- `insert`
- `update`
- `delete`

### 10.2 `queryForObject(...)`

用于查询单条数据。

### 10.3 `query(...)`

用于查询多条数据。

### 10.4 `RowMapper`

用于把结果集映射为 Java 对象。

---

## 11. 插入 SQL 的注意事项

不要这样写：

```java
String sql = "insert into book values(?,?,?,?,?,?,?)";
```

原因：

1. 容易和表字段顺序耦合
2. `id` 是自增字段
3. `created_at` 是默认时间字段
4. 占位符数量和参数数量很容易不一致

推荐写法：

```java
String sql = "insert into book(title, author, price, stock, category_id, status) values(?,?,?,?,?,?)";
```

要记住这个检查原则：

- 列名数 = `?` 个数
- `?` 个数 = Java 参数个数

三者必须一致。

---

## 12. insert 方法返回什么

插入时会遇到一个问题：

传入的是一个“待插入的 `Book` 对象”，但数据库会自动生成：

- `id`
- `created_at`

所以有三种常见设计：

### 12.1 返回受影响行数

```java
public int insert(Book book)
```

适合最基础阶段。

### 12.2 返回自增主键

```java
public Long insert(Book book)
```

适合当前学习阶段，比较推荐。

### 12.3 插入后再查询完整对象

```java
public Book insertAndReturn(Book book)
```

一般做法是：

1. 先插入
2. 拿到主键
3. 再根据 id 查询一次

---

## 13. 推荐开发顺序

建议你按这个顺序做：

1. 建库建表
2. 写 `application.properties`
3. 写 `Category` 和 `Book` 实体类
4. 写 `CategoryDao`
5. 写 `CategoryDaoTest`
6. 写 `BookDao`
7. 写 `BookDaoTest`

这样更适合当前阶段，不会被太多层次干扰。

---

## 14. 单元测试要求

每写完一个 DAO 方法，都立刻写测试验证。

### 14.1 `CategoryDaoTest`

至少包含：

- 插入分类
- 根据 id 查询分类
- 查询全部分类
- 修改分类
- 删除分类

### 14.2 `BookDaoTest`

至少包含：

- 插入图书
- 根据 id 查询图书
- 查询全部图书
- 修改图书
- 删除图书

---

## 15. 训练重点总结

做这个项目时，重点不要放在“页面”或“接口”，而要放在：

1. SQL 自己写
2. 结果集映射自己写
3. 熟悉 `queryForObject` 和 `query` 的区别
4. 理解 `update(...)` 返回值的意义
5. 通过单元测试验证 DAO 是否正确

---

## 16. 这套设计为什么适合之后继续学

这个项目虽然当前只练习基础 CRUD，但表结构和思路可以继续扩展到后面的学习：

- 多表查询
- 连表查询
- 条件查询
- 分页查询
- 事务
- 借阅功能
- 库存扣减
- `@Transactional`

所以它不是一次性练习题，而是一个可以不断升级的练习底座。

---

## 17. 最终题目表述

使用 **Spring Boot + JdbcTemplate + MySQL** 实现一个简易图书管理数据库练习项目。系统包含 `category` 和 `book` 两张表。要求编写对应的实体类和 DAO 类，完成基础增删改查，并通过单元测试验证每个 DAO 方法的正确性。当前阶段不需要编写 controller 和 service，只专注于数据库访问层和测试代码。
