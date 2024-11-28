package me.noitcereon.practice.spring.core;

import me.noitcereon.practice.spring.core.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringCoreApplication.class, args);
		MyController bean = context.getBean(MyController.class);
		bean.sayHello("Hello");
		System.out.println(bean.getInstanceVariable());
	}

}
