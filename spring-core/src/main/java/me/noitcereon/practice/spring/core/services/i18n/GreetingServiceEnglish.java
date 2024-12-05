package me.noitcereon.practice.spring.core.services.i18n;

import me.noitcereon.practice.spring.core.services.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("EN")
@Service
@Qualifier("i18nService")
public class GreetingServiceEnglish implements GreetingService {
    @Override
    public String sayHello(String greeting) {
        String message = greeting + " (english)";
        System.out.println(message);
        return message;
    }
}
