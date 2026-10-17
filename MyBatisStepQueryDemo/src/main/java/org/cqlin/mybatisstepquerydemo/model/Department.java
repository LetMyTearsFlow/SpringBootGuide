package org.cqlin.mybatisstepquerydemo.model;

public class Department {
    private Long id;
    private String departmentName;
    private java.util.List<Employee> employees = new java.util.ArrayList<>();

    // 获取部门编号。
    public Long getId() { return id; }

    // 设置部门编号。
    public void setId(Long id) { this.id = id; }

    // 获取部门名称。
    public String getDepartmentName() { return departmentName; }

    // 设置部门名称。
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    // 获取员工列表。
    public java.util.List<Employee> getEmployees() { return employees; }

    // 设置员工列表。
    public void setEmployees(java.util.List<Employee> employees) { this.employees = employees; }
}
