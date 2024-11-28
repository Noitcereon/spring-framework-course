package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Least optimal way to do dependency injection. Easy to forget initialization of greetingService, which gives nullpointer.
 */
@Controller
public class PropertyInjectedController {

    @Autowired
    GreetingService greetingService;

    public String sayHello(String greeting){
        return greetingService.sayHello(greeting);
    }
}
