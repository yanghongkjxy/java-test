package com.king.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
public class Test {
	public static void main(String args[])
	{
		SpringApplication.run(Test.class, args);
		System.out.println("hello");
		Map<String,String> treeMap=new TreeMap<>();
		Set<String> treeSet=new TreeSet<>();
		ReentrantLock lock=new ReentrantLock();
	}
	
	@RequestMapping("/")
	public String say()
	{
		return " hello spring boot" ;
	}
	@RequestMapping("/say/{name}")
	public String say(@PathVariable String name)
	{
		return name+" , hello";
	}

}
