package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingService;

/**
 * Second option (still not optimal) for dependency injection. If using setter is forgotten it can give nullpointer.
 */
public class SetterInjectedController {
    private GreetingService greetingService;

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(String greeting){
        return greetingService.sayHello(greeting);
    }
}
