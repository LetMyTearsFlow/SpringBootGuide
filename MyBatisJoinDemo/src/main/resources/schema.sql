DROP TABLE IF EXISTS t_order_item;
DROP TABLE IF EXISTS t_order;
DROP TABLE IF EXISTS t_customer;

CREATE TABLE t_customer (
    id BIGINT PRIMARY KEY,
    customer_name VARCHAR(50) NOT NULL
);
CREATE TABLE t_order (
    id BIGINT PRIMARY KEY,
    order_no VARCHAR(30) NOT NULL,
    customer_id BIGINT NOT NULL REFERENCES t_customer(id)
);
CREATE TABLE t_order_item (
    id BIGINT PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES t_order(id),
    product_name VARCHAR(50) NOT NULL,
    quantity INT NOT NULL
);
