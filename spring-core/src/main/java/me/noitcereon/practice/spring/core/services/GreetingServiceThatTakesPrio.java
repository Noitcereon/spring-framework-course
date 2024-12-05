package me.noitcereon.practice.spring.core.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary // This annotation makes Spring use this implementation of GreetingService over others when injecting.
@Service
public class GreetingServiceThatTakesPrio implements GreetingService {
    @Override
    public String sayHello(String greeting) {
        System.out.println("This is the GreetingServiceThatTakesPrio");
        System.out.println(greeting);
        return greeting;
    }
}
