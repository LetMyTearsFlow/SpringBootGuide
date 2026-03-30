package org.cqlin.staticproxydemo.service;

public class UserServiceImpl implements UserService{
    @Override
    public void addUser(String name) {
        System.out.println("Add user: "+name);
    }
}
