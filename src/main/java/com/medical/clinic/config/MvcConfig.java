package com.medical.clinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/patientDashboard").setViewName("patient-dashboard");
        registry.addViewController("/adminDashboard").setViewName("patient-dashboard");
       // registry.addViewController("/shipper_home").setViewName("shipper_home");
    }

}