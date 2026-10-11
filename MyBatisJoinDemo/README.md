# MyBatis 多表联合查询练习

环境、实体和测试已准备好，**SQL 和 resultMap 由你实现**。使用 H2 内存库，无需安装数据库，每次启动恢复数据。

## 1. 从这里开始

打开 `src/main/resources/mapper/JoinExerciseMapper.xml`，完成 TODO 1–4。接口和实体已写好，无需修改。

关系：一个客户有多个订单，一个订单有多个明细。

- `association`：封装一个对象，例如 `Order.customer`。
- `collection`：封装一个列表，例如 `Customer.orders`、`Order.items`。

模型位于 `org.cqlin.mybatisjoindemo.model`：

| 模型 | 属性 |
| --- | --- |
| OrderSummary | orderId、orderNo、customerName |
| Customer | id、customerName、List<Order> orders |
| Order | id、orderNo、Customer customer、List<OrderItem> items |
| OrderItem | id、productName、quantity |

## 2. 数据

| 表 | 数据（按列顺序） |
| --- | --- |
| t_customer (id, customer_name) | (1, 张三)、(2, 李四)、(3, 王五) |
| t_order (id, order_no, customer_id) | (101, A001, 1)、(102, A002, 1)、(103, A003, 2) |
| t_order_item (id, order_id, product_name, quantity) | (1001, 101, 键盘, 1)、(1002, 101, 鼠标, 2)、(1003, 102, 显示器, 1) |

王五没有订单；A003 没有明细。用这两条数据练习 LEFT JOIN 和空集合。

## 3. 按顺序完成

| 题目 | Mapper 方法 | 要求 | 测试方法 |
| --- | --- | --- | --- |
| 1 联合查询 | findOrderSummaries | 两表 JOIN，返回 3 个扁平对象，按订单 id 升序 | step1_join |
| 2 association | findOrderWithCustomer | 按订单 id 查询，订单中包含完整客户对象；不存在返回 null | step2_association |
| 3 collection | findCustomerWithOrders | 客户 LEFT JOIN 订单，订单按 id 升序；王五的 orders 为空列表 | step3_collection |
| 4 组合使用 | findAllOrderDetails | 三表 JOIN，订单包含客户和明细；4 行合并成 3 个订单，A003 的 items 为空列表 | step4_combined |

第 4 题按订单 id、明细 id 升序；结果示例：A001 → 张三 + [键盘×1，鼠标×2]。

要求第 2–4 题使用**一次联合查询 + 嵌套结果映射**，暂不使用嵌套 select。只加载题目指定的关系，不需要构造客户与订单互相引用的对象图。

提示：

- 每个 select 的 id 与接口方法名一致；参数名是 `id`，使用 `#{id}`。
- resultMap 放在 select 前；类型可写模型完整类名。
- 多张表都有 id，用不同列别名区分，column 写查询结果的别名。
- 父对象和子对象的主键使用 `<id>`，帮助 MyBatis 合并重复行。
- `association` 的对象类型可用 `javaType`；`collection` 的元素类型用 `ofType`。
- LEFT JOIN 的子表可能全是 NULL；可用 `notNullColumn` 指定子对象存在的判断列。
- 驼峰映射关闭：第 1 题用 SQL 别名或 resultMap；后续题显式映射各层字段。

## 4. 运行验收

在项目根目录打开 PowerShell：

```powershell
# 只检查环境：1 个环境测试应通过，4 个练习测试默认跳过。
.\mvnw.cmd -f MyBatisJoinDemo/pom.xml test

# 完成一题后单独验收，替换 # 后的测试方法名。
.\mvnw.cmd -f MyBatisJoinDemo/pom.xml '-Dexercise=true' '-Dtest=JoinMappingExerciseTests#step1_join' test

# 全部完成后验收。
.\mvnw.cmd -f MyBatisJoinDemo/pom.xml '-Dexercise=true' test
```

IDE 中导入本模块 `pom.xml`，运行练习测试时添加 VM 参数 `-Dexercise=true`。模块作为独立练习，不加入根 POM。

`Invalid bound statement`：该题尚未添加 select，或 id/namespace 不匹配。
字段为 null：检查 column 别名与 property；父对象重复：检查各层 `<id>`；空集合多一个空对象：检查 LEFT JOIN 子表的判断列。

测试验收结果值；是否使用指定 JOIN、association、collection，仍需对照题目检查 XML。
