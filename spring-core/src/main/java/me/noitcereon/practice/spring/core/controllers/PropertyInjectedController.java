package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingService;

/**
 * Least optimal way to do dependency injection. Easy to forget initialization of greetingService, which gives nullpointer.
 */
public class PropertyInjectedController {

    GreetingService greetingService;

    public String sayHello(String greeting){
        return greetingService.sayHello(greeting);
    }
}
