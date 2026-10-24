package org.cqlin.dynamicsqldemo.model;

/** 员工数据；查询结果和动态更新参数共用。 */
public class Employee {
    private Long id;
    private String employeeName;
    private Long departmentId;
    private Integer salary;
    private String status;

    /** 获取员工编号。 */
    public Long getId() { return id; }
    /** 设置员工编号。 */
    public void setId(Long id) { this.id = id; }
    /** 获取员工姓名。 */
    public String getEmployeeName() { return employeeName; }
    /** 设置员工姓名。 */
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }
    /** 获取部门编号。 */
    public Long getDepartmentId() { return departmentId; }
    /** 设置部门编号。 */
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    /** 获取工资。 */
    public Integer getSalary() { return salary; }
    /** 设置工资。 */
    public void setSalary(Integer salary) { this.salary = salary; }
    /** 获取状态。 */
    public String getStatus() { return status; }
    /** 设置状态。 */
    public void setStatus(String status) { this.status = status; }
}
