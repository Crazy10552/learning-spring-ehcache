package com.tianyang.learning.spring.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//Spring扫描工程的包路径
@ComponentScan({"com.tianyang.learning.spring.ehcache"})
// 自定义文件的引入
@ImportResource("classpath:spring.xml")
public class SpringEhcacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEhcacheApplication.class, args);
	}

}
