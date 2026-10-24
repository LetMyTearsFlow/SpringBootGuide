DROP TABLE IF EXISTS t_employee;
CREATE TABLE t_employee (
    id BIGINT PRIMARY KEY,
    employee_name VARCHAR(50) NOT NULL,
    department_id BIGINT,
    salary INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL
);
