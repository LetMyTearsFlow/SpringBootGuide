INSERT INTO t_customer (id, customer_name) VALUES
    (1, '张三'), (2, '李四'), (3, '王五');
INSERT INTO t_order (id, order_no, customer_id) VALUES
    (101, 'A001', 1), (102, 'A002', 1), (103, 'A003', 2);
INSERT INTO t_order_item (id, order_id, product_name, quantity) VALUES
    (1001, 101, '键盘', 1), (1002, 101, '鼠标', 2), (1003, 102, '显示器', 1);
