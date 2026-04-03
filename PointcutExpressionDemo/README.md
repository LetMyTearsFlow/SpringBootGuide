请分别写出下面每一问的切点表达式。

第 1 题

匹配 service 包及其子包下所有类的所有方法。
```
execution(* org.cqlin.pointcutexpressiondemo.service..*.*(..))
```
第 2 题

匹配 service 包及其子包下 所有 public 方法。
```
execution(public * org.cqlin.pointcutexpressiondemo.service..*.*(..))
```

第 3 题

匹配 OrderServiceImpl 类中定义的所有方法。
```
execution(* org.cqlin.pointcutexpressiondemo.service.impl.OrderServiceImpl.*(..))
```

第 4 题

匹配所有方法名以 find 开头 的方法。
```
execution(* org.cqlin.pointcutexpressiondemo..*.find*(..))
```

第 5 题

匹配所有返回值为 void 的方法。
```
execution(void org.cqlin.pointcutexpressiondemo..*.*(..))
```
第 6 题

匹配所有 无参方法。
```
execution(* org.cqlin.pointcutexpressiondemo..*.*())
```

第 7 题

匹配所有 第一个参数是 String，后续参数任意 的方法。
```
execution(* org.cqlin.pointcutexpressiondemo..*.*(String, ..))
```

第 8 题

匹配所有只有一个参数 的方法。
```
execution(* org.cqlin.pointcutexpressiondemo..*.*(*))
```

第 9 题

匹配 BaseService 及其子类上的所有方法。
```
within(org.cqlin.pointcutexpressiondemo.service.BaseService+)
```

第 10 题

匹配 运行时参数类型 为 SecretDTO 的方法。
```
args(org.cqlin.pointcutexpressiondemo.dto.SecretDTO, ..)
```

第 11 题

匹配方法上标了 @Audit 的方法。
```
@annotation(org.cqlin.pointcutexpressiondemo.annotation.Audit)
```

第 12 题

匹配类上标了 @SensitiveOp 的类中的方法。
```
@within(org.cqlin.pointcutexpressiondemo.annotation.SensitiveOp)
```


第 13 题

分别写出能匹配 orderService Bean 和所有名称以 Service 结尾 Bean 的表达式。

匹配 orderService Bean:
```
bean(orderService)
```

匹配所有名称以 Service 结尾 Bean:
```
bean(*Service)
```

第 14 题

只匹配 repository 包下的所有方法。
```
execution(* org.cqlin.pointcutexpressiondemo.repository.*.*(..))
```

第 15 题

匹配所有 service 包及其子包下的方法，但排除所有 find* 方法。
```
execution(* org.cqlin.pointcutexpressiondemo.service..*.*(..)) && !execution(* org.cqlin.pointcutexpressiondemo..*.find*(..))
```

第 16 题

匹配所有 service 包下的方法，并且要求方法上必须有 @Audit 注解。
```
execution(* org.cqlin.pointcutexpressiondemo.service..*.*(..)) && @annotation(org.cqlin.pointcutexpressiondemo.annotation.Audit)

```

第 17 题

分别写出 this(...) 和 target(...) 的示例，目标都设为 OrderService 类型，并说明二者在 Spring AOP 中的区别。
```
this(org.cqlin.pointcutexpressiondemo.service.OrderService)

target(org.cqlin.pointcutexpressiondemo.service.OrderService)
```

this是代理对象是OrderService类型，target是被代理对象是OrderService类型。

第 18 题

匹配参数对象的 运行时类型上带有 @ClassifiedParam 注解 的方法。

```
@args(org.cqlin.pointcutexpressiondemo.annotation.ClassifiedParam)
```

第 19 题

下面哪个表达式 不能用于 Spring AOP，并说明原因：
```
execution(* com.demo.service..*.*(..))
call(* com.demo.service..*.*(..))
bean(orderService)
@annotation(com.demo.annotation.Audit)
```

call，因为call不属于 Spring AOP 支持的范围。

第 20 题

说明 OrderServiceImpl.internalCall() 内部调用 this.findOrder(1L) 时，为什么相关切面默认可能不会生效。

因为 Spring AOP 基于代理，所以不拦截对象内部调用自身的方法。