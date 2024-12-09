package me.noitcereon.practice.spring.core.controllers;

import me.noitcereon.practice.spring.core.services.DataSourceService;
import org.springframework.stereotype.Controller;

@Controller
public class DataSourceController {

    private final DataSourceService dataSourceService;

    public DataSourceController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    public String getConnnectionString(){
        return dataSourceService.getConnectionString();
    }
}
