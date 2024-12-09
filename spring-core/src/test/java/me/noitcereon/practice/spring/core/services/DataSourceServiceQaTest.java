package me.noitcereon.practice.spring.core.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("QA")
class DataSourceServiceQaTest {

    @Autowired
    DataSourceService dataSourceService;
    @Test
    void getConnectionString() {
        String expected = "Quality assurance datasource";
        String actual = dataSourceService.getConnectionString();
        assertEquals(expected, actual);
    }
}