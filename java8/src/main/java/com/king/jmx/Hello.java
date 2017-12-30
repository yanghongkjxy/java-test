package com.king.jmx;
/*
  * 该类名称必须与实现的接口的前缀保持一致（即MBean前面的名称
  */

public class Hello implements HelloMBean {
    private String name;

    private String age;

    public void getTelephone() {
        System.out.println("get Telephone");
    }

    public void helloWorld() {
        System.out.println("hello world");
    }

    public void helloWorld(String str) {
        System.out.println("helloWorld:" + str);
    }

    public String getName() {
        System.out.println("get name 123" + name);
        return name;
    }

    public void setName(String name) {
        System.out.println("set name " + name);
        this.name = name;
    }

    public String getAge() {
        System.out.println("get age 123");
        return age;
    }

    public void setAge(String age) {
        System.out.println("set age 123");
        this.age = age;
    }

    public void printHello(String whoName) {
        System.out.println("Hello , " + whoName);
    }
}