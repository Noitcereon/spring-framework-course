package me.noitcereon.practice.spring.core;

import me.noitcereon.practice.spring.core.controllers.MyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringCoreApplicationTests {

	@Autowired
	ApplicationContext context;
	@Autowired
	MyController myController;

	@Test
	void contextLoads() {
	}
	@Test
	void testWeCanLoadBeanFromContext(){
		MyController controller = context.getBean(MyController.class);
		controller.sayHello(controller.getInstanceVariable());
	}
	@Test
	void givenMyControllerViaSpringDI_WhenUsingBeanMethod_ThenSayHello(){
		this.myController.sayHello("Hello");
	}

}
