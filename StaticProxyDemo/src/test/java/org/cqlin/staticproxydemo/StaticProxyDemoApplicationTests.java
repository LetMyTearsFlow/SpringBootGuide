package org.cqlin.staticproxydemo;

import org.cqlin.staticproxydemo.proxy.UserServiceProxy;
import org.cqlin.staticproxydemo.service.UserService;
import org.cqlin.staticproxydemo.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

class StaticProxyDemoApplicationTests {

    @Test
    void test() {
        UserService userService = new UserServiceImpl();
        UserService proxy = new UserServiceProxy(userService);

        proxy.addUser("L");
    }

}
