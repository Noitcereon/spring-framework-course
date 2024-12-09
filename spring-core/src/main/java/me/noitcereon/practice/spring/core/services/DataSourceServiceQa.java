package me.noitcereon.practice.spring.core.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("QA")
@Service
public class DataSourceServiceQa implements DataSourceService {
    @Override
    public String getConnectionString() {
        return "Quality assurance datasource";
    }
}
