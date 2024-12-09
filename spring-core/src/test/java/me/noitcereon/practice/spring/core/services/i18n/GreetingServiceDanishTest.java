package me.noitcereon.practice.spring.core.services.i18n;

import me.noitcereon.practice.spring.core.services.DataSourceService;
import me.noitcereon.practice.spring.core.services.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("DA")
@SpringBootTest
class GreetingServiceDanishTest {

    @Qualifier("i18nService") // This qualifier combined with the active profile makes the Spring DI inject the GreetingServiceDanish service.
    @Autowired
    GreetingService greetingService;

    @Test
    void sayHello() {
        greetingService.sayHello("Hej");
    }
}