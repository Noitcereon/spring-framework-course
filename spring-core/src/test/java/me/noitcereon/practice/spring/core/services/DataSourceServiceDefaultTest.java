package me.noitcereon.practice.spring.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
class DataSourceServiceDefaultTest {

    @Autowired
    private DataSourceService dataSourceService;
    @Test
    void getConnectionString() {
        String expected = "http://127.0.0.1/dev";
        String actual = dataSourceService.getConnectionString();
        Assertions.assertEquals(expected, actual);
    }
}