package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingService;
import me.noitcereon.practice.spring.core.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {
    public String instanceVariable = "Wow";
    private final GreetingService greetingService;

    public MyController(){
        this.greetingService = new GreetingServiceImpl();
    }

    public String sayHello(String greeting){
        return greetingService.sayHello(greeting);
    }

    public String getInstanceVariable() {
        return instanceVariable;
    }
}
