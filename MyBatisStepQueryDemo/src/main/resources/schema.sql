DROP TABLE IF EXISTS t_employee;
DROP TABLE IF EXISTS t_department;
CREATE TABLE t_department (id BIGINT PRIMARY KEY, department_name VARCHAR(50));
CREATE TABLE t_employee (id BIGINT PRIMARY KEY, employee_name VARCHAR(50), department_id BIGINT, FOREIGN KEY (department_id) REFERENCES t_department(id));
