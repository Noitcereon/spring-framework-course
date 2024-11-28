package me.noitcereon.practice.spring.core.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String greeting) {
        String message = greeting + " most esteemed person.";
        System.out.println(message);
        return message;
    }
}
