package org.cqlin.mybatisjoindemo;

import java.util.List;
import org.cqlin.mybatisjoindemo.mapper.JoinExerciseMapper;
import org.cqlin.mybatisjoindemo.model.Customer;
import org.cqlin.mybatisjoindemo.model.Order;
import org.cqlin.mybatisjoindemo.model.OrderItem;
import org.cqlin.mybatisjoindemo.model.OrderSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
// 这些测试直接查询真实内存库，只保留依赖注入监听器。
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@EnabledIfSystemProperty(named = "exercise", matches = "true")
class JoinMappingExerciseTests {
    @Autowired private JoinExerciseMapper mapper;

    // 验收两表联合查询的字段映射、行数和排序。
    @Test
    void step1_join() {
        List<OrderSummary> rows = mapper.findOrderSummaries();
        assertEquals(3, rows.size());
        assertSummary(rows.get(0), 101L, "A001", "张三");
        assertSummary(rows.get(1), 102L, "A002", "张三");
        assertSummary(rows.get(2), 103L, "A003", "李四");
    }

    // 验收一个订单封装一个客户，以及不存在订单时的返回值。
    @Test
    void step2_association() {
        Order first = mapper.findOrderWithCustomer(101L);
        assertOrder(first, 101L, "A001");
        assertCustomer(first.getCustomer(), 1L, "张三");
        Order second = mapper.findOrderWithCustomer(103L);
        assertOrder(second, 103L, "A003");
        assertCustomer(second.getCustomer(), 2L, "李四");
        assertNull(mapper.findOrderWithCustomer(999L));
    }

    // 验收多笔订单、单笔订单、无订单客户和不存在客户。
    @Test
    void step3_collection() {
        Customer first = mapper.findCustomerWithOrders(1L);
        assertCustomer(first, 1L, "张三");
        assertEquals(2, first.getOrders().size());
        assertOrder(first.getOrders().get(0), 101L, "A001");
        assertOrder(first.getOrders().get(1), 102L, "A002");
        Customer second = mapper.findCustomerWithOrders(2L);
        assertCustomer(second, 2L, "李四");
        assertEquals(1, second.getOrders().size());
        assertOrder(second.getOrders().get(0), 103L, "A003");
        Customer empty = mapper.findCustomerWithOrders(3L);
        assertCustomer(empty, 3L, "王五");
        assertNotNull(empty.getOrders());
        assertTrue(empty.getOrders().isEmpty(), "无订单时不能生成一个字段全空的订单对象");
        assertNull(mapper.findCustomerWithOrders(999L));
    }

    // 验收三表映射、父对象去重、明细字段、排序和空明细列表。
    @Test
    void step4_combined() {
        List<Order> orders = mapper.findAllOrderDetails();
        assertEquals(3, orders.size(), "联结结果有 4 行，但应合并为 3 个订单");
        Order first = orders.get(0);
        assertOrder(first, 101L, "A001");
        assertCustomer(first.getCustomer(), 1L, "张三");
        assertEquals(2, first.getItems().size());
        assertItem(first.getItems().get(0), 1001L, "键盘", 1);
        assertItem(first.getItems().get(1), 1002L, "鼠标", 2);
        Order second = orders.get(1);
        assertOrder(second, 102L, "A002");
        assertCustomer(second.getCustomer(), 1L, "张三");
        assertEquals(1, second.getItems().size());
        assertItem(second.getItems().get(0), 1003L, "显示器", 1);
        Order empty = orders.get(2);
        assertOrder(empty, 103L, "A003");
        assertCustomer(empty.getCustomer(), 2L, "李四");
        assertNotNull(empty.getItems());
        assertTrue(empty.getItems().isEmpty(), "无明细时不能生成一个字段全空的明细对象");
    }

    // 检查扁平查询结果的全部字段。
    private static void assertSummary(OrderSummary row, Long id, String no, String name) {
        assertNotNull(row);
        assertEquals(id, row.getOrderId());
        assertEquals(no, row.getOrderNo());
        assertEquals(name, row.getCustomerName());
    }

    // 检查客户的基础字段，不要求加载反向订单关系。
    private static void assertCustomer(Customer customer, Long id, String name) {
        assertNotNull(customer);
        assertEquals(id, customer.getId());
        assertEquals(name, customer.getCustomerName());
    }

    // 检查订单的基础字段，让主键混淆和编号漏映射能被发现。
    private static void assertOrder(Order order, Long id, String no) {
        assertNotNull(order);
        assertEquals(id, order.getId());
        assertEquals(no, order.getOrderNo());
    }

    // 检查明细主键、商品名称和购买数量。
    private static void assertItem(OrderItem item, Long id, String name, Integer quantity) {
        assertNotNull(item);
        assertEquals(id, item.getId());
        assertEquals(name, item.getProductName());
        assertEquals(quantity, item.getQuantity());
    }
}
