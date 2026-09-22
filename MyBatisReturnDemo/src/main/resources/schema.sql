-- 仅在本模块内存库中重建练习表。
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id BIGINT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    age INTEGER
);
