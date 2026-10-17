package org.cqlin.mybatisstepquerydemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.cqlin.mybatisstepquerydemo.mapper.StepQueryMapper;
import org.cqlin.mybatisstepquerydemo.model.Employee;

@SpringBootTest
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@EnabledIfSystemProperty(named = "exercise", matches = "true")
class StepQueryExerciseTests {
    @Autowired private StepQueryMapper mapper;

    // 验收嵌套对象、不同部门、空外键及不存在的员工。
    @Test
    void step1_association() {
        var employee = mapper.findEmployeeWithDepartment(101L);
        assertEquals(101L, employee.getId());
        assertEquals("张三", employee.getEmployeeName());
        assertEquals(1L, employee.getDepartmentId());
        assertEquals(1L, employee.getDepartment().getId());
        assertEquals("研发部", employee.getDepartment().getDepartmentName());
        assertEquals("测试部", mapper.findEmployeeWithDepartment(103L).getDepartment().getDepartmentName());
        assertNull(mapper.findEmployeeWithDepartment(104L).getDepartment());
        assertNull(mapper.findEmployeeWithDepartment(999L));
    }

    // 验收一对多列表、排序、空集合及不存在的部门。
    @Test
    void step2_collection() {
        var department = mapper.findDepartmentWithEmployees(1L);
        assertEquals(1L, department.getId());
        assertEquals("研发部", department.getDepartmentName());
        assertEquals(java.util.List.of(101L, 102L), department.getEmployees().stream().map(Employee::getId).toList());
        assertEquals("张三", department.getEmployees().get(0).getEmployeeName());
        assertEquals(1L, department.getEmployees().get(0).getDepartmentId());
        assertEquals(1, mapper.findDepartmentWithEmployees(2L).getEmployees().size());
        assertNotNull(mapper.findDepartmentWithEmployees(3L).getEmployees());
        assertTrue(mapper.findDepartmentWithEmployees(3L).getEmployees().isEmpty());
        assertNull(mapper.findDepartmentWithEmployees(999L));
    }

    // 验收列表查询复用嵌套映射，并保留未分配部门的员工。
    @Test
    void step3_list() {
        var employees = mapper.findAllEmployeesWithDepartment();
        assertEquals(java.util.List.of(101L, 102L, 103L, 104L), employees.stream().map(Employee::getId).toList());
        assertEquals("研发部", employees.get(0).getDepartment().getDepartmentName());
        assertEquals("研发部", employees.get(1).getDepartment().getDepartmentName());
        assertEquals("测试部", employees.get(2).getDepartment().getDepartmentName());
        assertEquals("赵六", employees.get(3).getEmployeeName());
        assertNull(employees.get(3).getDepartment());
    }
}
