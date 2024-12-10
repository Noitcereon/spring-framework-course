package me.noitcereon.practice.spring.core.services;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(value = {"DEV", "default"})
@Service
public class DataSourceServiceDev implements DataSourceService {
    @Override
    public String getConnectionString() {
        return "http://127.0.0.1/dev";
    }
}
