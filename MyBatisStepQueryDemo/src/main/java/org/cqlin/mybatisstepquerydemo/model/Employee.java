package org.cqlin.mybatisstepquerydemo.model;

public class Employee {
    private Long id;
    private String employeeName;
    private Long departmentId;
    private Department department;

    // 获取员工编号。
    public Long getId() { return id; }

    // 设置员工编号。
    public void setId(Long id) { this.id = id; }

    // 获取员工姓名。
    public String getEmployeeName() { return employeeName; }

    // 设置员工姓名。
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    // 获取部门编号。
    public Long getDepartmentId() { return departmentId; }

    // 设置部门编号。
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }

    // 获取所属部门。
    public Department getDepartment() { return department; }

    // 设置所属部门。
    public void setDepartment(Department department) { this.department = department; }
}
