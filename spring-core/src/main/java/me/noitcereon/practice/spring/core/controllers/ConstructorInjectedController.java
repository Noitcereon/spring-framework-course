package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingService;

/**
 * The most optimal way to do dependency injection, as you can't forget to initialize required dependencies.
 */
public class ConstructorInjectedController {
    private final GreetingService greetingService;

    public ConstructorInjectedController(GreetingService greetingService){
        this.greetingService = greetingService;
    }

    public String sayHello(String greeting){
        return greetingService.sayHello(greeting);
    }
}
