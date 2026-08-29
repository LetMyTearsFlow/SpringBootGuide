# Spring Boot 数据校验练习

本模块用于练习 Spring Boot MVC 中的 Bean Validation（常称 JSR 303 数据校验）。代码包含一套可运行的最小示例和两条对照链路：控制器直接读取 `BindingResult`，以及由全局异常处理器统一处理校验异常。

## 1. 请求处理链路

```text
Postman JSON
    -> @RequestBody 把 JSON 转成 UserCreateRequest
    -> @Valid 触发字段约束和自定义约束
    -> 校验成功：进入控制器业务代码
    -> 校验失败：
         方式一：BindingResult 在控制器内收集错误
         方式二：抛出 MethodArgumentNotValidException，由 @RestControllerAdvice 处理
```

## 2. 主要知识点

### 2.1 JSR 303 与 Bean Validation

JSR 303 是 Java Bean Validation 的早期规范编号。现代 Spring Boot 使用 `jakarta.validation.*` 包，具体校验工作通常由 Hibernate Validator 完成。

常用注解：

| 注解 | 适用类型 | 作用 |
| --- | --- | --- |
| `@NotNull` | 任意引用类型 | 不能为 `null`，但字符串可以是空串 |
| `@NotEmpty` | 字符串、集合、数组、Map | 不能为 `null` 且长度不能为 0 |
| `@NotBlank` | 字符串 | 不能为 `null`，去除首尾空白后长度不能为 0 |
| `@Size(min, max)` | 字符串、集合、数组、Map | 限制长度或元素数量 |
| `@Min` / `@Max` | 数字 | 限制数值下界或上界 |
| `@Positive` / `@Negative` | 数字 | 必须为正数或负数 |
| `@Email` | 字符串 | 校验常见邮箱格式 |
| `@Pattern(regexp)` | 字符串 | 按正则表达式校验 |
| `@Past` / `@Future` | 日期时间 | 必须早于或晚于当前时间 |

`spring-boot-starter-validation` 提供校验 API 和默认实现。Spring Boot 3/4 使用 `jakarta.validation`，旧项目中常见的 `javax.validation` 不应混用。

### 2.2 `@Valid`

把 `@Valid` 放在控制器参数上，用于触发对象字段的约束校验：

```java
public ApiResponse<?> create(@Valid @RequestBody UserCreateRequest request) {
    // Validation has completed before this method body runs.
}
```

如果 DTO 中还嵌套了另一个对象，需要在该字段上继续添加 `@Valid`，才能级联校验嵌套对象。

### 2.3 `BindingResult`

`BindingResult` 必须紧跟在对应的 `@Valid` 参数后面。存在它时，校验错误不会直接交给全局异常处理器，控制器可以通过 `hasErrors()`、`getFieldErrors()` 自行读取错误。

这种方式适合理解校验结果或处理少量特殊接口，但每个控制器重复编写错误转换代码会污染业务逻辑。

### 2.4 全局异常处理

不声明 `BindingResult` 时，请求体校验失败会抛出 `MethodArgumentNotValidException`。`@RestControllerAdvice` 配合 `@ExceptionHandler` 可以把异常统一转换为稳定的 JSON 格式，控制器只关注正常业务流程。

本模块返回的错误项包含：

- `field`：校验失败的字段名；
- `message`：已经完成占位符替换和国际化后的提示文本。

示例故意不回显 `password` 或字段的 `rejectedValue`，避免把敏感数据带入响应和日志。

注意：JSON 语法错误或类型转换失败（例如把 `age` 传成 `"abc"`）发生在 Bean Validation 之前，通常属于 `HttpMessageNotReadableException`，不是字段约束失败。

### 2.5 自定义校验器

自定义约束由两部分组成：

1. `@ValidPhone`：使用 `@Constraint(validatedBy = PhoneValidator.class)` 绑定校验器，并按规范声明 `message`、`groups`、`payload`。
2. `PhoneValidator`：实现 `ConstraintValidator<ValidPhone, String>`，在 `isValid` 中编写校验规则。

本例让 `PhoneValidator` 对 `null` 返回 `true`，把“是否为空”交给 `@NotBlank`，把“格式是否正确”交给 `@ValidPhone`。每个约束只负责一件事，错误提示更准确。

### 2.6 错误消息键与占位符

DTO 中的写法：

```java
@Size(min = 3, max = 20, message = "{user.username.size}")
String username;
```

外层 `{user.username.size}` 表示从消息配置文件查找这个键。配置值中的 `{min}`、`{max}`、`{value}` 会使用注解属性替换：

```properties
user.username.size=用户名长度必须在 {min} 到 {max} 个字符之间
user.age.min=年龄不能小于 {value} 岁
```

不要混淆两种花括号：注解的整个 `message` 是消息键，而配置值里的占位符是约束注解的属性名。

### 2.7 配置文件与国际化

`application.properties`：

```properties
spring.messages.basename=messages
spring.messages.encoding=UTF-8
spring.messages.fallback-to-system-locale=false
```

消息文件：

- `messages.properties`：默认文本；
- `messages_zh_CN.properties`：简体中文；
- `messages_en_US.properties`：美式英文。

在 Postman 的 Headers 中设置 `Accept-Language: zh-CN` 或 `Accept-Language: en-US`，同一个校验错误会得到不同语言的提示。所有文件都应使用 UTF-8 保存。

## 3. Postman 请求

启动模块：

```powershell
cd DataValidationDemo
.\mvnw.cmd spring-boot:run
```

请求设置：

- Method：`POST`
- Header：`Content-Type: application/json`
- 可选 Header：`Accept-Language: zh-CN` 或 `en-US`

### 3.1 全局异常处理方式

URL：`http://localhost:8080/api/users`

合法请求（练习题修改前）：

```json
{
  "username": "spring-user",
  "email": "spring@example.com",
  "age": 20,
  "password": "springboot123",
  "phone": "13812345678",
  "nickname": "2b",
  "score": 67
}
```

非法请求：

```json
{
  "username": "a",
  "email": "not-an-email",
  "age": 15,
  "password": "123",
  "phone": "110"
}
```

### 3.2 `BindingResult` 方式

把相同 JSON 发送到 `http://localhost:8080/api/users/binding-result`，比较响应结构，并在调试器中观察 `BindingResult` 的内容。

## 4. 练习题

建议按顺序完成，每完成一题都用 Postman 同时测试合法值、边界值、非法值和缺失字段。

### 练习 1：补充基础约束（入门）

给 `UserCreateRequest` 增加 `nickname` 和 `score`：

- `nickname` 可以为 `null`，但非空时长度必须为 2～30；
- `score` 必填，取值范围为 0～100；
- 中英文错误文本必须放入消息配置文件，不能直接写在注解中。

验收：分别测试 `nickname=null`、1 个字符、2 个字符，以及 `score=-1`、0、100、101。

### 练习 2：比较三种非空注解（入门）

临时给三个字符串字段分别添加 `@NotNull`、`@NotEmpty`、`@NotBlank`，依次发送 `null`、`""`、`"   "`，记录响应差异，并在本文档末尾写出你的结论。

### 练习 3：正则校验（基础）

新增 `idCard` 字段，先使用 `@Pattern` 校验一个简化的 18 位身份证号码格式：前 17 位是数字，最后一位是数字或 `X/x`。

验收：错误消息支持中英文，并说明为什么“格式正确”不等于“身份证号码真实有效”。

### 练习 4：嵌套对象与级联校验（进阶）

新增 `AddressRequest`，包含 `province`、`city`、`detail`，给三个字段添加合适的约束；然后在 `UserCreateRequest` 中增加 `address` 字段。

验收：只有在 `address` 字段上添加 `@Valid` 后，`address.city` 的错误才能被检查出来。观察全局异常响应中的字段路径。

### 练习 5：扩展自定义校验器（进阶）

把 `@ValidPhone` 改造成可配置注解，增加 `allowLandline` 属性；`false` 时只允许手机号，`true` 时同时允许形如 `010-12345678` 的座机号。通过校验器的 `initialize` 方法读取注解配置。

验收：为两种配置各写至少三个 Postman 测试用例，不要把必填逻辑混入格式校验器。

### 练习 6：处理 JSON 类型错误（进阶）

向 `/api/users` 发送 `"age": "abc"`。为 `HttpMessageNotReadableException` 增加全局处理方法，使它与字段校验失败具有可区分的错误码和提示。

验收：响应中不能暴露堆栈、Java 类名或内部实现细节。

### 练习 7：方法参数校验（挑战）

新增查询接口 `GET /api/users/{id}?pageSize=...`，使用 `@Positive`、`@Min`、`@Max` 校验路径变量和查询参数，并研究 Spring Boot 当前版本抛出的异常类型。把它接入统一错误响应。

### 练习 8：校验分组（挑战）

定义 `CreateGroup` 和 `UpdateGroup`：创建用户时密码必填，更新用户时密码允许为空，但填写后仍必须满足长度要求。分别新增创建和更新接口，使用 `@Validated(分组.class)` 触发不同规则。

## 5. 自测问题

1. `@Valid` 和 `@Validated` 各自适合什么场景？
2. 为什么 `BindingResult` 必须紧跟被校验参数？
3. `@NotNull`、`@NotEmpty`、`@NotBlank` 的边界差异是什么？
4. 为什么全局异常处理通常比每个控制器都写 `BindingResult` 更适合 REST API？
5. 自定义约束注解中的 `message`、`groups`、`payload` 为什么必须存在？
6. 为什么自定义手机号校验器把 `null` 当作合法值？
7. `{user.username.size}` 与消息文本中的 `{min}`、`{max}` 分别由谁解析？
8. Postman 如何触发英文错误提示？
9. Bean Validation 失败与 JSON 反序列化失败分别发生在哪个阶段？

## 6. 参考笔记

- [Notion：数据校验](https://app.notion.com/p/3c7422f96c6080198df6e7f4b47445ba)
- [Notion：Java SSM](https://app.notion.com/p/2bf422f96c6080f9b171f17e27a31107)
