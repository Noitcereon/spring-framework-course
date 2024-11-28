package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.GreetingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PropertyInjectedControllerTest {

    PropertyInjectedController controller;
    @BeforeEach
    void setUp() {
        controller = new PropertyInjectedController();
        controller.greetingService = new GreetingServiceImpl();
    }

    @Test
    void sayHello() {
        controller.sayHello("Hello");
    }
}