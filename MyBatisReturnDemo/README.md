# MyBatis 返回值封装练习

环境、实体和测试已准备好，**SQL 与映射由你实现**。使用 H2 内存库，无需安装数据库，每次启动恢复数据。

## 1. 从这两个文件开始

- `src/main/resources/mapper/UserReturnMapper.xml`：完成 TODO 1–7。
- `src/main/java/org/cqlin/mybatisreturndemo/mapper/UserReturnMapper.java`：完成第 5 题注解。

实体：`org.cqlin.mybatisreturndemo.model.User`（`Long id`、`String userName`、`Integer age`）。
表：`t_user`（`id`、`user_name`、`age`）。驼峰自动映射保持关闭。

| id | user_name | age |
| --- | --- | --- |
| 1 | 张三 | 20 |
| 2 | 李四 | 25 |
| 3 | 王五 | 25 |
| 4 | 赵六 | NULL |

## 2. 按顺序完成

| 题目 | 方法 | 要求 |
| --- | --- | --- |
| 1 单值 | countUsers | 返回总数 4 |
| 2 对象 | findById | 使用 resultType 和 SQL 别名；不存在返回 null |
| 3 集合 | findByMinAge | 年龄大于等于参数，按 id 升序；无匹配返回空 List |
| 4 单行 Map | findRow | 键为 id、user_name、age；不加 @MapKey |
| 5 多行 Map | findAllAsMap | id 为键、User 为值；自行添加 @MapKey |
| 6 自定义映射 | findWithResultMap | 使用 resultMap，明确映射所有字段；不写 SQL 别名 |
| 7 可空单值 | findAge | 正常年龄、NULL、无记录均能处理 |

每个 select 的 id 与方法名一致；resultType 与 resultMap 二选一。第 6 题的 resultMap 放在 select 标签前。

## 3. 运行验收

在本模块目录打开 PowerShell（首次运行需要下载 Maven/依赖）：

```powershell
# 检查环境：应通过；7 个练习测试默认跳过，不代表完成练习。
.\mvnw.cmd test

# 每完成一题就验收；替换 # 后的方法名即可。
.\mvnw.cmd '-Dexercise=true' '-Dtest=ReturnMappingExerciseTests#step1_count' test

# 全部完成后验收 7 题。
.\mvnw.cmd '-Dexercise=true' test
```

逐题测试方法：`step1_count`、`step2_object`、`step3_list`、`step4_rowMap`、`step5_keyedMap`、`step6_resultMap`、`step7_nullableValue`。

在 IDE 运行练习测试时，添加 VM 参数 `-Dexercise=true`。模块未加入根 POM，直接导入本目录的 `pom.xml`。

**遇到错误：**`Invalid bound statement` → 该题尚未补写或 id 不匹配；userName 为 null → 检查别名或映射；练习被跳过 → 检查 exercise 参数。

自检：为什么 List 的 resultType 不写 List？两种 Map 的键分别来自哪里？为什么年龄用 Integer？测试检查结果，映射方式还需对照题目自行检查。
