package me.noitcereon.practice.spring.core.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("PROD")
@Service
public class DataSourceServiceProd implements DataSourceService {
    @Override
    public String getConnectionString() {
        return "production datasource";
    }
}
