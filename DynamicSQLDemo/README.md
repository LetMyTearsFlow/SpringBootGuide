# MyBatis 动态 SQL 练习

只需完成 src/main/resources/mapper/DynamicSqlMapper.xml 中的 TODO。
实体、接口、H2 数据和验收测试已配好，无需安装数据库。IDEA 中先 Reload Maven。

## 题目

查询结果一律按 id 升序；使用 #{} 绑定参数。字符串为空指 null 或空串。

| 题号 | 方法 | 要求 |
| --- | --- | --- |
| 1 | search | if + where：姓名包含匹配、部门相等、工资大于等于下限；有效条件以 AND 组合，无条件查全部 |
| 2 | chooseSearch | choose/when/otherwise：优先姓名包含匹配，其次部门相等，否则只查 ACTIVE；只选一个分支 |
| 3 | findByIds | foreach 构造 IN；null 或空集合返回空列表；忽略不存在的编号 |
| 4 | updateSelective | set：按 id 更新非 null 字段，姓名还要忽略空串；保留未传字段；没有可更新字段时返回 0 |
| 5 | trimSearch | trim 的 prefix/prefixOverrides：部门、状态可选，AND 组合，无条件查全部 |
| 6 | batchInsert | foreach 构造多行 VALUES，返回插入数量；传入列表保证非空，字段有效 |
| 7 | bindSearch | bind 构造姓名包含匹配参数，配合 #{} 使用；姓名为空查全部 |

第4题提示：没有可更新字段时，可用 choose 分支执行一条匹配零行的合法 UPDATE。
本题不要求把字段主动设置为 NULL。更新和插入测试会自动回滚。
测试验证行为；标签使用要求需要自己对照题目检查。

## 初始数据

| id | employee_name | department_id | salary | status |
| --- | --- | --- | --- | --- |
| 101 | 张三 | 1 | 8000 | ACTIVE |
| 102 | 李四 | 1 | 12000 | ACTIVE |
| 103 | 王五 | 2 | 6000 | INACTIVE |
| 104 | 张小六 | 2 | 9000 | ACTIVE |
| 105 | 赵七 | NULL | 5000 | INACTIVE |

## 验证（在项目根目录运行）

环境检查（练习默认跳过）：

```powershell
.\mvnw.cmd -pl DynamicSQLDemo test
```

只验收第1题；把 step1_ifWhere 改成其他测试方法名可逐题验证：

```powershell
.\mvnw.cmd -pl DynamicSQLDemo '-Dexercise=true' '-Dtest=DynamicSqlExerciseTests#step1_ifWhere' test
```

验收全部：

```powershell
.\mvnw.cmd -pl DynamicSQLDemo '-Dexercise=true' test
```

未实现映射时，练习测试出现 Invalid bound statement 属于预期。
