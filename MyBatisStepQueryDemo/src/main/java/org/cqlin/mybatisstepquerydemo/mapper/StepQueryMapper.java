package org.cqlin.mybatisstepquerydemo.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.cqlin.mybatisstepquerydemo.model.Department;
import org.cqlin.mybatisstepquerydemo.model.Employee;

@Mapper
public interface StepQueryMapper {
    // 第一步：按员工编号查询员工，再通过 association 加载部门。
    Employee findEmployeeWithDepartment(@Param("id") Long id);

    // 第二步：供 association 调用，按部门编号查询部门基础字段。
    Department findDepartmentById(@Param("id") Long id);

    // 第一步：按部门编号查询部门，再通过 collection 加载员工列表。
    Department findDepartmentWithEmployees(@Param("id") Long id);

    // 第二步：供 collection 调用，查询部门内员工，按员工编号升序。
    List<Employee> findEmployeesByDepartmentId(@Param("departmentId") Long departmentId);

    // 综合练习：查询全部员工，通过嵌套 select 填充部门。
    List<Employee> findAllEmployeesWithDepartment();
}
