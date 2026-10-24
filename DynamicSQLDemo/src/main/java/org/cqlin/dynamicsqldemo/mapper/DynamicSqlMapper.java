package org.cqlin.dynamicsqldemo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cqlin.dynamicsqldemo.model.Employee;

@Mapper
public interface DynamicSqlMapper {
    /** 第1题：if + where，组合可选条件，所有条件为空时查询全部。 */
    List<Employee> search(@Param("name") String name, @Param("departmentId") Long departmentId,
                          @Param("minSalary") Integer minSalary);

    /** 第2题：choose，按姓名、部门、默认在职的优先级只选一个分支。 */
    List<Employee> chooseSearch(@Param("name") String name, @Param("departmentId") Long departmentId);

    /** 第3题：foreach，按编号集合查询，null 或空集合返回空列表。 */
    List<Employee> findByIds(@Param("ids") List<Long> ids);

    /** 第4题：set，仅更新非空字段，姓名还应忽略空串，id 为必填条件。 */
    int updateSelective(Employee employee);

    /** 第5题：trim，以 prefix 和 prefixOverrides 管理可选条件。 */
    List<Employee> trimSearch(@Param("departmentId") Long departmentId, @Param("status") String status);

    /** 第6题：foreach 批量插入，调用方保证列表非空且员工字段有效。 */
    int batchInsert(@Param("employees") List<Employee> employees);

    /** 第7题：bind 生成姓名模糊查询参数；姓名为空时查询全部。 */
    List<Employee> bindSearch(@Param("name") String name);
}
