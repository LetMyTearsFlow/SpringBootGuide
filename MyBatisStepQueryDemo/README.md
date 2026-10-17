# MyBatis 分步查询练习

数据库、实体、接口、测试已准备好，SQL 和 resultMap 由你完成。使用 H2 内存库，无需安装数据库。

## 从这里开始

编辑 `src/main/resources/mapper/StepQueryMapper.xml`，按 TODO 1 → 2 → 3 完成。
实体包：`org.cqlin.mybatisstepquerydemo.model`。

分步查询：先查主对象，再把结果中的某一列作为参数，调用另一个 select 查询关联对象。
例如：员工 101 → department_id=1 → 查询部门 1 → 填入 employee.department。

| 题目 | 入口方法 | 要求 |
| --- | --- | --- |
| 1 多对一 | findEmployeeWithDepartment | association + 嵌套 select，封装所属部门 |
| 2 一对多 | findDepartmentWithEmployees | collection + 嵌套 select，封装员工列表 |
| 3 列表查询 | findAllEmployeesWithDepartment | 复用第 1 题映射，观察 SQL 与一级缓存 |

## 数据和边界

- 部门：1 研发部、2 测试部、3 人事部。
- 员工：101 张三（1）、102 李四（1）、103 王五（2）、104 赵六（NULL）。
- 人事部的 employees 应为空列表；赵六的 department 应为 null。
- 查不存在的员工或部门，返回 null。
- 列表按员工 id 升序；不构造部门与员工互相嵌套的对象图。

## 提示

- select 的 id 对应接口方法名，namespace 对应接口全名。
- `property` 是 Java 属性，`column` 是第一步 SQL 的结果列名，`select` 是第二步语句 id。
- 第二步参数：部门用 `#{id}`，员工列表用 `#{departmentId}`。
- 驼峰映射关闭，使用 resultMap 显式映射字段；部门基础查询可定义单独 resultMap。
- 两个有嵌套关系的 resultMap 放在 select 前；员工列表查询使用不含 association 的基础映射。
- 本练习关闭延迟加载。完成后可尝试开启，并比较访问关联属性前后的日志。
- 第 3 题在一次 Mapper 调用内，共享相同部门的员工通常复用一级缓存；因此不要直接认定一定执行 1+N 次 SQL。不同 Mapper 调用不保证共享 SqlSession。
- 必须使用嵌套 select，不使用 JOIN。测试检查结果，映射方式和 SQL 次数需要对照 XML 与控制台检查。

## 运行验收

在仓库根目录运行：

```powershell
# 环境测试通过，三个练习测试默认跳过。
.\mvnw.cmd -pl MyBatisStepQueryDemo test

# 完成一题后单独验收。
.\mvnw.cmd -pl MyBatisStepQueryDemo '-Dexercise=true' '-Dtest=StepQueryExerciseTests#step1_association' test

# 全部完成后验收。
.\mvnw.cmd -pl MyBatisStepQueryDemo '-Dexercise=true' test
```

第 2、3 题测试名分别为 step2_collection、step3_list。
未完成时主动开启练习测试，出现 Invalid bound statement 是正常的，表示尚未添加对应 select。

根 pom.xml 已登记此模块，子模块通过 parent 和 ../pom.xml 继承根项目。在 IDEA 的 Maven 工具窗口点击 Reload All Maven Projects 即可识别。
